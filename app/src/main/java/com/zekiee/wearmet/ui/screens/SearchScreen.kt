package com.zekiee.wearmet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.zekiee.wearmet.ui.components.searchScreen.SearchTextField
import com.zekiee.wearmet.ui.utils.CommonUtil

@Composable
fun SearchScreen() {
    var searchText by rememberSaveable { mutableStateOf("") }

    Scaffold(containerColor = Color.Transparent) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(CommonUtil.backgroundGradient),
            contentAlignment = Alignment.TopCenter
        ) {
            SearchTextField(searchText) { searchText = it }
        }
    }
}

@Preview
@Composable
fun ShowSearchScreen() {
    SearchScreen()
}