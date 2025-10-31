package zip.viwrap.myapplication.userpersist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverter
import java.time.Instant
import java.util.Date

object DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

@Entity(tableName = "timespanDetail", primaryKeys = ["start", "stop"])
data class TimespanDetail(
    @ColumnInfo(name = "start")
    val start: Date = Date.from(Instant.now()),

    @ColumnInfo(name = "stop")
    var stop: Date = Date.from(Instant.now()),
)

