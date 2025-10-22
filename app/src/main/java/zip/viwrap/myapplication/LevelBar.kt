package zip.viwrap.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.log

@Composable
fun LevelBar(level: Int,progress:Float){
    Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)){
        var levelWidth=20* (log(level.toDouble(), base = 10.0)+1)
        Text(level.toString(), fontSize = 18.sp, color = Color.White, modifier = Modifier
            .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp)).
            background(Color(0xFFA600FD)).width(levelWidth.dp).height(20.dp), textAlign = TextAlign.Center)
        LinearProgressIndicator(
            progress = { progress },
            gapSize = 0.dp,
            drawStopIndicator = {},
            color = Color(0xFFFD00D3),
            strokeCap = StrokeCap.Square
        )
        var nextLevel=level+1
        var nextLevelWidth=20* (log(nextLevel.toDouble(), base = 10.0)+1)
        Text(nextLevel.toString(),fontSize = 18.sp, color = Color.White, modifier = Modifier
            .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp)).
            background(Color(0xFFA600FD)).width(nextLevelWidth.dp).height(20.dp), textAlign = TextAlign.Center)
    }
}