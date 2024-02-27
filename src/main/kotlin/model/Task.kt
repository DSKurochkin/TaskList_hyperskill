package tasklist.model

data class Task(
    var date: String, var time: String, var priority: String, val due: String, var subtasks: MutableList<String>
) {
    fun onPrint(index: Int): String {
        var indent = "  "
        val indent2 = "   "
        if (index > 8) {
            indent = " "
        }
        val subtasksInString = "$indent2${subtasks.joinToString("\n$indent2")}"
        return "${index + 1}$indent$date $time $priority $due" + "\n" + subtasksInString + "\n"
    }
}

