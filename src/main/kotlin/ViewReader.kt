package tasklist

interface ViewReader {
    val view: View
    fun getPriority(): String
    fun getDate(): String
    fun getTime(): String
    fun getNumForUpd(size: Int): Int
    fun getField(): String
    fun getTasks(): MutableList<String>
    fun printStartMenu(): String
}