package com.zekiee.wearmet.ui.components.searchScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zekiee.wearmet.domain.model.Location
import com.zekiee.wearmet.ui.theme.WearMetTheme


@Composable
fun LocationList(locations: List<Location>, onClick: (Double, Double) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = locations, key = { it.id }) { location ->
            LocationItem(
                location = location,
                onClick = onClick
            )
        }
    }
}

@Preview
@Composable
fun ShowLocationList() {
    WearMetTheme {
        LocationList(previewLocations, onClick = { _, _ -> })
    }
}


val previewLocations = listOf(
    previewLocation,
    previewLocation.copy(id = 2, name = "Los Barrios", latitude = 36.53, longitude = -6.29)
)