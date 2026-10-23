package zip.viwrap.myapplication


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
                content = content,
                imageContent = imageContent
            )
        }

    }
}
