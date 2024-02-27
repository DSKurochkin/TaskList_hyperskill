package tasklist.console_inmem_impl

import tasklist.model.Task
import tasklist.View
import tasklist.util.StringConst
import java.util.stream.Collectors

class ViewImpl : View {

    private val num = "N"
    private val date = "Date"
    private val time = "Time"
    private val p = "P"
    private val d = "D"
    private val taskKey = "Task "


    //colors
    private val red = "\u001B[101m \u001B[0m"
    private val yellow = "\u001B[103m \u001B[0m"
    private val green = "\u001B[102m \u001B[0m"
    private val blue = "\u001B[104m \u001B[0m"

    //maps
    private val columnsMap =
        mapOf<String, Int>(Pair(num, 4), Pair(date, 12), Pair(time, 7), Pair(p, 3), Pair(d, 3), Pair(taskKey, 44))
    private val priorityTagColors =
        mapOf<String, String>(Pair("C", red), Pair("H", yellow), Pair("N", green), Pair("L", blue))
    private val dueTagColors = mapOf<String, String>(Pair("I", green), Pair("T", yellow), Pair("O", red))

    private val maxSizeTaskDescription = 44


    override fun displayMes(mes: String, custom: Boolean) {
        if (custom) print(mes)
        else println(mes)
    }

    override fun displayTasks(tasks: MutableList<Task>): Boolean {
        return if (tasks.isEmpty()) {
            (StringConst.noTask)
            false
        } else {
            displayMes(separateLine(), true)
            displayMes(header(), true)
            displayMes(separateLine(), true)
            for (i in 0 until tasks.size) {
                printTask(tasks[i], i)
                displayMes(separateLine(), true)
            }
            true
        }
    }

    override fun readInput(): String {
        return readln()
    }


    //private
    private fun separateLine(): String {
        return listOf(
            columnsMap[num],
            columnsMap[date],
            columnsMap[time],
            columnsMap[p],
            columnsMap[d],
            columnsMap[taskKey],
            0
        )
            .stream().map { addPlus(it) }.collect(Collectors.joining()) + "\n"
    }

    private fun header(): String {
        return columnsMap.entries
            .stream().map { wrapString(it.key, it.value, true) }.collect(Collectors.joining()) + "|\n"
    }

    private fun printTask(task: Task, num: Int) {
        val taskInLines = listLinesBySize(task)
        displayMes(
            toLine(
                (num + 1).toString(),
                task.date,
                task.time,
                priorityTagColors.getOrDefault(task.priority, task.priority),
                dueTagColors.getOrDefault(task.due, task.due),
                taskInLines[0]
            )
        )
        for (i in 1 until taskInLines.size) {
            displayMes(toLine("", "", "", "", "", taskInLines[i]))
        }
    }

    private fun addPlus(n: Int?): String {
        if (n == null) throw RuntimeException("Error in graphicsMap")
        return "+${"-".repeat(n)}"
    }

    private fun wrapString(s: String, i: Int, isHeader: Boolean = false, isTaskDescription: Boolean = false): String {
        if (s.length > i) {
            return "| $s "
        }
        var indentL = 1
        if (isHeader) {
            indentL = (i - s.length) / 2
        }
        if (isTaskDescription) {
            indentL = 0
        }
        val indentR = i - s.length - indentL
        return "|${" ".repeat(indentL)}$s${" ".repeat(indentR)}"
    }

    private fun toLine(vararg args: String): String {
        val sizeOfColumnList = columnsMap.values.toList()

        var i = 0
        var res = ""
        for (j in 0 until args.size - 1) {
            res += wrapString(args[j], sizeOfColumnList[i])
            i++
        }
        res += wrapString(args[args.size - 1], sizeOfColumnList[i], false, true)

        res += "|"
        return res
    }

    private fun listLinesBySize(task: Task): ArrayList<String> {
        val res = ArrayList<String>(task.subtasks.size)
        for (line in task.subtasks) {
            if (line.length < maxSizeTaskDescription + 1) {
                res.add(line)
            } else {
                var size = line.length
                var temp = line
                while (size > maxSizeTaskDescription) {
                    res.add(temp.substring(0, maxSizeTaskDescription))
                    temp = temp.substring(maxSizeTaskDescription)
                    size -= maxSizeTaskDescription
                }
                res.add(temp)
            }

        }
        return res
    }

}