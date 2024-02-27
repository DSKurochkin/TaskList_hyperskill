package tasklist.console_inmem_impl

import tasklist.View
import tasklist.ViewReader
import tasklist.util.Checker.Companion.chDate
import tasklist.util.Checker.Companion.chField
import tasklist.util.Checker.Companion.chNumForUpd
import tasklist.util.Checker.Companion.chPriority
import tasklist.util.Checker.Companion.chTime
import tasklist.util.StringConst
import tasklist.util.StringConst.Companion.delimiter
import tasklist.util.StringConst.Companion.inputTaskNum
import tasklist.util.StringConst.Companion.taskMenu

class ViewReaderImpl(override val view: View) : ViewReader {
    override fun getPriority(): String = loop(
        StringConst.priorityMenu,
        StringConst.invalidPriority,
        chPriority
    ).uppercase()

    override fun getDate(): String = loop(StringConst.dateMenu, StringConst.invalidDate, chDate)
    override fun getTime(): String = loop(StringConst.timeMenu, StringConst.invalidTime, chTime)
    override fun getNumForUpd(size: Int): Int =
        loop(inputTaskNum(size), StringConst.invalidTaskNum, chNumForUpd, size).toInt()

    override fun getField(): String = loop(StringConst.editFieldMenu, StringConst.invalidField, chField)
    override fun getTasks(): MutableList<String> {
        val res = mutableListOf<String>()
        view.displayMes(taskMenu)
        var task = view.readInput().trim()
        while (task.isNotBlank()) {
            res.add(task)
            task = view.readInput().trim()
        }
        return res
    }

    override fun printStartMenu(): String {
        view.displayMes(StringConst.startMenu)
        return view.readInput()
    }


    private fun loop(menu: String, er: String, f: (s: String) -> String, repoSize: Int = -1): String {
        if (repoSize == 0) {
            return StringConst.noTask
        }
        view.displayMes(menu, true)
        var input = f.invoke(readStr(repoSize))
        while (input == er) {
            view.displayMes(er, true)
            view.displayMes(menu, true)
            input = f.invoke(readStr(repoSize))
        }

        return input
    }

    fun readStr(size: Int): String {
        var res = view.readInput()
        if (size > 0) {
            res = res + delimiter + size
        }
        return res
    }
}