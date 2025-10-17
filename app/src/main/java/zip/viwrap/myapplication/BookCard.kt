package zip.viwrap.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun BookCard(movie: Movie, onClickDetails: (movie: Movie) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.padding(3.dp).height(500.dp)
            .clickable { onClickDetails(movie) }
    ) {
        Column (modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.weight(1f).fillMaxWidth()
                    .background(Color.Gray)
            ) {
                AsyncImage(
                    model = movie.imageUrl,
                    contentDescription = "Movie poster",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier.padding(10.dp)
                    .fillMaxWidth().wrapContentHeight()
            ) {
                Text(
                    movie.title,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp, 2.dp)
                )
                Text(
                    movie.director,
                    color = Color.Black,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(10.dp, 0.dp)
                )
                Text(
                    "Sortie : ${movie.year}",
                    color = Color.Black,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(10.dp, 0.dp)
                )
                if (expanded) {
                    Text(movie.plot, color = Color.Black, fontSize = 11.sp,
                        modifier = Modifier.padding(10.dp, 0.dp))
                }
                Box (
                    Modifier.fillMaxWidth().padding(10.dp),
                    contentAlignment = Alignment.TopEnd,
                ) {
                    Button(onClick = { expanded = !expanded },
                        colors = ButtonDefaults.buttonColors(Color.Gray),
                        modifier = Modifier.requiredWidthIn(60.dp)
                    ) {
                        Icon(imageVector = Icons.Filled.KeyboardArrowDown, contentDescription = "Check movie")
                    }
                }
            }
        }
    }
}