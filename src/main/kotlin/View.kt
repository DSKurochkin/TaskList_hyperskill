package tasklist

import tasklist.model.Task

interface View {
    fun displayMes(mes: String, custom: Boolean = false)
    fun displayTasks(tasks: MutableList<Task>): Boolean
    fun readInput(): String
}