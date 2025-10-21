package zip.viwrap.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.unit.dp

@Composable
fun ClickableTag(text: String,clicked:Boolean){
    var clicked by remember { mutableStateOf(clicked) }
    val colorStops = arrayOf(
        0.0f to Color(0xFF6650a4),
        1f to Color(0xFFFD24AD)
    )
    val colorStopsWhite = arrayOf(
        0.0f to Color(0xFFADADAD),
        1f to Color(0xFFADADAD)
    )
    var gradient=Brush.linearGradient(colorStops = colorStops)
    var gradientWhite=Brush.linearGradient(colorStops = colorStopsWhite,
        start = Offset(0f,0f), end = Offset(200f,450f))


    Text(text, modifier = Modifier.clip(RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
        .background(if(clicked) gradient else gradientWhite)
        .padding(2.dp,2.dp,2.dp,2.dp)
        .clickable { clicked=!clicked }, color = Color.White)
}

@Composable
fun Tag(text: String){
    Text(text, modifier = Modifier.clip(RoundedCornerShape(5.dp, 5.dp, 5.dp, 5.dp))
        .background(Color(0xFFA600FD))
        .padding(2.dp,2.dp,2.dp,2.dp), color = Color.White)
}