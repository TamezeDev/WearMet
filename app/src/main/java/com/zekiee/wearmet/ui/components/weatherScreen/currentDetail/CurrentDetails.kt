package com.zekiee.wearmet.ui.components.weatherScreen.currentDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zekiee.wearmet.R
import com.zekiee.wearmet.domain.model.CurrentWeather
import com.zekiee.wearmet.domain.model.DailyForecast
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.roundToInt

@Composable
internal fun CurrentDetails(current: CurrentWeather, today: DailyForecast?) {
    val timeFormat = remember { DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT) }
    val spacing = Arrangement.spacedBy(12.dp)

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = spacing) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = spacing) {
            WindDetailCard(current, Modifier.weight(1f))
            DetailCard(
                Modifier.weight(1f),
                R.string.detail_precipitation,
                "${current.precipitation} mm"
            ) {
                DetailIcon(R.drawable.ic_rain_drop)
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = spacing) {
            DetailCard(
                Modifier.weight(1f),
                R.string.detail_pressure,
                "${current.pressure.roundToInt()} hPa"
            ) {
                DetailIcon(R.drawable.ic_pressure)
            }
            DetailCard(Modifier.weight(1f), R.string.detail_clouds, "${current.cloudCover}%") {
                DetailIcon(R.drawable.ic_clouds)
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = spacing) {

            DetailCard(
                Modifier.weight(1f),
                R.string.detail_uv,
                today?.uvIndexMax?.roundToInt()?.toString() ?: "–"
            ) { DetailIcon(R.drawable.ic_uv) }
            DetailCard(Modifier.weight(1f), R.string.detail_humidity, "${current.humidity}%") {
                DetailIcon(R.drawable.ic_humidity)
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = spacing) {
            DetailCard(
                Modifier.weight(1f),
                R.string.detail_sunrise,
                today?.sunrise?.toLocalTime()?.format(timeFormat) ?: "–"
            ) { DetailIcon(R.drawable.ic_sunrise) }
            DetailCard(
                Modifier.weight(1f),
                R.string.detail_sunset,
                today?.sunset?.toLocalTime()?.format(timeFormat) ?: "–"
            ) { DetailIcon(R.drawable.ic_sunset) }
        }
    }
}
