package zip.viwrap.myapplication

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import zip.viwrap.myapplication.userpersist.BookProgressViewModel
import kotlin.concurrent.thread
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import java.util.Locale


@Composable
fun StoryReader(story: Story,page:Int,tts: MutableState<TextToSpeech?>){
    val dbvm = BookProgressViewModel.getViewModel()
    if(!dbvm.isTimeSpanStarted){
        dbvm.startTimespan();println("timespan started!")
        dbvm.isTimeSpanStarted=true;
    }
    if(page<=story.pages){
        val currentContent=story.content.get(page-1)
        val currentImageContent=story.imageContent.get(page-1)
        tts.value?.speak(currentContent, TextToSpeech.QUEUE_FLUSH, null, "")
        Column (modifier = Modifier.verticalScroll(rememberScrollState())){
            AsyncImage(model = currentImageContent, contentDescription = "",
                modifier = Modifier.fillMaxWidth().height(400.dp)
                , contentScale = ContentScale.FillBounds)
            Text(currentContent)

        }

    }else{
        Text("FIN", fontSize = 100.sp, modifier = Modifier.fillMaxSize()
            .wrapContentSize(Alignment.Center),
            textAlign = TextAlign.Center);
    }

}