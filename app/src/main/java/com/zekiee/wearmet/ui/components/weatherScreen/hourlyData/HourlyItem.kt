package com.zekiee.wearmet.ui.components.weatherScreen.hourlyData

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zekiee.wearmet.R
import com.zekiee.wearmet.domain.model.HourlyForecast
import com.zekiee.wearmet.ui.logicUI.weaather.WeatherCondition
import com.zekiee.wearmet.ui.logicUI.weaather.WindArrow
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun HourlyItem(
    hour: HourlyForecast,
    isNow: Boolean,
    isDay: Boolean,
    timeFormat: DateTimeFormatter
) {
    val condition = WeatherCondition.fromCode(hour.weatherCode)

    Card(
        modifier = Modifier.width(76.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isNow) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.surface,
            contentColor = if (isNow) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = if (isNow) stringResource(R.string.now)
                else hour.time.toLocalTime().format(timeFormat),
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1
            )
            Image(
                painter = painterResource(condition.iconRes(isDay)),
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
            Text("${hour.temperature.roundToInt()}°", style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                WindArrow(hour.windSpeed, hour.windDirection, Modifier.size(16.dp))
                Spacer(Modifier.width(5.dp))
                Text("${hour.windSpeed.roundToInt()}", style = MaterialTheme.typography.labelSmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painterResource(R.drawable.ic_rain_drop), null, Modifier.size(14.dp))
                Spacer(Modifier.width(2.dp))
                Text("${hour.precipitationProbability}%", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}