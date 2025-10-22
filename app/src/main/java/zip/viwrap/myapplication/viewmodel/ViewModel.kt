package zip.viwrap.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import zip.viwrap.myapplication.Story
import zip.viwrap.myapplication.api.StoriesAPI
import zip.viwrap.myapplication.network.StateManager

class StoryViewModel: ViewModel() {
    private val storiesMutableStateFlow= MutableStateFlow(StoryState())

    val stateFlow: StateFlow<StoryState>
        get() = storiesMutableStateFlow.asStateFlow()

    private var storyState: StoryState
        get() = storiesMutableStateFlow.value
        set(value) {
            storiesMutableStateFlow.value = value
        }

    init {
        viewModelScope.launch {
            getStoryList()
        }
    }

    fun getStoryList() {
        storyState = storyState.copy(isLoading = true)
        StateManager.launchCoroutine {
            storyState = try {
                storyState.copy(
                    stories = StoriesAPI.getStories(),
                    isLoading = false
                )
            } catch (error: Exception) {
                storyState.copy(
                    error = error,
                    isLoading = false
                )
            }
        }
    }

    fun setSelectedStory(story: Story) {
        storyState = storyState.copy(
            selectedStory =story
        )
    }
}

