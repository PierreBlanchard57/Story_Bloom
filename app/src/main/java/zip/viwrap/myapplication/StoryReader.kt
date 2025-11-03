package zip.viwrap.myapplication

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import zip.viwrap.myapplication.userpersist.BookProgressViewModel

@Composable
fun StoryReader(story: Story,page:Int){
    val dbvm = BookProgressViewModel.getViewModel()
    //dbvm.startTimespan()
    Text(story.content.get(page-1))
}