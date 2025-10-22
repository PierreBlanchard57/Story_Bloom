package zip.viwrap.myapplication.api

import io.ktor.http.HttpMethod
import zip.viwrap.myapplication.SingleStoryDto
import zip.viwrap.myapplication.Story
import zip.viwrap.myapplication.StoryDto
import zip.viwrap.myapplication.StoryMapper
import zip.viwrap.myapplication.network.KtorClient
import kotlin.jvm.Throws

object StoriesAPI {
        private lateinit var stories: List<Story>

        private const val STORY_ENDPOINT = "Stories"
        private val storyapper = StoryMapper()

        @Throws(Exception::class)
        suspend fun getStories(): List<Story> {
            if (!this::stories.isInitialized) {
                val storiesData: List<SingleStoryDto> = KtorClient.httpCall(HttpMethod.Get, STORY_ENDPOINT)
                stories = storiesData.map {
                    storyapper.mapStoryDtoToStory(it)
                }
            }

            return stories
        }
}