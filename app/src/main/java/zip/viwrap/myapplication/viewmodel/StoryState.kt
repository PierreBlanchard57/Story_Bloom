package zip.viwrap.myapplication.viewmodel

import zip.viwrap.myapplication.Story

data class StoryState(
    val stories: List<Story> = listOf(),
    val selectedStory: Story? = null,
    val isLoading: Boolean = false,
    val error: Exception? = null
)
