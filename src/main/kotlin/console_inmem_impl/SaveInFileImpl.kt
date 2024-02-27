package tasklist.console_inmem_impl

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import tasklist.model.Task
import java.io.File

class SaveInFileImpl {
    val file: File = File("tasklist.json")
    val jsonAdapter = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()).build()
        .adapter<MutableList<Task>>(Types.newParameterizedType(MutableList::class.java, Task::class.java))

}