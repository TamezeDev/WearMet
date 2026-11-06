package com.zekiee.wearmet.ui.components.weatherScreen.currentDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zekiee.wearmet.R
import com.zekiee.wearmet.domain.model.CurrentWeather
import com.zekiee.wearmet.ui.logicUI.weaather.WindArrow
import kotlin.math.roundToInt

@Composable
private fun ValueWithUnit(value: String, unit: String? = null) {
    Text(
        text = buildAnnotatedString {
            append(value)
            if (unit != null) {
                withStyle(SpanStyle(fontSize = 11.sp)) { append(" $unit") }
            }
        },
        style = MaterialTheme.typography.titleMedium,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
internal fun WindDetailCard(current: CurrentWeather, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 10.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    stringResource(R.string.detail_wind),
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    WindArrow(current.windSpeed, current.windDirection, Modifier.size(20.dp))
                    Spacer(Modifier.width(6.dp))
                    ValueWithUnit("${current.windSpeed.roundToInt()}")
                }
            }
            VerticalDivider(
                Modifier.padding(horizontal = 6.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    stringResource(R.string.detail_gusts),
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                ValueWithUnit(
                    "${current.windGusts.roundToInt()}",
                    stringResource(R.string.unit_kmh)
                )
            }
        }
    }
}