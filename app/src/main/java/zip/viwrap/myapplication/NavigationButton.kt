package zip.viwrap.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun NavigationButton(icon: ImageVector, text: String, callback: () -> Unit) {
    Button(callback, colors = ButtonColors(
        Color.Transparent, Color(0xFF423F3F),
        disabledContainerColor = Color.Transparent,
        disabledContentColor = Color.Transparent
    )) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(icon, "")
            Text(text)
        }
    }
}
