package com.zekiee.wearmet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.zekiee.wearmet.ui.theme.EndGradientBg
import com.zekiee.wearmet.ui.theme.MiddleGradientBg
import com.zekiee.wearmet.ui.theme.StartGradientBg

@Composable
fun CurrentInfo() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(backgroundGradient))
}

@Preview
@Composable
fun ShowCurrentInfo() {
    CurrentInfo()
}

val backgroundGradient = Brush.linearGradient(
    colors = listOf(
        StartGradientBg, MiddleGradientBg,
        EndGradientBg
    )
)