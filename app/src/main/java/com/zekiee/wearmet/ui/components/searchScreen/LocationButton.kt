package com.zekiee.wearmet.ui.components.searchScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zekiee.wearmet.R
import com.zekiee.wearmet.ui.theme.WearMetTheme

@Composable
fun LocationButton() {
    Button(
        onClick = {},

        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            stringResource(R.string.my_location),
            fontSize = 18.sp,
            modifier = Modifier.padding(
                top = 8.dp, bottom = 8.dp, start = 4.dp, end = 4.dp
            )
        )
    }
}

@Preview
@Composable
fun ShorLocationButton() {
    WearMetTheme {
        LocationButton()
    }
}