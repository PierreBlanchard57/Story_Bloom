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

    @Query("SELECT COUNT(*) FROM bookProgressDetail")
    fun getBooksStarted(): Flow<Int>

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

    @Update
    fun update(prefDetail: PreferenceDetail)

    @Query("SELECT data FROM preferenceDetail WHERE pref_key = :key")
    fun getPreference(key: String): Flow<Preference?>

    @Query("INSERT INTO timespanDetail(start, stop) VALUES(CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)")
    fun startTimespan()

    @Query("UPDATE timespanDetail SET stop = CURRENT_TIMESTAMP WHERE start = (SELECT start FROM timespanDetail ORDER BY start DESC LIMIT 1)")
    fun stopTimespan()

    @Query("SELECT SUM(unixepoch(stop) - unixepoch(start)) FROM timespanDetail WHERE stop BETWEEN datetime(date()) AND datetime()")
    fun getTimespanSecondsToday(): Flow<Int>

    @Query("SELECT SUM(unixepoch(stop) - unixepoch(start)) FROM timespanDetail")
    fun getTimespanSecondsAllTime(): Flow<Int>

    @Query("SELECT * from passwordDetail LIMIT 1")
    fun hasPasswordDefined(): Flow<PasswordDetail?>

    @Upsert
    fun upsert(passwordDetail: PasswordDetail)
}