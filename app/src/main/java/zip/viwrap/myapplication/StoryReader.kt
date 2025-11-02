package zip.viwrap.myapplication

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import zip.viwrap.myapplication.viewmodel.StoryViewModel

@Composable
fun StoryReader(story: Story){
    //val vm: StoryViewModel= viewModel()
    //val storyState=vm.storiesStateFlow.collectAsState().value
    Text(story.title)
}