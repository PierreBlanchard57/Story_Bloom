package zip.viwrap.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import zip.viwrap.myapplication.ui.theme.ExampleApplicationTheme
import zip.viwrap.myapplication.userpersist.BookProgressViewModel
import zip.viwrap.myapplication.userpersist.UserPersistenceProvider
import zip.viwrap.myapplication.viewmodel.StoryViewModel

// Référence :
// https://gitlab.intech.dev/paul.dardaine/netfloux-ihm

// npx json-server --watch app/src/main/java/zip/viwrap/myapplication/network/db.json

object DefaultFont {
    val Family = FontFamily(
        Font(R.font.inter_variable_font),
        Font(R.font.inter_italic_variable_font),
    )
}

private val defaultTypography = Typography()
val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = DefaultFont.Family),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = DefaultFont.Family),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = DefaultFont.Family),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = DefaultFont.Family),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = DefaultFont.Family),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = DefaultFont.Family),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = DefaultFont.Family),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = DefaultFont.Family),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = DefaultFont.Family),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = DefaultFont.Family),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = DefaultFont.Family),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = DefaultFont.Family),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = DefaultFont.Family),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = DefaultFont.Family),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = DefaultFont.Family)
)

object TitleFont {
    val Family = FontFamily(Font(R.font.inter_black_font))
}

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = Typography,
        content = content
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialisation du viewmodel des données du serveur.

        // Initialisation du viewmodel des données persistentes.
        val db = UserPersistenceProvider.getDatabase(applicationContext)
        val dao = db.getBookProgressDao()
        val dbviewmodel = BookProgressViewModel.getViewModel(dao)

        setContent {
            AppTheme {
                StoryBloom()
            }
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