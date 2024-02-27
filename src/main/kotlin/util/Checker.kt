package tasklist.util

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.time.format.*
import java.time.temporal.ChronoField
import java.util.stream.Stream

class Checker {
    companion object {
        val chPriority: (String) -> String =
            { s ->
                Stream.of("C", "H", "N", "L")
                    .filter { it.equals(s, ignoreCase = true) }
                    .findFirst()
                    .orElse(StringConst.invalidPriority)
            }

        val chDate: (String) -> String =
            {

                val listPatterns = getFormattersForDate()
                var res = StringConst.invalidDate
                try {
                    for (p in listPatterns) {
                        try {
                            res = java.time.LocalDate.parse(it, p)
                                .toString()
                            break
                        } catch (e: DateTimeParseException) {
                            continue
                        }
                    }
                } catch (e: DateTimeParseException) {
                    res = StringConst.invalidDate
                }
                res
            }
        val chTime: (String) -> String =
            {
                val listPatterns = listOf("HH:mm", "HH:m", "H:mm", "H:m")
                var res = StringConst.invalidTime
                try {
                    for (p in listPatterns) {
                        try {
                            res = java.time.LocalTime
                                .parse(it, DateTimeFormatter.ofPattern(p).withResolverStyle(ResolverStyle.STRICT))
                                .toString()
                            break
                        } catch (e: DateTimeParseException) {
                            continue
                        }
                    }
                } catch (e: DateTimeParseException) {
                    res = StringConst.invalidTime
                }
                res
            }


        val chField: (String) -> String = { s ->
            Stream.of("priority", "date", "time", "task")
                .filter { it.equals(s, ignoreCase = true) }
                .findFirst()
                .orElse(StringConst.invalidField)
        }

        val chNumForUpd: (String) -> String = {
            var res = StringConst.invalidTaskNum
            val arr = it.split(StringConst.delimiter)
            if (isInRange(arr[0], arr[1])) {
                res = arr[0]
            }

            res
        }

        fun checkDue(taskDate: String): String {
            val delta = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
                .daysUntil(LocalDate.parse(taskDate))
            if (delta == 0) return "T"
            if (delta > 0) return "I"
            return "O"
        }


        //private methods
        private fun isInRange(strNum: String, strSize: String): Boolean {
            try {
                if (strNum.toInt() in 1..strSize.toInt()) {
                    return true
                }
                return false
            } catch (e: NumberFormatException) {
                return false
            }
        }

        private fun getFormattersForDate(): List<DateTimeFormatter> {
            val mmdd = DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
                .appendLiteral('-')
                .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                .appendLiteral('-')
                .appendValue(ChronoField.DAY_OF_MONTH, 2).toFormatter()
                .withResolverStyle(ResolverStyle.STRICT)

            val mdd = DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
                .appendLiteral('-')
                .appendValue(ChronoField.MONTH_OF_YEAR, 1)
                .appendLiteral('-')
                .appendValue(ChronoField.DAY_OF_MONTH, 2).toFormatter()
                .withResolverStyle(ResolverStyle.STRICT)
            val md = DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
                .appendLiteral('-')
                .appendValue(ChronoField.MONTH_OF_YEAR, 1)
                .appendLiteral('-')
                .appendValue(ChronoField.DAY_OF_MONTH, 1).toFormatter()
                .withResolverStyle(ResolverStyle.STRICT)
            val mmd = DateTimeFormatter.ofPattern("yyyy-MM-d")
            return listOf<DateTimeFormatter>(mmdd, mdd, md, mmd)

        }
    }
}