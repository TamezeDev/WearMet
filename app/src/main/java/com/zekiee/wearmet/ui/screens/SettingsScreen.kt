package com.zekiee.wearmet.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zekiee.wearmet.R
import com.zekiee.wearmet.domain.model.Location
import com.zekiee.wearmet.local.data.dataStore.AppSettings
import com.zekiee.wearmet.local.data.dataStore.SplashDuration
import com.zekiee.wearmet.local.data.dataStore.UpdateInterval
import com.zekiee.wearmet.ui.components.settings.SettingSwitchRow
import com.zekiee.wearmet.ui.components.settings.SettingsSection
import com.zekiee.wearmet.ui.theme.WearMetTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settings: AppSettings,
    favourites: List<Location>,
    onBack: () -> Unit,
    onSplashEnabledChange: (Boolean) -> Unit,
    onSplashDurationChange: (SplashDuration) -> Unit,
    onAutoOpenChange: (Boolean) -> Unit,
    onPreferredChange: (Location) -> Unit,
    onUpdateIntervalChange: (UpdateInterval) -> Unit
) {
    val hasFavourites = favourites.isNotEmpty()
    val preferred = favourites.firstOrNull { it.id == settings.preferredLocation?.id }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.settings_back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            SettingsSection(stringResource(R.string.settings_section_start)) {
                SettingSwitchRow(
                    title = stringResource(R.string.settings_splash),
                    checked = settings.splashEnabled,
                    onCheckedChange = onSplashEnabledChange
                )
                if (settings.splashEnabled) {
                    SingleChoiceSegmentedButtonRow(
                        Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    ) {
                        SplashDuration.entries.forEachIndexed { index, option ->
                            SegmentedButton(
                                selected = settings.splashDuration == option,
                                onClick = { onSplashDurationChange(option) },
                                shape = SegmentedButtonDefaults.itemShape(
                                    index, SplashDuration.entries.size
                                )
                            ) {
                                Text(
                                    stringResource(
                                        if (option == SplashDuration.SHORT)
                                            R.string.settings_splash_short
                                        else R.string.settings_splash_normal
                                    )
                                )
                            }
                        }
                    }
                }
                SettingSwitchRow(
                    title = stringResource(R.string.settings_auto_open),
                    description = stringResource(R.string.settings_auto_open_desc),
                    checked = settings.autoOpenPreferred && preferred != null,
                    onCheckedChange = onAutoOpenChange,
                    enabled = preferred != null
                )
            }

            SettingsSection(stringResource(R.string.settings_section_widget)) {
                if (!hasFavourites) {
                    Text(
                        stringResource(R.string.settings_no_favourites),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                PreferredLocationPicker(
                    selected = preferred,
                    favourites = favourites,
                    onSelect = onPreferredChange
                )
                Text(
                    stringResource(R.string.settings_update_interval),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 4.dp)
                )
                SingleChoiceSegmentedButtonRow(
                    Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
                    UpdateInterval.entries.forEachIndexed { index, option ->
                        SegmentedButton(
                            selected = settings.updateInterval == option,
                            onClick = { onUpdateIntervalChange(option) },
                            shape = SegmentedButtonDefaults.itemShape(
                                index, UpdateInterval.entries.size
                            ),
                            enabled = preferred != null
                        ) {
                            Text(
                                stringResource(
                                    R.string.settings_interval_hours, option.hours.toInt()
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PreferredLocationPicker(
    selected: Location?,
    favourites: List<Location>,
    onSelect: (Location) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val label = selected?.let { "${it.flag} ${it.name}" }
        ?: stringResource(R.string.settings_preferred_none)

    Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            stringResource(R.string.settings_preferred),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable(enabled = favourites.isNotEmpty()) { expanded = true }
                .padding(vertical = 8.dp)
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            favourites.forEach { location ->
                DropdownMenuItem(
                    text = { Text("${location.flag} ${location.name}") },
                    onClick = {
                        onSelect(location)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShowSettingsScreen() {
    val algeciras = Location(1, "Algeciras", 36.13, -5.45, "ES", "Andalucía")
    val madrid = Location(2, "Madrid", 40.41, -3.70, "ES", "Madrid")

    WearMetTheme {
        SettingsScreen(
            settings = AppSettings(preferredLocation = algeciras),
            favourites = listOf(algeciras, madrid),
            onBack = {},
            onSplashEnabledChange = {},
            onSplashDurationChange = {},
            onAutoOpenChange = {},
            onPreferredChange = {},
            onUpdateIntervalChange = {}
        )
    }
}