package tasklist

import tasklist.console_inmem_impl.DaoInMem
import tasklist.console_inmem_impl.SaveInFileImpl
import tasklist.console_inmem_impl.ViewImpl
import tasklist.console_inmem_impl.ViewReaderImpl


fun main() {
    val saveInFileImpl = SaveInFileImpl()
    val view = ViewImpl()
    CommandCenter(ViewReaderImpl(view), DaoInMem(saveInFileImpl), view, saveInFileImpl).startWorking()
}
