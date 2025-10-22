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
class StoryMapper {
    fun mapStoryDtoToStory(storyDto: SingleStoryDto): Story {
        return with(storyDto) {
            Story(
                id = id,
                title = title,
                author = author,
                coverImage = coverImage,
                pages = pages,
                synopsis = synopsis,
                duration = duration,
                minAge = minAge,
                maxAge = maxAge,
                tags = tags,
            )
        }

    }
}
