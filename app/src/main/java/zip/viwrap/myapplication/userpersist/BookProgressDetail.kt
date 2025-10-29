package zip.viwrap.myapplication.userpersist

import androidx.room.*

@Entity(tableName = "bookProgressDetail")
data class BookProgressDetail(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    // Pour mesurer le niveau de l'utilisateur
    @ColumnInfo(name = "pages_read")
    val pagesRead: Int,
)