package zip.viwrap.myapplication

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import zip.viwrap.myapplication.userpersist.BookProgressViewModel
import kotlin.concurrent.thread
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.util.Locale


@Composable
fun StoryReader(story: Story,page:Int,tts: MutableState<TextToSpeech?>){
    val dbvm = BookProgressViewModel.getViewModel()
    if(!dbvm.isTimeSpanStarted){
        dbvm.startTimespan();println("timespan started!")
        dbvm.isTimeSpanStarted=true;
    }
    val currentContent=story.content.get(page-1)
    tts.value?.speak(currentContent, TextToSpeech.QUEUE_FLUSH, null, "")
    Text(currentContent)
}