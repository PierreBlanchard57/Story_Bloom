package zip.viwrap.myapplication.userpersist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*
import zip.viwrap.myapplication.Preference
import java.util.concurrent.Executors

class BookProgressViewModel(private val bookProgressDao: BookProgressDao) : ViewModel() {
    private val _bookDetails = MutableStateFlow<List<BookProgressDetail?>>(arrayListOf())
    val bookDetails get() = _bookDetails.asStateFlow()

    fun removeBookProgressFromDB(bookId: Int) {
        viewModelScope.launch {
            bookProgressDao.deleteBookProgressDetail(bookId)
        }
    }

    // Flux réactif du niveau du joueur selon la persistence.
    val levelState: StateFlow<Int> = bookProgressDao.getLevel().stateIn(
        viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = 0
    )

    fun getPagesRead(id: Int): StateFlow<Int> {
        return bookProgressDao.getPagesRead(id).stateIn(
            viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = 0
        )
    }

    fun getSecondsSpent(): StateFlow<Int> {
        return bookProgressDao.getTimespanSecondsToday().stateIn(
            viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = 0
        )
    }

    val preferences: StateFlow<List<PreferenceDetail>> = bookProgressDao.getPreferences().stateIn(
        viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = listOf()
    )

    fun updatePreference(pDetail: PreferenceDetail) {
        viewModelScope.launch {
            Executors.newSingleThreadExecutor().execute {
                bookProgressDao.update(pDetail)
            }
        }
    }

    companion object {
        private lateinit var instance: BookProgressViewModel

        @JvmStatic
        fun getViewModel(bookProgressDao: BookProgressDao? = null): BookProgressViewModel {
            if (::instance.isInitialized)
                return instance
            instance = BookProgressViewModel(bookProgressDao!!)
            return instance
        }
    }
}

// C'est vachement plus facile d'avoir un singleton

