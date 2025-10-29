package zip.viwrap.myapplication.userpersist

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progressDetail: BookProgressDetail)

    @Delete
    suspend fun update(progressDetail: BookProgressDetail)

    @Query("SELECT * FROM bookProgressDetail WHERE id = :id")
    suspend fun getBookProgressDetail(id: Int): BookProgressDetail?

    @Query("DELETE FROM bookProgressDetail WHERE id = :id")
    suspend fun deleteBookProgressDetail(id: Int)

    @Query("SELECT * FROM bookProgressDetail")
    suspend fun getAllBookProgressDetails(): List<BookProgressDetail?>

    @Query("SELECT SUM(pages_read) FROM bookProgressDetail")
    fun getLevel(): Flow<Int>
}