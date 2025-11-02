package zip.viwrap.myapplication.viewmodel

import zip.viwrap.myapplication.Story

data class StoryState(
    val stories: List<Story> = listOf(),
    val selectedStory: Story? = null,
    val currentPage:Int=0,
    val isLoading: Boolean = false,
    val error: Exception? = null,
)
