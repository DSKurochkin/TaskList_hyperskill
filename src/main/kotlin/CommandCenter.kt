package tasklist

import tasklist.console_inmem_impl.SaveInFileImpl
import tasklist.model.Task
import tasklist.util.Checker.Companion.checkDue
import tasklist.util.StringConst.Companion.addCommand
import tasklist.util.StringConst.Companion.blankTask
import tasklist.util.StringConst.Companion.changed
import tasklist.util.StringConst.Companion.deleteCommand
import tasklist.util.StringConst.Companion.deleted
import tasklist.util.StringConst.Companion.editCommand
import tasklist.util.StringConst.Companion.end
import tasklist.util.StringConst.Companion.endCommand
import tasklist.util.StringConst.Companion.printCommand
import tasklist.util.StringConst.Companion.unsupInput

class CommandCenter(
    private val viewReader: ViewReader,
    private val dao: Dao,
    private val view: View,
    private val saveInFileImpl: SaveInFileImpl
) {

    fun startWorking() {
        var command = viewReader.printStartMenu()
        while (true) {
            when (command) {
                addCommand -> addCommand()
                editCommand -> editCommand()
                printCommand -> printCommand()
                deleteCommand -> deleteCommand()
                endCommand -> exitCommand()
                else -> view.displayMes(unsupInput)
            }
            command = viewReader.printStartMenu()
        }

    }

    private fun addCommand() {
        val priority = viewReader.getPriority()
        val date = viewReader.getDate()
        val time = viewReader.getTime()
        val due = checkDue(date)
        val tasks = viewReader.getTasks()
        if (tasks.isEmpty()) {
            view.displayMes(blankTask)
            return
        }
        dao.add(Task(date, time, priority, due, tasks))

    }

    private fun editCommand() {
        if (view.displayTasks(dao.getAll())) {
            val num = viewReader.getNumForUpd(dao.size())
            val field = viewReader.getField()
            when (field) {
                "priority" -> dao.get(num).priority = viewReader.getPriority()

                "date" -> dao.get(num).date = viewReader.getDate()
                "time" -> dao.get(num).time = viewReader.getTime()
                "task" -> {
                    dao.get(num).subtasks = viewReader.getTasks()
                }
            }
        }
        view.displayMes(changed)
    }

    private fun printCommand() = view.displayTasks(dao.getAll())

    private fun deleteCommand() {
        if (view.displayTasks(dao.getAll())) {
            val num = viewReader.getNumForUpd(dao.size())
            dao.delete(num)
            view.displayMes(deleted)
        }
    }

    private fun exitCommand() {
        saveInFileImpl.file.writeText(saveInFileImpl.jsonAdapter.toJson(dao.getAll()))
        view.displayMes(end)
        System.exit(0)
    }
}