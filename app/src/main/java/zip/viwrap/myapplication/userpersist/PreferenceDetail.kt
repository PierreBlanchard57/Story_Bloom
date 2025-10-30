package zip.viwrap.myapplication.userpersist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import zip.viwrap.myapplication.Preference

data class PreferenceNameDescIcon (
    val icon: ImageVector,
    val name: String,
    val desc: String
)

object PreferenceStrings {
    val strings = mapOf(
        "notifications" to PreferenceNameDescIcon(Icons.Filled.Notifications,
            "Notifications", "Alerte de limite de temps"),
        "auto_read" to PreferenceNameDescIcon(Icons.Filled.Refresh,
            "Lecture automatique", "Passer automatiquement à la page suivante."),
        "age_filter" to PreferenceNameDescIcon(Icons.Filled.Warning,
            "Filtre d'age", "Afficher uniquement histoires adaptées."),
    )
}

@Entity(tableName = "preferenceDetail")
data class PreferenceDetail(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pref_key")
    val key: String,

    @ColumnInfo(name = "data")
    val data: Preference,
)
