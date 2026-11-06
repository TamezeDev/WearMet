package com.zekiee.wearmet.ui.components.weatherScreen.topbar

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.zekiee.wearmet.R
import com.zekiee.wearmet.ui.theme.WearMetTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopBar(
    locationName: String,
    locationRegion: String,
    locationFlag: String,
    isFavourite: Boolean,
    onRefresh: () -> Unit,
    onToggleFavourite: () -> Unit,
    onOpenFavourites: () -> Unit,
    onOpenSettings: () -> Unit,
    onSearch: () -> Unit
) {
    val title: String = listOfNotNull(locationFlag, locationName).joinToString(" ")
    CenterAlignedTopAppBar(
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(
                    text = locationRegion,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = { WeatherMenu(onOpenFavourites, onOpenSettings) },
        actions = {
            IconButton(onClick = onToggleFavourite) {
                Icon(
                    imageVector = if (isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(
                        if (isFavourite) R.string.alt_remove_favourite else R.string.alt_add_favourite
                    )
                )
            }
            IconButton(onClick = onSearch) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = stringResource(R.string.alt_search_icon)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

@Preview
@Composable
fun ShowWeatherTopBar() {
    WearMetTheme {
        WeatherTopBar("Algeciras", "Andalucía", "🇪🇸", false, {}, {}, {}, {}, {})
    }
}