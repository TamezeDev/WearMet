package com.zekiee.wearmet.ui.components.searchScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zekiee.wearmet.ui.theme.WearMetTheme

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        contentAlignment = Alignment.TopCenter
    ){
        CircularProgressIndicator(color = MaterialTheme.colorScheme.surface)
    }
}

@Preview
@Composable
fun ShowLoadingItem(){
    WearMetTheme {
        LoadingItem()
    }
}