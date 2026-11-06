package com.zekiee.wearmet.ui.components.weatherScreen.currentDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.zekiee.wearmet.R
import com.zekiee.wearmet.domain.model.CurrentWeather
import com.zekiee.wearmet.domain.model.DailyForecast
import com.zekiee.wearmet.ui.logicUI.weaather.WeatherCondition
import com.zekiee.wearmet.ui.theme.extendedColors
import kotlin.math.roundToInt

@Composable
fun CurrentWeatherSection(
    current: CurrentWeather,
    today: DailyForecast?,
    modifier: Modifier = Modifier
) {
    val condition = WeatherCondition.fromCode(current.weatherCode)
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(condition.lottieRes(current.isDay))
    )

    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(140.dp)
        )
        Text(
            "${current.temperature.roundToInt()}°",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            stringResource(condition.labelRes),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            stringResource(R.string.feels_like, current.feelsLike.roundToInt()),
            color = MaterialTheme.extendedColors.textSecondary
        )
        if (today != null) {
            Text(
                stringResource(R.string.max_min, today.temperatureMax.roundToInt(), today.temperatureMin.roundToInt()),
                color = MaterialTheme.extendedColors.textSecondary
            )
        }
    }
}