package zip.viwrap.myapplication

import android.os.AsyncTask
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import zip.viwrap.myapplication.userpersist.BookProgressViewModel
import zip.viwrap.myapplication.viewmodel.StoryState
import zip.viwrap.myapplication.viewmodel.StoryViewModel
import kotlin.concurrent.thread
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryBloom() {
    val navController = rememberNavController()

    // pour la movie on peut aussi utiliser un "remember" pour voir les changements.
    // Un "signal" peut exister à chaque changement d'une variable remember avec un "LaunchEffect"
    // c'est équivalent à un système de property listener (ou de "hook" en React).
    val viewModel: StoryViewModel= viewModel()
    val storyState = viewModel.storiesStateFlow.collectAsState().value

    val dbvm = BookProgressViewModel.getViewModel()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    var currentPage by remember { mutableStateOf(0) }

    Scaffold (
        topBar = {
            when (currentRoute){
                "List", "Achievements", "Control",null -> TitleBanner()
                else -> {
                    Row(modifier = Modifier.background(Color(0xFF9C27B0)).fillMaxWidth().padding(0.dp,20.dp,0.dp,0.dp)) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "", tint = Color.White,
                            modifier=Modifier.clickable(){
                                if(dbvm.isTimeSpanStarted)  dbvm.stopTimespan();println("timespan stopped!")
                                navController.popBackStack(route = "List", inclusive = false)}
                                .size(40.dp))
                        Text("LECTURE", color = Color.White,
                            modifier = Modifier.padding(100.dp,
                                0.dp,0.dp,0.dp), fontSize = 40.sp)
                    }
                }
            }
        },
        bottomBar = {
            when(currentRoute){
                "List", "Achievements", "Control",null ->{
                    NavigationBar(listOf(
                        NavigationItem(Icons.Outlined.Home, "Accueil", { navController.popBackStack(route = "List", inclusive = false) }),
                        NavigationItem(Icons.Outlined.Check, "Succès", { navController.navigate("Achievements") { popUpTo("List") } }),
                        NavigationItem(Icons.Outlined.Lock, "Contrôle", { navController.navigate("Control") { popUpTo("List") } })
                    ))
                }
                "StoryReader"->{
                    PageButtons(requireNotNull(storyState.selectedStory),currentPage,{
                        if(currentPage>1)currentPage--
                    },{
                        if(currentPage<storyState.selectedStory.pages) {
                            currentPage++
                            if(currentPage>dbvm.getPagesRead(storyState.selectedStory.id).value)
                                dbvm.updateBookPage(storyState.selectedStory.id,currentPage)
                        }
                    })
                }
                else->{
                    //si autre,on affiche pas la botombar
                }
            }
        }
    ) {
            padding ->
        // La taille de la topbar qui ne prend pas d'espace concret
        Column (modifier = Modifier.padding(padding)) {

            NavHost(navController = navController, startDestination = "List") {
                composable("List") {
                    StoryBloomList(storyState) {
                            story -> viewModel.setSelectedStory(story)
                        navController.navigate("Details")
                    }
                }
                composable("Details") {
                    StoryBloomDetails(storyState){
                    story->
                        val dbvm = BookProgressViewModel.getViewModel()
                        val pagesRead =dbvm.getPagesRead(storyState.selectedStory?.id ?: 0).value
                        currentPage=pagesRead+1
                        navController.navigate("StoryReader");
                    }
                }
                composable("Achievements") {
                    StoryBloomAchievements(storyState)
                }
                composable("Control") {
                    StoryBloomControl(storyState)
                }
                composable("StoryReader"){
                    StoryReader(requireNotNull(storyState.selectedStory),currentPage)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryBloomList(storyState: StoryState, onClickDetails: (story: Story) -> Unit) {
    var searchTxt by remember { mutableStateOf("") }
    var list = remember { mutableStateListOf<String>() }
    val dbvm = BookProgressViewModel.getViewModel()

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

        if (storyState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val stories = storyState.stories
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
            val pagesMap = stories.associate { story ->
                val pages = produceState(initialValue = 0, story.id) {
                    dbvm.getPagesRead(story.id).collect { value = it }
                }.value
                story.id to pages
            }
            val continueFiltered = remember(stories, pagesMap) {
                stories.filter { story ->
                    val pages = pagesMap[story.id] ?: 0
                    pages != 0 && pages != story.pages
                }
            }

            Row{
                Icon(Icons.Filled.PlayArrow,"",tint = Color(0xFF4CAF50))
                Text("Continuer la lecture")
            }
            LazyRow {

                items(continueFiltered) {
                        story ->
                    StoryCard(story,onClickDetails)
                }
            }
            Row{
                Icon(Icons.Filled.Star,"", tint = Color(0xFFFFC107));
                Text("Toutes les histoires")
            }
            LazyRow {
                items(tagFiltered) {
                        story ->
                    StoryCard(story,onClickDetails)
                    //BookCard(story, onClickDetails)
                }
            }
        }

    }
}

@Composable
fun StoryBloomAchievements(state: StoryState) {
    var pvm = BookProgressViewModel.getViewModel()

    val booksStarted by pvm.booksStarted.collectAsState()

    val level by pvm.levelState.collectAsState()
    val progress = (calculateLevelProgress(level) * 100).toInt()
    val nextLevel = getLevelBucket(level) + 1

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

                // Compte des livres lus
                AchievementTile(w, painterResource(R.drawable.book), "$booksStarted livres commencés.")

                // Compte total de pages lues
                val pagesRead by pvm.levelState.collectAsState()
                AchievementTile(w, painterResource(R.drawable.book), "$pagesRead pages lues au total.")

                // Progression précise
                AchievementTile(w, painterResource(R.drawable.book), "$progress% avant niveau $nextLevel.")

                AchievementTile(w, painterResource(R.drawable.book), "99% de trucs.")
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
                val w = ((screenWidth - totalSpacing - 20.dp /* padding */) / columns) - 1.dp
                BadgeTile(w, rememberVectorPainter(Icons.Default.Star), "Premiers pas", booksStarted >= 1)
                BadgeTile(w, rememberVectorPainter(Icons.Default.Face), "Dévoreur de livres", booksStarted >= 5)
                BadgeTile(w, rememberVectorPainter(Icons.Default.FavoriteBorder), "Novice", nextLevel >= 4)
                BadgeTile(w, rememberVectorPainter(Icons.Default.Favorite), "Lecteur", nextLevel >= 9)
            }
        }
        item { Text("En cours") }
        items(state.stories) {
            ReaderProgressTile(it)
        }
    }
}

@Composable
fun StoryBloomControl(storyState: StoryState) {
    val settingsVM = BookProgressViewModel.getViewModel()
    val prefs by settingsVM.preferences.collectAsState()
    val seconds by settingsVM.getSecondsSpent().collectAsState()
    val mins = (seconds / 60)
    LazyColumn (modifier = Modifier.fillMaxWidth().padding(10.dp), verticalArrangement =
        Arrangement.spacedBy(10.dp)) {
        item {
            AchievementTile(null, painterResource(R.drawable.book), mins.toString() + "min de temps de lecture aujourd'hui.")
        }
        item {
            val minutesState by settingsVM.getTimeLimit().collectAsState()
            val minsLimit = (minutesState as RangePreference).value.roundToInt()
            WarningBar((minsLimit - mins).toString() + " mins restantes.")
        }
        item { Text("Paramètres de contrôle") }
        items(prefs) {
            if (it.data is RangePreference)
                LockedSliderEditor(it.key, it.data)
            else
                PreferenceEditor(it.key, it.data)
        }
    }
}

@Composable
fun StoryBloomDetails(storyState: StoryState,onPlay:(Story)->Unit) {
    val story:Story = requireNotNull(storyState.selectedStory)

    val scrollState = rememberScrollState()

    val dbvm = BookProgressViewModel.getViewModel()
    val pagesRead by dbvm.getPagesRead(story.id).collectAsState()
    val progress = if (story.pages > 0) pagesRead.toFloat() / story.pages else 0f

    Column (modifier = Modifier.fillMaxSize().padding(20.dp).verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally) {

        AsyncImage(
            model = story.coverImage,
            contentDescription = "Movie poster",
            Modifier.fillMaxWidth().height(300.dp),
            contentScale = ContentScale.Fit
        )
        Text(story.title, fontSize = 20.sp)
        Row (horizontalArrangement = Arrangement.spacedBy(5.dp)){
            story.tags.forEach {
                NormalTag(it)
            }
        }
        Text(story.synopsis, textAlign = TextAlign.Justify, modifier = Modifier.padding(8.dp))
        ReadButton(story,onPlay)
        Row{
            Icon(Icons.Rounded.Info,"")
            Text("${story.duration} min",color = Color.Gray, modifier = Modifier.padding(0.dp,0.dp,10.dp,0.dp))
            Icon(Icons.Rounded.AccountCircle,"")
            Text("${story.minAge}-${story.maxAge} ans",color = Color.Gray)
        }
        LinearProgressIndicator(
            progress = { progress },
            color = Color(0xFF9C27B0),
            gapSize = 0.dp,
            strokeCap = StrokeCap.Butt,
            modifier = Modifier.padding(10.dp,10.dp,10.dp,10.dp).height(10.dp),
            drawStopIndicator = {}
        )

        Text("${(progress*100).toInt()} %", fontSize = 20.sp)
    }
}
