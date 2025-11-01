package zip.viwrap.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.*
import coil3.compose.AsyncImage

// Tuile avec graphique du succès en bas à gauche, et statistique (avec nombre clé en évidence)
@Composable
fun AchievementTile(width: Dp?, painter: Painter, statistic: String) {
    val words = statistic.split(" ")
    val firstWord = words.first()
    val restWords = words.drop(1).joinToString(" ")
    val ratio = firstWord.endsWith("%");

    // Si on n'a pas de largeur, prendre toute la largeur...
    var boxModifier = Modifier
        .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
        .background(color = Color(0xFFAC52DC))
        .height(100.dp)
    boxModifier = if (width != null) boxModifier.width(width) else boxModifier.fillMaxWidth()

    Box(modifier = boxModifier) {
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
fun BadgeTile(width: Dp, _painter: Painter, _title: String, unlockPredicate: Boolean) {
    val painter = if (unlockPredicate) _painter else rememberVectorPainter(Icons.Default.Lock)
    val title = if (unlockPredicate) _title else "Verrouillé"

    Box(modifier = Modifier
        .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
        .width(width)
        .aspectRatio(1f)
        .background(color = Color(0xFFAC52DC))
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()) {
            Icon(painter = painter, contentDescription = "", tint = Color(0xAAFFFFFF))
            Text(title, color = Color(0xFFFFFFFF), textAlign = TextAlign.Center)
        }
    }
}

// Tuile horizontale
@Composable
fun ReaderProgressTile(book: Story) {
    Box(Modifier
        .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
        .background(color = Color(0xFFAC52DC))
        .fillMaxWidth()
        .requiredHeight(80.dp)
    ) {
        Box(modifier = Modifier.padding(10.dp)) {
            Row {
                AsyncImage(model = book.coverImage, contentDescription = "Couverture du bouquin",
                    modifier = Modifier.fillMaxHeight().aspectRatio(1f))
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly) {
                    Row {
                        Text(book.title, color = Color(0xFFFFFFFF))
                        Spacer(Modifier.weight(1f))
                        Text("90%", fontSize = 30.sp, fontFamily = TitleFont.Family, color = Color(0xFFFFFFFF))
                    }
                    LinearProgressIndicator(
                        progress = { 0.3f },
                        gapSize = 0.dp,
                        drawStopIndicator = {},
                        strokeCap = StrokeCap.Square,
                        color = Color(0xFFFF9E30),
                        trackColor = Color(0x80FFFFFF),
                        modifier = Modifier.fillMaxWidth().height(10.dp)
                    )
                }
            }
        }
    }
}
