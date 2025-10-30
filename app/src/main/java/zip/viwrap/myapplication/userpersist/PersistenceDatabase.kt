package zip.viwrap.myapplication.userpersist

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import zip.viwrap.myapplication.BoolPreference
import zip.viwrap.myapplication.RangePreference
import zip.viwrap.myapplication.Preference
import java.util.concurrent.Executors
import kotlin.concurrent.Volatile

class PreferenceTypeConverter {
    @TypeConverter
    fun toJSON(value: Preference): String = Json.encodeToString(value)

    @TypeConverter
    fun toPreference(json: String): Preference = Json.decodeFromString<Preference>(json)
}

@TypeConverters(PreferenceTypeConverter::class)
@Database(version = 5, entities = [BookProgressDetail::class, PreferenceDetail::class], exportSchema = false)
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
            ).fallbackToDestructiveMigration(true)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                            super.onDestructiveMigration(db)
                            // données par défaut
                            Executors.newSingleThreadExecutor().execute {
                                val prefsDao = instance!!.getBookProgressDao()
                                prefsDao.insert(PreferenceDetail("notifications", BoolPreference(true)))
                                prefsDao.insert(PreferenceDetail("auto_read", BoolPreference(false)))
                                prefsDao.insert(PreferenceDetail("age_filter", RangePreference(5.0, 1.0, 18.0, 1.0)))
                            }
                        }
                    }
                ).build()
            instance!!
        }
    }
}

