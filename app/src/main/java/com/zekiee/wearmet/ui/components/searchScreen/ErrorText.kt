package com.zekiee.wearmet.ui.components.searchScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zekiee.wearmet.ui.state.GeoCodingState

@Composable
fun ErrorText(state: GeoCodingState.Error) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            stringResource(state.messageRes),
            color = MaterialTheme.colorScheme.onBackground
        )
    }

}