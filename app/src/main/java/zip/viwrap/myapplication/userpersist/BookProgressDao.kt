package zip.viwrap.myapplication.userpersist

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import zip.viwrap.myapplication.Preference

@Dao
interface BookProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progressDetail: BookProgressDetail)

    @Delete
    suspend fun delete(progressDetail: BookProgressDetail)

    @Query("SELECT * FROM bookProgressDetail WHERE id = :id")
    suspend fun getBookProgressDetail(id: Int): BookProgressDetail?

    @Query("SELECT pages_read FROM bookProgressDetail WHERE id = :id")
    fun getPagesRead(id: Int): Flow<Int>

    @Upsert
    suspend fun upsertBookProgressDetail(progressDetail: BookProgressDetail)

    // A appeler quand on feuillette un livre, met à jour le niveau aussi.
    suspend fun updateBookPage(id: Int, page: Int) {
        val read = getBookProgressDetail(id)?.pagesRead
        if (read == null || read < page)
            upsertBookProgressDetail(BookProgressDetail(id, page))
    }

    @Query("DELETE FROM bookProgressDetail WHERE id = :id")
    suspend fun deleteBookProgressDetail(id: Int)

    @Query("SELECT * FROM bookProgressDetail")
    suspend fun getAllBookProgressDetails(): List<BookProgressDetail?>

    @Query("SELECT SUM(pages_read) FROM bookProgressDetail")
    fun getLevel(): Flow<Int>

    @Query("SELECT * from preferenceDetail")
    fun getPreferences(): Flow<List<PreferenceDetail>>

    @Insert
    fun insert(prefDetail: PreferenceDetail)
}