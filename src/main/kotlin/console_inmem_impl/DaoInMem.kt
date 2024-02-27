package tasklist.console_inmem_impl


import tasklist.Dao
import tasklist.model.Task
import java.io.IOException

class DaoInMem(private val saveInFileImpl: SaveInFileImpl) : Dao {

    private var taskList: MutableList<Task>


    init {
        taskList = try {
            val temp = saveInFileImpl.jsonAdapter.fromJson(saveInFileImpl.file.readText())
            if (temp.isNullOrEmpty()) throw IOException()
            temp
        } catch (e: IOException) {
            mutableListOf()
        }
    }

    override fun add(task: Task) {
        taskList.add(task)
    }

    override fun edit(task: Task, num: Int) {
        taskList[num] = task
    }

    override fun delete(num: Int) {
        taskList.removeAt(num - 1)
    }

    override fun getAll(): MutableList<Task> = taskList
    override fun get(num: Int): Task = taskList.get(num - 1)
    override fun size() = taskList.size
}