package zip.viwrap.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*
import coil3.compose.AsyncImage
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
        },
        bottomBar = {
            NavigationBar(listOf(
                NavigationItem(Icons.Outlined.Home, "Accueil", { navController.popBackStack(route = "List", inclusive = false) }),
                NavigationItem(Icons.Outlined.Check, "Succès", { navController.navigate("Achievements") { popUpTo("List") } }),
                NavigationItem(Icons.Outlined.Lock, "Contrôle", { navController.navigate("Control") { popUpTo("List") } })
            ))
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
                composable("Achievements") {
                    StoryBloomAchievements()
                }
                composable("Control") {
                    Text("Contrôle")
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
        .fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        TextField(value = searchTxt, onValueChange = { searchTxt = it }, modifier = Modifier
            .width(350.dp).clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp)) ,
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Search icon") },
            trailingIcon = {
                if(searchTxt.isNotEmpty()) Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Search icon",
                    modifier = Modifier.clickable { searchTxt = "" }
                )
            }, placeholder = {
                Text("Rechercher un histoire...", color = Color.Gray)
            }
        )
        Row {
            ClickableTag("Tous",true)
            ClickableTag("Aventure",false)
            ClickableTag("Science",false)
            ClickableTag("Nature",false)
            ClickableTag("Fantasy",false)
            ClickableTag("Educatif",false)
            ClickableTag("Magie",false)
        }
        Row{
            Icon(Icons.Filled.PlayArrow,"");
            Text("Continuer la lecture")
        }
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
            StoryCard(listOf("Aventure","Magie"),"Le titre","auteur",20,90,99,0.2f)
            LazyRow {

                //items(filtered) {
                //    movie ->
                //    BookCard(movie, onClickDetails)
               // }
            }
            Row{
                Icon(Icons.Filled.Star,"");
                Text("Toutes les histoires")
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
fun StoryBloomAchievements() {
    Column (modifier = Modifier.fillMaxSize().padding(10.dp)) {
        Text("Succès")
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item { AchievementTile(painterResource(R.drawable.book), "99% de trucs.") }
            item { AchievementTile(painterResource(R.drawable.book), "45 tomes de Vilieu lus.") }
            item { AchievementTile(painterResource(R.drawable.book), "5 livres terminés.") }
            item { AchievementTile(painterResource(R.drawable.book), "64% avant niveau 2.") }
            item { AchievementTile(painterResource(R.drawable.book), "1 livre restant.") }
        }
        Text("Badges")

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
