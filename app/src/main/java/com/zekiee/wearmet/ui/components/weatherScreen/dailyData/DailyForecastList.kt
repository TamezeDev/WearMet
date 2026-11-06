package com.zekiee.wearmet.ui.components.weatherScreen.dailyData

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zekiee.wearmet.domain.model.DailyForecast
import com.zekiee.wearmet.domain.model.HourlyForecast
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Composable
fun DailyForecastList(
    daily: List<DailyForecast>,
    hourly: List<HourlyForecast>,
    now: LocalDateTime,
    modifier: Modifier = Modifier
) {
    val today = now.toLocalDate()
    val currentHour = now.truncatedTo(ChronoUnit.HOURS)
    val hoursByDay = remember(hourly) { hourly.groupBy { it.time.toLocalDate() } }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        daily.forEachIndexed { index, day ->
            val isToday = day.date == today
            val dayHours = hoursByDay[day.date].orEmpty().let { list ->
                if (isToday) list.filter { !it.time.isBefore(currentHour) } else list
            }
            DailyRow(day, isToday = isToday, hours = dayHours, daily = daily)
            if (index < daily.lastIndex) {
                HorizontalDivider(Modifier.padding(horizontal = 12.dp))
            }
        }
    }
}
