package tasklist.util

class StringConst {
    companion object {
        //command
        const val addCommand = "add"
        const val printCommand = "print"
        const val endCommand = "end"
        const val editCommand = "edit"
        const val deleteCommand = "delete"

        //menu
        const val startMenu = "Input an action (add, print, edit, delete, end):"
        const val taskMenu = "Input a new task (enter a blank line to end):"
        const val priorityMenu = "Input the task priority (C, H, N, L):\n"
        const val dateMenu = "Input the date (yyyy-mm-dd):\n"
        const val timeMenu = "Input the time (hh:mm):\n"
        const val editFieldMenu = "Input a field to edit (priority, date, time, task):\n"

        fun inputTaskNum(num: Int) = "Input the task number (1-$num):\n"

        //messages
        const val changed = "The task is changed"
        const val deleted = "The task is deleted"
        const val end = "Tasklist exiting!"


        //invalidMes
        const val invalidPriority = ""
        const val invalidDate = "The input date is invalid\n"
        const val invalidTime = "The input time is invalid\n"
        const val invalidTaskNum = "Invalid task number\n"
        const val invalidField = "Invalid field\n"

        //delimiter for check repo size
        const val delimiter = "!@#$%^&*"

        //errorResponse
        const val noTask = "No tasks have been input"
        const val blankTask = "The task is blank"
        const val unsupInput = "The input action is invalid"


    }


}
