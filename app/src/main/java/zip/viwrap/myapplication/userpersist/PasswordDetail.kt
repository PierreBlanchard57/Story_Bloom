package zip.viwrap.myapplication.userpersist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwordDetail")
data class PasswordDetail(
    // Le mot de passe des parents
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "password")
    val password: String,
)