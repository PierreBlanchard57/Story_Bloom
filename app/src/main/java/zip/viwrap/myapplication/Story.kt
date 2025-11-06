package zip.viwrap.myapplication

import kotlinx.serialization.Serializable
@Serializable
data class StoryDto(
    val stories: List<SingleStoryDto>
)

@Serializable
data class SingleStoryDto(
    val title:String,
    val id:Int,
    val author:String,
    val coverImage:String,
    val pages:Int,
    val synopsis:String,
    val duration:Int,
    val minAge:Int,
    val maxAge:Int,
    val tags:List<String>,
    val content:List<String>,
    val imageContent:List<String>
)
data class Story(
    val title:String,
    val id:Int,
    val author:String,
    val coverImage:String,
    val pages:Int,
    val synopsis:String,
    val duration:Int,
    val minAge:Int,
    val maxAge:Int,
    val tags:List<String>,
    val content:List<String>,
    val imageContent:List<String>
)
