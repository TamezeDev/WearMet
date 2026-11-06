package com.zekiee.wearmet.ui.components.weatherScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zekiee.wearmet.R
import com.zekiee.wearmet.domain.model.Weather
import com.zekiee.wearmet.domain.model.nextHours
import com.zekiee.wearmet.ui.components.weatherScreen.currentDetail.CollapsibleSection
import com.zekiee.wearmet.ui.components.weatherScreen.currentDetail.CurrentDetails
import com.zekiee.wearmet.ui.components.weatherScreen.currentDetail.CurrentWeatherSection
import com.zekiee.wearmet.ui.components.weatherScreen.dailyData.DailyForecastList
import com.zekiee.wearmet.ui.components.weatherScreen.hourlyData.HourlyCarousel

@Composable
fun WeatherContent(weather: Weather, modifier: Modifier = Modifier) {
    val now = weather.current.time
    val today = remember(weather) {
        weather.daily.firstOrNull { it.date == now.toLocalDate() } ?: weather.daily.first()
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { CurrentWeatherSection(weather.current, today) }
        item {
            CollapsibleSection(
                title = stringResource(R.string.details_title),
                initiallyExpanded = true
            ) {
                CurrentDetails(weather.current, today)
            }
        }
        item {
            CollapsibleSection(
                title = stringResource(R.string.hourly_title),
                initiallyExpanded = false
            ) {
                HourlyCarousel(weather.nextHours(), weather.daily)
            }
        }
        item {
            CollapsibleSection(
                title = stringResource(R.string.daily_title),
                initiallyExpanded = false
            ) {
                DailyForecastList(
                    daily = weather.daily,
                    hourly = weather.hourly,
                    now = now
                )
            }
        }
    }
}
