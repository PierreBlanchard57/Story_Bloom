package zip.viwrap.myapplication

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
)
