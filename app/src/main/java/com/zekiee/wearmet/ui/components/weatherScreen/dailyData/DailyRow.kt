package com.zekiee.wearmet.ui.components.weatherScreen.dailyData

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.zekiee.wearmet.R
import com.zekiee.wearmet.domain.model.DailyForecast
import com.zekiee.wearmet.domain.model.HourlyForecast
import com.zekiee.wearmet.ui.components.weatherScreen.hourlyData.HourlyItemsRow
import com.zekiee.wearmet.ui.logicUI.weaather.WeatherCondition
import java.time.format.TextStyle
import kotlin.math.roundToInt
import androidx.compose.ui.platform.LocalLocale

@Composable
fun DailyRow(
    day: DailyForecast, isToday: Boolean, hours: List<HourlyForecast>, daily: List<DailyForecast>
) {
    var expanded by rememberSaveable(day.date.toString()) { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (expanded) 180f else 0f, label = "chevron")
    val condition = WeatherCondition.fromCode(day.weatherCode)
    val mutedColor = LocalContentColor.current.copy(alpha = 0.6f)
    val dayName = if (isToday) stringResource(R.string.today)
    else day.date.dayOfWeek.getDisplayName(TextStyle.SHORT, LocalLocale.current.platformLocale)
        .replaceFirstChar { it.uppercase() }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = hours.isNotEmpty()) { expanded = !expanded }
                .padding(horizontal = 8.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                dayName,
                Modifier.width(44.dp),
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1
            )
            Image(painterResource(condition.iconRes(true)), null, Modifier.size(32.dp))
            Spacer(Modifier.width(6.dp))
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painterResource(R.drawable.ic_rain_drop), null, Modifier.size(14.dp))
                    Spacer(Modifier.width(3.dp))
                    Text(
                        "${day.precipitationProbabilityMax}%",
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        softWrap = false
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(R.drawable.ic_wind),
                        null,
                        Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        "${day.windSpeedMax.roundToInt()} km/h",
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        softWrap = false
                    )
                }
            }
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                        append("${day.temperatureMax.roundToInt()}°")
                    }
                    append("  ")
                    withStyle(SpanStyle(color = mutedColor)) {
                        append("${day.temperatureMin.roundToInt()}°")
                    }
                }, style = MaterialTheme.typography.bodyLarge, maxLines = 1
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown, contentDescription = stringResource(
                    if (expanded) R.string.alt_collapse_hours else R.string.alt_expand_hours
                ), modifier = Modifier
                    .size(20.dp)
                    .rotate(rotation)
            )
        }
        AnimatedVisibility(visible = expanded) {
            HourlyItemsRow(
                hours = hours,
                daily = daily,
                highlightFirst = isToday,
                modifier = Modifier.padding(bottom = 12.dp),
                contentPadding = PaddingValues(horizontal = 12.dp)
            )
        }
    }
}