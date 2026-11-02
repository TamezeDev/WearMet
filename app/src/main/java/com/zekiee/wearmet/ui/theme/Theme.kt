package com.zekiee.wearmet.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnBrand,
    secondary = DarkSecondary,
    onSecondary = DarkOnBrand,
    tertiary = Highlight,
    onTertiary = DarkOnBrand,
    background = DarkBgMiddle,
    onBackground = DarkTextPrimary,
    surface = DarkSurfaceCard,
    onSurface = DarkTextPrimary,
    error = DarkRiskExtreme,
    onError = DarkOnBrand
)

private val DarkExtendedColors = ExtendedColors(
    backgroundBrush = Brush.verticalGradient(listOf(DarkBgTop, DarkBgMiddle, DarkBgBottom)),
    textSecondary = DarkTextSecondary,
    riskGood = DarkRiskGood,
    riskWarning = DarkRiskWarning,
    riskExtreme = DarkRiskExtreme
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnBrand,
    secondary = Secondary,
    onSecondary = OnBrand,
    tertiary = Highlight,
    onTertiary = OnBrand,
    background = BgMiddle,
    onBackground = TextPrimary,
    surface = SurfaceCard,
    onSurface = OnBrand,
    error = RiskExtreme,
    onError = TextPrimary
)

data class ExtendedColors(
    val backgroundBrush: Brush,
    val textSecondary: Color,
    val riskGood: Color,
    val riskWarning: Color,
    val riskExtreme: Color
)

private val WearMetExtendedColors = ExtendedColors(
    backgroundBrush = Brush.verticalGradient(listOf(BgTop, BgMiddle, BgBottom)),
    textSecondary = TextSecondary,
    riskGood = RiskGood,
    riskWarning = RiskWarning,
    riskExtreme = RiskExtreme
)

val LocalExtendedColors = staticCompositionLocalOf { WearMetExtendedColors }

val MaterialTheme.extendedColors: ExtendedColors
    @Composable get() = LocalExtendedColors.current

@Composable
fun WearMetTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val extended = if (darkTheme) DarkExtendedColors else WearMetExtendedColors

    CompositionLocalProvider(LocalExtendedColors provides extended) {
        MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
    }
}