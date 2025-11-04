package zip.viwrap.myapplication

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import zip.viwrap.myapplication.userpersist.BookProgressViewModel
import kotlin.concurrent.thread

@Composable
fun StoryReader(story: Story,page:Int){
    val dbvm = BookProgressViewModel.getViewModel()
    LaunchedEffect(Unit) {
        dbvm.startTimespan();println("timespan started!")
        dbvm.isTimeSpanStarted=true;
    }
    Text(story.content.get(page-1))
}