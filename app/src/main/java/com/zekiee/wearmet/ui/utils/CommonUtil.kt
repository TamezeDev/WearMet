package com.zekiee.wearmet.ui.utils

import androidx.compose.ui.graphics.Brush
import com.zekiee.wearmet.ui.theme.EndGradientBg
import com.zekiee.wearmet.ui.theme.MiddleGradientBg
import com.zekiee.wearmet.ui.theme.StartGradientBg

class CommonUtil {
    companion object {
        val backgroundGradient = Brush.linearGradient(
            colors = listOf(
                StartGradientBg, MiddleGradientBg,
                EndGradientBg
            )
        )
    }
}