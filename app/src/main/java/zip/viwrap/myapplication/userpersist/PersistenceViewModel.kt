package zip.viwrap.myapplication.userpersist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*

class BookProgressViewModel(private val bookProgressDao: BookProgressDao) : ViewModel() {
    private val _bookDetails = MutableStateFlow<List<BookProgressDetail?>>(arrayListOf())
    val bookDetails get() = _bookDetails.asStateFlow()

    fun removeBookProgressFromDB(bookId: Int) {
        viewModelScope.launch {
            bookProgressDao.deleteBookProgressDetail(bookId)
        }
    }

    // Flux réactif du niveau du joueur selon la persistence.
    val level = bookProgressDao.getLevel()

    companion object {
        private lateinit var instance: ViewModel

        @JvmStatic
        fun getViewModel(bookProgressDao: BookProgressDao? = null): ViewModel {
            if (::instance.isInitialized)
                return instance
            instance = BookProgressViewModel(bookProgressDao!!)
            return instance
        }
    }
}

// C'est vachement plus facile d'avoir un singleton

