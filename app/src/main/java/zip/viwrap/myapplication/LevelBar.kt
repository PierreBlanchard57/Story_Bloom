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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zip.viwrap.myapplication.userpersist.BookProgressViewModel
import kotlin.math.log
import kotlin.math.pow

// Grosse formule au pif pour baliser les niveaux avec des intervalles grandissantes
val factor = 50
val exponent = 2
val base = 0

private fun getUpperBound(level: Int): Int {
    return (factor * (level.toDouble().pow(exponent).toInt())) + base
}

private fun getLowerBound(level: Int): Int {
    return (factor * ((level-1).toDouble().pow(exponent).toInt())) + base
}

@Composable
fun LevelBar(){
    val pesistence = BookProgressViewModel.getViewModel()
    val level by pesistence.levelState.collectAsState()
    var range = 0
    while (getUpperBound(range) <= level)
        ++range

    val lowerBound = getLowerBound(range)
    val upperBound = getUpperBound(range)
    val progress = (level - lowerBound).toFloat() / (upperBound - lowerBound)

    Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)){
        var levelWidth = 20 * (log(lowerBound.toDouble(), base = 10.0) + 1)
        Text(lowerBound.toString(), fontSize = 18.sp, color = Color.White, modifier = Modifier
            .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
            .background(Color(0xFFA600FD))
            .width(levelWidth.dp)
            .height(20.dp), textAlign = TextAlign.Center)
        LinearProgressIndicator(
            progress = { progress },
            gapSize = 0.dp,
            drawStopIndicator = {},
            color = Color(0xFFFD00D3),
            strokeCap = StrokeCap.Square
        )
        val nextLevelWidth = 20 * (log(upperBound.toDouble(), base = 10.0) + 1)
        Text(upperBound.toString(),fontSize = 18.sp, color = Color.White, modifier = Modifier
            .clip(RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
            .background(Color(0xFFA600FD))
            .width(nextLevelWidth.dp)
            .height(20.dp), textAlign = TextAlign.Center)
    }
}