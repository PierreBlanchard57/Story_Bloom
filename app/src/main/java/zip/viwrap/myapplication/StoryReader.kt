package zip.viwrap.myapplication

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun StoryReader(story: Story,page:Int){
    Text(story.content.get(page-1))
}