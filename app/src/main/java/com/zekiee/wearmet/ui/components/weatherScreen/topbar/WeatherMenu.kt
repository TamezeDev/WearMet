package com.zekiee.wearmet.ui.components.weatherScreen.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.zekiee.wearmet.R


@Composable
fun WeatherMenu(onOpenFavourites: () -> Unit, onOpenSettings: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        FilledIconButton(
            onClick = { expanded = true }) {
            Icon(Icons.Default.Menu, contentDescription = stringResource(R.string.alt_opt_menu))
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.favourites_locations)) },
                leadingIcon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                onClick = { expanded = false; onOpenFavourites() }
            )
            HorizontalDivider()
            DropdownMenuItem(
                text = { Text(stringResource(R.string.settings)) },
                leadingIcon = { Icon(Icons.Default.Settings, contentDescription = null) },
                onClick = { expanded = false; onOpenSettings() }
            )
        }
    }
}
