package zip.viwrap.myapplication.userpersist

import android.content.Context
import androidx.room.*
import kotlin.concurrent.Volatile

@Database(version = 1, entities = [BookProgressDetail::class], exportSchema = false)
abstract class UserPersistenceDatabase : RoomDatabase() {
    abstract fun getBookProgressDao(): BookProgressDao
}

object UserPersistenceProvider {

    @Volatile
    private var instance: UserPersistenceDatabase? = null

    fun getDatabase(context: Context): UserPersistenceDatabase {
        return instance ?: synchronized(this) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                UserPersistenceDatabase::class.java,
                "user_persistence_db"
            ).build()
            instance!!
        }
    }
}

