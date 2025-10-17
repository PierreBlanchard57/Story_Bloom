package zip.viwrap.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import coil3.compose.AsyncImage
import kotlinx.serialization.json.Json
import zip.viwrap.myapplication.viewmodel.MovieState
import zip.viwrap.myapplication.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Netfloux(viewModel: MovieViewModel) {
    val navController = rememberNavController()

    // pour la movie on peut aussi utiliser un "remember" pour voir les changements.
    // Un "signal" peut exister à chaque changement d'une variable remember avec un "LaunchEffect"
    // c'est équivalent à un système de property listener (ou de "hook" en React).

    val movieState = viewModel.stateFlow.collectAsState().value

    Scaffold (
        topBar = {
            //TopAppBar(title = { Text("Netfloux") })
        }
    ) {
        padding ->
        // La taille de la topbar qui ne prend pas d'espace concret
        Column (modifier = Modifier.padding(padding)) {
            TitleBanner()
            NavHost(navController = navController, startDestination = "List") {
                composable("List") {
                    NetflouxList(movieState) {
                        movie -> viewModel.setSelectedMovie(movie)
                        navController.navigate("Synopsis")
                    }
                }
                composable("Synopsis") {
                    NetflouxSynopsis(movieState)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetflouxList(state: MovieState, onClickDetails: (movie: Movie) -> Unit) {
    var searchTxt by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.DarkGray)
    ) {
        TextField(value = searchTxt, onValueChange = { searchTxt = it }, modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Search icon") },
            trailingIcon = {
                if(searchTxt.isNotEmpty()) Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Search icon",
                    modifier = Modifier.clickable { searchTxt = "" }
                )
            }
        )

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val movies = state.movies
            val filtered = movies.filter {
                it.title.contains(searchTxt, ignoreCase = true)
            }

            LazyRow {
                items(filtered) {
                    movie ->
                    BookCard(movie, onClickDetails)
                }
            }
        }

    }
}

@Composable
fun NetflouxSynopsis(movieState: MovieState) {
    val movie = movieState.selectedMovie
    if (movie != null) {
        Column (modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Text(movie.title, fontSize = 20.sp)
            AsyncImage(
                model = movie.imageUrl,
                contentDescription = "Movie poster",
                Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
            Text(movie.plot, textAlign = TextAlign.Justify, modifier = Modifier.padding(8.dp))
            Box(modifier = Modifier
                .background(Color.Gray)
                .requiredHeight(1.dp)
                .fillMaxWidth())
            Text(movie.director, modifier = Modifier.padding(8.dp))
        }
    } else {
        Text("Aucun film à afficher.")
    }
}
