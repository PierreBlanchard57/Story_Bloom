package zip.viwrap.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import zip.viwrap.myapplication.ui.theme.ExampleApplicationTheme
import zip.viwrap.myapplication.viewmodel.MovieViewModel

// Référence :
// https://gitlab.intech.dev/paul.dardaine/netfloux-ihm

// npx json-server --watch app/src/main/java/zip/viwrap/myapplication/network/db.json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewmodel = MovieViewModel()
        setContent {
            //Tap()

            Netfloux(viewmodel)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExampleApplicationTheme {
        Greeting("Preview")
    }
}