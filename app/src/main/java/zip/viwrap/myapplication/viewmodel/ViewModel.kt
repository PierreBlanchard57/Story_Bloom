package zip.viwrap.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import zip.viwrap.myapplication.Movie
import zip.viwrap.myapplication.api.MoviesAPI
import zip.viwrap.myapplication.network.StateManager

class MovieViewModel: ViewModel() {
    private val moviesMutableStateFlow = MutableStateFlow(MovieState())

    val stateFlow: StateFlow<MovieState>
        get() = moviesMutableStateFlow.asStateFlow()

    private var movieState: MovieState
        get() = moviesMutableStateFlow.value
        set(value) {
            moviesMutableStateFlow.value = value
        }

    init {
        viewModelScope.launch {
            getMovieList()
        }
    }

    fun getMovieList() {
        movieState = movieState.copy(isLoading = true)
        StateManager.launchCoroutine {
            movieState = try {
                movieState.copy(
                    movies = MoviesAPI.getMovies(),
                    isLoading = false
                )
            } catch (error: Exception) {
                movieState.copy(
                    error = error,
                    isLoading = false
                )
            }
        }
    }

    fun setSelectedMovie(movie: Movie) {
        movieState = movieState.copy(
            selectedMovie = movie
        )
    }
}

