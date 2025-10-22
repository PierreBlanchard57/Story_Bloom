package zip.viwrap.myapplication

import android.nfc.Tag
import android.widget.ProgressBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun StoryCard(story: Story,progression:Float){
    Column(modifier = Modifier.padding(10.dp,10.dp,10.dp,10.dp).border(1.dp,Color.Gray, shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
        .width(180.dp)
        ) {

        Box(Modifier.fillMaxWidth()){
            AsyncImage(model = story.coverImage, contentDescription = "",
                modifier = Modifier.fillMaxWidth().height(200.dp)
            , contentScale = ContentScale.Crop,)
            Row (horizontalArrangement = Arrangement.spacedBy(5.dp)){
               story.tags.forEach(){
                       tag->
                   NormalTag(tag)
               }
           }

        }
        Text(story.title, fontSize = 18.sp, modifier = Modifier.padding(10.dp,10.dp,0.dp,0.dp))
        Text(story.author, color = Color.Gray,modifier = Modifier.padding(10.dp,10.dp,0.dp,10.dp))
        Row {
            Icon(Icons.Rounded.Info,"")
            Text("${story.duration} min",color = Color.Gray, modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp))
            Icon(Icons.Rounded.AccountCircle,"")
            Text("${story.minAge}-${story.maxAge} ans",color = Color.Gray)
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp,0.dp,10.dp,0.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Progression",color = Color.Gray)
            Text(text = "${(progression * 100).toInt()}%",color = Color.Gray)
        }
        LinearProgressIndicator(
            progress = { progression },
            color = Color.Black,
            gapSize = 0.dp,
            strokeCap = StrokeCap.Butt,
            modifier = Modifier.padding(10.dp,10.dp,10.dp,10.dp).height(10.dp),
            drawStopIndicator = {}
        )
    }

}