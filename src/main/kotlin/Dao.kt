package tasklist

import tasklist.model.Task

interface Dao {
    fun add(task: Task)
    fun edit(task: Task, num: Int)
    fun delete(num: Int)
    fun getAll(): MutableList<Task>
    fun get(num: Int): Task
    fun size(): Int
}