package zip.viwrap.myapplication.api

import io.ktor.http.HttpMethod
import zip.viwrap.myapplication.Movie
import zip.viwrap.myapplication.MovieMapper
import zip.viwrap.myapplication.SingleMovieDto
import zip.viwrap.myapplication.network.KtorClient
import kotlin.jvm.Throws

object MoviesAPI {
    private lateinit var movies: List<Movie>

    private const val MOVIE_ENDPOINT = "movies"
    private val movieMapper = MovieMapper()

    @Throws(Exception::class)
    suspend fun getMovies(): List<Movie> {
        if (!this::movies.isInitialized) {
            val moviesData: List<SingleMovieDto> = KtorClient.httpCall(HttpMethod.Get, MOVIE_ENDPOINT)
            movies = moviesData.map {
                movieMapper.mapMovieDtoToMovie(it)
            }
        }

        return movies
    }
}
