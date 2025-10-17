package com.example.exemple.viewModels

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.colorspace.Rgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import org.jetbrains.annotations.Async

data class MovieDto(
    val movies: List<SingleMovieDto>
)

data class SingleMovieDto (
    val id: Int,
    val title: String,
    val year: String,
    val runtime: String,
    val genres: List<String>,
    val director: String,
    val actors: String,
    val plot: String,
    val posterUrl: String
)
val movieData = MovieDto(
    movies = listOf(
        SingleMovieDto(
            id = 1,
            title = "Beetlejuice",
            year = "1988",
            runtime = "92",
            genres = listOf("Comedy", "Fantasy"),
            director = "Tim Burton",
            actors = "Alec Baldwin, Geena Davis, Annie McEnroe, Maurice Page",
            plot = "A couple of recently deceased ghosts contract the services of a \"bio-exorcist\" in order to remove the obnoxious new owners of their house.",
            posterUrl = "https://moviecovers.com/getjpg.html/BEETLEJUICE.jpg"
        ),
        SingleMovieDto(
            id = 2,
            title = "The Cotton Club",
            year = "1984",
            runtime = "127",
            genres = listOf("Crime", "Drama", "Music"),
            director = "Francis Ford Coppola",
            actors = "Richard Gere, Gregory Hines, Diane Lane, Lonette McKee",
            plot = "The Cotton Club was a famous night club in Harlem. The story follows the people that visited the club, those that ran it, and is peppered with the Jazz music that made it so famous.",
            posterUrl = "https://moviecovers.com/getjpg.html/COTTON%20CLUB.jpg"
        ),
        SingleMovieDto(
            id = 3,
            title = "The Shawshank Redemption",
            year = "1994",
            runtime = "142",
            genres = listOf("Crime", "Drama"),
            director = "Frank Darabont",
            actors = "Tim Robbins, Morgan Freeman, Bob Gunton, William Sadler",
            plot = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
            posterUrl = "https://moviecovers.com/getjpg.html/LES%20EVADES%20(1993).jpg"
        ),
        SingleMovieDto(
            id = 4,
            title = "Crocodile Dundee",
            year = "1986",
            runtime = "97",
            genres = listOf("Adventure", "Comedy"),
            director = "Peter Faiman",
            actors = "Paul Hogan, Linda Kozlowski, John Meillon, David Gulpilil",
            plot = "An American reporter goes to the Australian outback to meet an eccentric crocodile poacher and invites him to New York City.",
            posterUrl = "https://moviecovers.com/getjpg.html/CROCODILE%20DUNDEE.jpg"
        ),
        SingleMovieDto(
            id = 5,
            title = "Valkyrie",
            year = "2008",
            runtime = "121",
            genres = listOf("Drama", "History", "Thriller"),
            director = "Bryan Singer",
            actors = "Tom Cruise, Kenneth Branagh, Bill Nighy, Tom Wilkinson",
            plot = "A dramatization of the 20 July assassination and political coup plot by desperate renegade German Army officers against Hitler during World War II.",
            posterUrl = "https://moviecovers.com/getjpg.html/WALKYRIE.jpg"
        ),
        SingleMovieDto(
            id = 6,
            title = "Ratatouille",
            year = "2007",
            runtime = "111",
            genres = listOf("Animation", "Comedy", "Family"),
            director = "Brad Bird, Jan Pinkava",
            actors = "Patton Oswalt, Ian Holm, Lou Romano, Brian Dennehy",
            plot = "A rat who can cook makes an unusual alliance with a young kitchen worker at a famous restaurant.",
            posterUrl = "https://moviecovers.com/getjpg.html/RATATOUILLE.jpg"
        ),
        SingleMovieDto(
            id = 7,
            title = "City of God",
            year = "2002",
            runtime = "130",
            genres = listOf("Crime", "Drama"),
            director = "Fernando Meirelles, Kátia Lund",
            actors = "Alexandre Rodrigues, Leandro Firmino, Phellipe Haagensen, Douglas Silva",
            plot = "Two boys growing up in a violent neighborhood of Rio de Janeiro take different paths: one becomes a photographer, the other a drug dealer.",
            posterUrl = "https://moviecovers.com/getjpg.html/LA%20CITE%20DE%20DIEU.jpg"
        ),
        SingleMovieDto(
            id = 8,
            title = "Memento",
            year = "2000",
            runtime = "113",
            genres = listOf("Mystery", "Thriller"),
            director = "Christopher Nolan",
            actors = "Guy Pearce, Carrie-Anne Moss, Joe Pantoliano, Mark Boone Junior",
            plot = "A man juggles searching for his wife's murderer and keeping his short-term memory loss from being an obstacle.",
            posterUrl = "https://moviecovers.com/getjpg.html/MEMENTO.jpg"
        ),
        SingleMovieDto(
            id = 9,
            title = "The Intouchables",
            year = "2011",
            runtime = "112",
            genres = listOf("Biography", "Comedy", "Drama"),
            director = "Olivier Nakache, Eric Toledano",
            actors = "François Cluzet, Omar Sy, Anne Le Ny, Audrey Fleurot",
            plot = "After he becomes a quadriplegic from a paragliding accident, an aristocrat hires a young man from the projects to be his caregiver.",
            posterUrl = "https://moviecovers.com/getjpg.html/INTOUCHABLES%20(2011).jpg"
        )
    )
)

val NetflouxBackground = Color(0xFFf7d9d7)
val NetflouxTextColor = Color(0xFFEC7871)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Netfloux() {
    var searchTxt by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Netfloux") })
        }
    ) {

        padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(padding)
        ) {
            TextField(
                value = searchTxt,
                onValueChange = { newValue ->
                    searchTxt = newValue
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors().copy(
                    unfocusedContainerColor = NetflouxBackground.copy(alpha = 0.8f),
                    focusedContainerColor = NetflouxBackground.copy(alpha = 0.5f),
                    focusedTextColor = NetflouxTextColor,
                    focusedIndicatorColor = NetflouxTextColor,
                    unfocusedIndicatorColor = NetflouxTextColor,
                    focusedTrailingIconColor = NetflouxTextColor,
                    unfocusedTrailingIconColor = NetflouxTextColor,
                    focusedLeadingIconColor = NetflouxTextColor,
                    unfocusedLeadingIconColor = NetflouxTextColor,
                ),
                leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Search icon") },
                trailingIcon = {
                    if(searchTxt.isNotEmpty()) Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Search icon",
                        modifier = Modifier.clickable { searchTxt = "" }
                    )
                }
            )

            val data = movieData.movies
                .filter { it.title.contains(searchTxt, ignoreCase = true) }

            LazyColumn {
                items(data) {
                    data.forEach { movie ->
                        var infoDisplay by remember { mutableStateOf(false) }
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .padding(10.dp)
                        ) {
                            Row {
                                AsyncImage(
                                    model=movie.posterUrl, contentDescription = null,
                                )

                                Column(
                                    modifier = Modifier.padding(10.dp)
                                ) {

                                    Text(movie.title, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                                    Text(movie.director, color = Color.Black, fontSize = 16.sp)
                                    Text("Sortie: "+movie.year, color = Color.Black, fontSize = 16.sp)

                                    Button(onClick = {infoDisplay=!infoDisplay}, colors = ButtonColors(Color(0xFFF18D67)
                                    , Color.White,Color.Gray,Color.Gray)) {
                                        Icon(imageVector = Icons.Sharp.KeyboardArrowDown, contentDescription = "Search icon")
                                    }
                                    if(infoDisplay){
                                        Text("Acteurs: "+movie.actors, fontSize = 16.sp)
                                        Text("Synopsis: "+movie.plot,fontSize = 16.sp)
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun Nav(navController: NavController){
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            Netfloux(onScreenB = { navController.navigate("filmInfo") })
        }

        composable("filmInfo") {
            ScreenB(
                backToA = { navController.popBackStack("main", inclusive = false)}
            )
        }
    }
}