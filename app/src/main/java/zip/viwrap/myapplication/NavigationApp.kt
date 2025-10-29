package zip.viwrap.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*
import coil3.compose.AsyncImage
import zip.viwrap.myapplication.viewmodel.StoryState
import zip.viwrap.myapplication.viewmodel.StoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Netfloux(viewModel: StoryViewModel) {
    val navController = rememberNavController()

    // pour la movie on peut aussi utiliser un "remember" pour voir les changements.
    // Un "signal" peut exister à chaque changement d'une variable remember avec un "LaunchEffect"
    // c'est équivalent à un système de property listener (ou de "hook" en React).

    val storyState = viewModel.stateFlow.collectAsState().value


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
                    StoryBloomList(storyState) {
                        story -> viewModel.setSelectedStory(story)
                        navController.navigate("Details")
                    }
                }
                composable("Details") {
                    StoryBloomDetails(storyState)
                }
                composable("Achievements") {
                    StoryBloomAchievements(storyState)
                }
                composable("Control") {
                    StoryBloomControl(storyState)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryBloomList(state: StoryState, onClickDetails: (story: Story) -> Unit) {
    var searchTxt by remember { mutableStateOf("") }
    var list = remember { mutableStateListOf<String>() }
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
                Text("Rechercher une histoire...", color = Color.Gray)
            }
        )
        Row {
            ClickableTag("Aventure",false, list)
            ClickableTag("Science",false, list)
            ClickableTag("Nature",false, list)
            ClickableTag("Fantasy",false, list)
            ClickableTag("Educatif",false, list)
            ClickableTag("Magie",false, list)
        }
        Row{
            Icon(Icons.Filled.PlayArrow,"",tint = Color(0xFF4CAF50))
            Text("Continuer la lecture")
        }
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val stories = state.stories
            val titleFiltered = stories.filter {
                it.title.contains(searchTxt, ignoreCase = true)
            }
            val tagFiltered=titleFiltered.filter {
                story->
                if(list.isEmpty())true
                else{
                    var ok=true
                    list.forEach(){
                        if(!story.tags.contains(it))ok=false
                    }
                    ok
                }
            }
            LazyRow {
                items(tagFiltered) {
                        story ->
                    StoryCard(story,0.6f,onClickDetails)
                }
            }
            Row{
                Icon(Icons.Filled.Star,"", tint = Color(0xFFFFC107));
                Text("Toutes les histoires")
            }
            LazyRow {
                items(tagFiltered) {
                        story ->
                    StoryCard(story,0.6f,onClickDetails)
                    //BookCard(story, onClickDetails)
                }
            }
        }

    }
}

@Composable
fun StoryBloomAchievements(state: StoryState) {
    LazyColumn (modifier = Modifier.fillMaxWidth().padding(10.dp), verticalArrangement =
        Arrangement.spacedBy(10.dp)) {
        item { Text("Succès") }
        item {
            val spacing = 10.dp
            val columns = 2
            FlowRow (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                val totalSpacing = spacing * (columns - 1)
                val w = (screenWidth - totalSpacing - 20.dp /*padding*/) / columns
                AchievementTile(w, painterResource(R.drawable.book), "99% de trucs.")
                AchievementTile(w, painterResource(R.drawable.book), "45 tomes de Vilieu lus.")
                AchievementTile(w, painterResource(R.drawable.book), "5 livres terminés.")
                AchievementTile(w, painterResource(R.drawable.book), "64% avant niveau 2.")
                AchievementTile(w, painterResource(R.drawable.book), "1 livre restant.")
            }
        }
        item { Text("Badges") }
        item {
            val spacing = 10.dp
            val columns = 4
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                val totalSpacing = spacing * (columns - 1)
                val w = (screenWidth - totalSpacing - 20.dp /* padding */) / columns
                BadgeTile(w, rememberVectorPainter(Icons.Default.Star), "Premiers pas")
                BadgeTile(w, rememberVectorPainter(Icons.Default.Face), "Dévoreur de livres")
                BadgeTile(w, rememberVectorPainter(Icons.Default.Lock), "Verrouillé")
            }
        }
        item { Text("En cours") }
        items(state.stories) {
            ReaderProgressTile(it)
        }
    }
}

@Composable
fun StoryBloomControl(movieState: StoryState) {
    LazyColumn (modifier = Modifier.fillMaxWidth().padding(10.dp), verticalArrangement =
        Arrangement.spacedBy(10.dp)) {
        item { AchievementTile(null, painterResource(R.drawable.book), "15min de temps de lecture aujourd'hui.") }
        item { WarningBar("15 mins restantes.") }
        item { Text("Paramètres de contrôle") }
        item { LockedSliderEditor("Limite quotidienne", "Temps max de lecture par jour") }
        item { PreferenceEditor(rememberVectorPainter(Icons.Filled.Notifications), "Notifications", "Alerte de limite de temps", 1) }
        item { PreferenceEditor(rememberVectorPainter(Icons.Filled.Refresh), "Lecture automatique", "Passer automatiquement à la page suivante.", 1) }
        item { PreferenceEditor(rememberVectorPainter(Icons.Filled.Warning), "Filtre d'age", "Afficher uniquement histoires adaptées.", 1) }
    }
}

@Composable
fun StoryBloomDetails(storyState: StoryState) {
    val movie = storyState.selectedStory
    if (movie != null) {
        Column (modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Text(movie.title, fontSize = 20.sp)
            AsyncImage(
                model = movie.coverImage,
                contentDescription = "Movie poster",
                Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
            Text(movie.synopsis, textAlign = TextAlign.Justify, modifier = Modifier.padding(8.dp))
            Box(modifier = Modifier
                .background(Color.Gray)
                .requiredHeight(1.dp)
                .fillMaxWidth())
            Text(movie.author, modifier = Modifier.padding(8.dp))
        }
    } else {
        Text("Aucun film à afficher.")
    }
}
