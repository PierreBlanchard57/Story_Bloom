package zip.viwrap.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable
import zip.viwrap.myapplication.userpersist.BookProgressViewModel
import zip.viwrap.myapplication.userpersist.PreferenceDetail
import zip.viwrap.myapplication.userpersist.PreferenceStrings
import java.text.DecimalFormat
import java.text.NumberFormat

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
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                )
            }
        }
    }
}

@Composable
fun LockedSliderEditor(key: String, pref: RangePreference) {
    val meta = PreferenceStrings.strings[key]
    val painter = rememberVectorPainter(meta?.icon ?: Icons.Filled.Warning)
    val nf: NumberFormat = DecimalFormat.getInstance()
    nf.maximumFractionDigits = 0
    Box(modifier = Modifier
        .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
        .background(color = bgColor)
        .fillMaxWidth())
    {
        Box(modifier = Modifier.padding(10.dp)) {
            Column {
                Row() {
                    Column (
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f),
                        verticalArrangement = Arrangement.Top) {
                        Text(meta?.name ?: "NOM", color = Color(0xFFFFFFFF), fontFamily = TitleFont.Family)
                        Text(meta?.desc ?: "Description", color = Color(0xFFD8B1E1), fontSize = 15.sp, lineHeight = 12.sp)
                    }
                    Spacer(modifier = Modifier)
                    Icon(painter = painter, contentDescription = "", tint = Color(0xFFFFFFFF),
                        modifier = Modifier
                            .height(40.dp)
                            .aspectRatio(1f)
                    )
                }
                var sliderPosition by remember { mutableFloatStateOf(pref.value.toFloat()) }
                val active = nf.format(sliderPosition) + " " + pref.unit
                Row {
                    Text("Défini à", color = Color.White)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(active, color = Color.White, fontFamily = TitleFont.Family, fontSize = 20.sp)
                }
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
                    steps = ((pref.upperBound - pref.lowerBound) / pref.step).toInt() - 1,
                    valueRange = pref.lowerBound.toFloat()..pref.upperBound.toFloat(),
                    modifier = Modifier.height(30.dp),
                    colors = SliderDefaults.colors(activeTickColor = Color.Transparent, inactiveTickColor = Color.Transparent)
                )
                Row {
                    Text(nf.format(pref.lowerBound) + " " + pref.unit, color = Color.White)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(nf.format(pref.upperBound) + " " + pref.unit, color = Color.White)
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
    val unit: String = ""
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
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .background(insetColor)
                        .scale(0.7f)
                )
                Column (
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(10.dp, 0.dp),
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
                            steps = ((pref.upperBound - pref.lowerBound) / pref.step).toInt() - 1,
                            valueRange = pref.lowerBound.toFloat()..pref.upperBound.toFloat(),
                            modifier = Modifier.height(30.dp),
                            colors = SliderDefaults.colors(activeTickColor = Color.Transparent, inactiveTickColor = Color.Transparent)
                        )
                    }
                }
                Spacer(modifier = Modifier)
                if (pref is BoolPreference) {
                    var checkedState by remember { mutableStateOf(pref.value) }
                    Switch(
                        checked = checkedState,
                        onCheckedChange = {
                            checkedState = it
                            val pvm = BookProgressViewModel.getViewModel()
                            pvm.updatePreference(PreferenceDetail(key, BoolPreference(checkedState)))
                        }
                    )
                }
            }
        }
    }
}
