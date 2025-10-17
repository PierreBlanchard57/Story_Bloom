package zip.viwrap.myapplication

class MovieMapper {
    fun mapMovieDtoToMovie(movieDto: SingleMovieDto): Movie {
        return with(movieDto) {
            Movie(
                id = id,
                title = title,
                year = year.toInt(),
                genres = genres,
                director = director,
                actors = actors.split(", "),
                plot = plot,
                imageUrl = posterUrl
            )
        }

    }
}
