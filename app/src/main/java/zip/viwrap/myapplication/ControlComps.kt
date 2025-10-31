package zip.viwrap.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable
import zip.viwrap.myapplication.userpersist.BookProgressViewModel
import zip.viwrap.myapplication.userpersist.PreferenceDetail
import zip.viwrap.myapplication.userpersist.PreferenceStrings

val bgColor = Color(0xFFAC52DC)
val insetColor = Color(0xFF733296)

@Composable
fun WarningBar(text: String) {
    Box(modifier = Modifier
        .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
        .background(color = Color(0xFFFC7271))
        .fillMaxWidth()
        .requiredHeight(50.dp))
    {
        Box(modifier = Modifier.padding(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text, color = Color(0xFFFFFFFF), fontFamily = TitleFont.Family)
                Spacer(modifier = Modifier.weight(1f))
                Icon(painter = rememberVectorPainter(Icons.Default.Warning), contentDescription = "", tint = Color(0xFFFFFFFF),
                    modifier = Modifier.fillMaxHeight().aspectRatio(1f)
                )
            }
        }
    }
}

@Composable
fun LockedSliderEditor(name: String, desc: String) {
    Box(modifier = Modifier
        .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
        .background(color = bgColor)
        .fillMaxWidth())
    {
        Box(modifier = Modifier.padding(10.dp)) {
            Column {
                Row() {
                    Column (
                        modifier = Modifier.fillMaxHeight().weight(1f),
                        verticalArrangement = Arrangement.Top) {
                        Text(name, color = Color(0xFFFFFFFF), fontFamily = TitleFont.Family)
                        Text(desc, color = Color(0xFFD8B1E1), fontSize = 15.sp, lineHeight = 12.sp)
                    }
                    Spacer(modifier = Modifier)
                    Icon(painter = rememberVectorPainter(Icons.Outlined.Lock), contentDescription = "", tint = Color(0xFFFFFFFF),
                        modifier = Modifier.height(40.dp).aspectRatio(1f)
                    )
                }
                Row {
                    Text("Durée", color = Color.White)
                    Spacer(modifier = Modifier.weight(1f))
                    Text("30 min", color = Color.White, fontFamily = TitleFont.Family, fontSize = 20.sp)
                }
                Slider(
                    value = 0.4f,
                    onValueChange = { },
                    modifier = Modifier.height(30.dp)
                )
                Row {
                    Text("15 min", color = Color.White)
                    Spacer(modifier = Modifier.weight(1f))
                    Text("120 min", color = Color.White)
                }
            }
        }
    }
}

@Serializable
sealed class Preference

@Serializable
data class BoolPreference(
    val value: Boolean
) : Preference()

@Serializable
data class RangePreference(
    var value: Double,
    val lowerBound: Double = 0.0,
    val upperBound: Double = 1.0,
    val step: Double = 0.0,
) : Preference()

// Presque équivalent à un "inspecteur" de données.
// TODO: value va sans doute devenir un chemin vers une donnée
@Composable
fun PreferenceEditor(key: String, pref: Preference) {
    val meta = PreferenceStrings.strings[key]
    val painter = rememberVectorPainter(meta?.icon ?: Icons.Filled.Warning)
    Box(modifier = Modifier
        .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
        .background(color = bgColor)
        .fillMaxWidth()
        .requiredHeight(80.dp))
    {
        Box(modifier = Modifier.padding(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painter, contentDescription = "", tint = Color(0xFFFFFFFF),
                    modifier = Modifier.padding(3.dp).fillMaxHeight().aspectRatio(1f).background(insetColor)
                        .scale(0.7f)
                )
                Column (
                    modifier = Modifier.fillMaxHeight().weight(1f).padding(10.dp, 0.dp),
                    verticalArrangement = Arrangement.Center) {
                    Text(meta?.name ?: "NOM", color = Color(0xFFFFFFFF), fontFamily = TitleFont.Family)
                    Text(meta?.desc ?: "Description", color = Color(0xFFD8B1E1), fontSize = 15.sp, lineHeight = 12.sp)
                    if (pref is RangePreference) {
                        var sliderPosition by remember { mutableFloatStateOf(pref.value.toFloat()) }
                        Slider(
                            value = sliderPosition,
                            onValueChange = {
                                sliderPosition = it
                            },
                            onValueChangeFinished = {
                                val pvm = BookProgressViewModel.getViewModel()
                                pref.value = sliderPosition.toDouble()
                                pvm.updatePreference(PreferenceDetail(key, pref))
                            },
                            valueRange = pref.lowerBound.toFloat()..pref.upperBound.toFloat(),
                            modifier = Modifier.height(30.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier)
                if (pref is BoolPreference) {
                    Switch(
                        checked = pref.value,
                        onCheckedChange = { TODO() }
                    )
                }
            }
        }
    }
}
