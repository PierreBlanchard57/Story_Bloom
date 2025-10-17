package zip.viwrap.myapplication.viewmodel

import zip.viwrap.myapplication.Movie

data class MovieState(
    val movies: List<Movie> = listOf(),
    val selectedMovie: Movie? = null,
    val isLoading: Boolean = false,
    val error: Exception? = null
)
