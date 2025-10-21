package zip.viwrap.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.*

// Tuile avec graphique du succès en bas à gauche, et statistique (avec nombre clé en évidence)
@Composable
fun AchievementTile(painter: Painter, statistic: String) {
    val words = statistic.split(" ")
    val firstWord = words.first()
    val restWords = words.drop(1).joinToString(" ")
    val ratio = firstWord.endsWith("%");

    Box(modifier = Modifier
        .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
        .background(color = Color(0xFFAC52DC))
        .height(100.dp)) {
        Image(painter = painter, contentDescription = "", modifier =
            Modifier.fillMaxSize().wrapContentSize(Alignment.BottomStart).size(90.dp)
                .absoluteOffset((-20).dp, 10.dp).rotate(10f)
        )
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 60.sp, fontWeight = FontWeight.Black, color = Color(
                    0xFFFFDC72
                )
                )) {
                    append(firstWord + if (ratio) "\n" else "")
                }
                append(" $restWords")
            },
            modifier = Modifier.fillMaxSize().padding(start = 50.dp),
            color = Color.White,
            textAlign = TextAlign.Start,
            softWrap = !ratio
        )
        if (ratio) {
            val progress = (firstWord.dropLast(1).toFloatOrNull() ?: 0f) / 100f
            LinearProgressIndicator(
                progress = { progress },
                gapSize = 0.dp,
                drawStopIndicator = {},
                strokeCap = StrokeCap.Square,
                modifier = Modifier.align(Alignment.BottomCenter).requiredHeight(10.dp),
                color = Color(0xFFFF9E30),
                trackColor = Color(0x80FFFFFF),
            )
        }
    }
}

// Tuile de badge (donc juste une image et un texte)
@Composable
fun BadgeTile() {

}

// Tuile horizontale
@Composable
fun ReaderProgressTile() {

}
