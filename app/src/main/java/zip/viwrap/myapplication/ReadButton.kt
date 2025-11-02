package zip.viwrap.myapplication

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import zip.viwrap.myapplication.userpersist.BookProgressViewModel
import zip.viwrap.myapplication.viewmodel.StoryState
import zip.viwrap.myapplication.viewmodel.StoryViewModel

@Composable
fun ReadButton(story:Story,onPlay:(Story)->Unit){
    val colorStops = arrayOf(
        0.0f to Color(0xFF6650a4),
        0.5f to Color(0xFFFD24AD),
        1f to Color(0xFFFFBB3C)
    )
    val gradient=Brush.linearGradient(colorStops = colorStops,
        start = Offset(0f,0f), end = Offset(200f,450f))

    Button(onClick = {onPlay(story)}, shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp),modifier = Modifier
        .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
        .background(gradient),
colors = ButtonColors(Color.Transparent, Color.Transparent,Color.Gray,Color.Gray)
    ) {
            Image(painter = painterResource(R.drawable.read_book),"")
            //("LIRE", color = Color(0xFF016507), fontWeight = FontWeight.Bold,
            //    textAlign = TextAlign.Center)
    }

}
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PageButtons(story: Story,page:Int,onPrec:()->Unit,onNext:()->Unit){
    Row(horizontalArrangement = Arrangement.Center){

        Icon(Icons.AutoMirrored.Filled.ArrowBack,"Page précédente", modifier = Modifier
            .size(60.dp)
            .clickable(){onPrec()
        })
        Icon(Icons.AutoMirrored.Filled.ArrowForward,"Page suivante",modifier = Modifier
            .size(60.dp)
            .clickable(){
            onNext()
        })

        Text("Page "+page)
    }
}