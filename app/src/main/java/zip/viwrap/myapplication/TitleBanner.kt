package zip.viwrap.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//@Preview
@Composable
fun TitleBanner() {
    val colorStops = arrayOf(
        0.0f to Color(0xFF6650a4),
        0.5f to Color(0xFFFD24AD),
        1f to Color(0xFFFFBB3C)
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
            .background(Brush.linearGradient(colorStops = colorStops,
                start = Offset(0f,0f), end = Offset(200f,450f)))
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Row {
            Image(painter = painterResource(R.drawable.book),
                contentDescription = "", modifier = Modifier.size(60.dp))
            Text("StoryBloom", fontSize = 48.sp, fontFamily = TitleFont.Family, color = Color.White)
        }
    }
}
