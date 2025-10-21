package zip.viwrap.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*

val MainBackground = Color(0xFFFFFFFF)
val MainForeground = Color(0xFFFFFFFF)

@Composable
fun Tap() {
    //val amountVisible = remember { mutableIntStateOf(0) }
    var amountVisible by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize().background(MainBackground),
        verticalArrangement = Arrangement.spacedBy(100.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("${amountVisible}€",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
            )
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterHorizontally)
        ) {
            //IncrementButton("+1€", { amountVisible++ })
            //IncrementButton("+10€", { amountVisible += 10 })
            //IncrementButton("+100€", { amountVisible += 100 })
        }
    }
}