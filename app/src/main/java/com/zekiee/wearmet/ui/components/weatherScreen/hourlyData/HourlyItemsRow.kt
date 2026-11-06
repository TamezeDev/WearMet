package com.zekiee.wearmet.ui.components.weatherScreen.hourlyData

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zekiee.wearmet.domain.model.DailyForecast
import com.zekiee.wearmet.domain.model.HourlyForecast
import com.zekiee.wearmet.domain.model.isDaytime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
internal fun HourlyItemsRow(
    hours: List<HourlyForecast>,
    daily: List<DailyForecast>,
    highlightFirst: Boolean,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val timeFormat = remember { DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT) }

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = contentPadding
    ) {
        itemsIndexed(hours, key = { _, hour -> hour.time.toString() }) { index, hour ->
            HourlyItem(
                hour = hour,
                isNow = highlightFirst && index == 0,
                isDay = daily.isDaytime(hour.time),
                timeFormat = timeFormat
            )
        }
    }
}