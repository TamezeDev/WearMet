package com.zekiee.wearmet.local.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zekiee.wearmet.domain.model.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsStore(context: Context) {

    private val dataStore = context.applicationContext.settingsDataStore

    internal object Keys {
        val splashEnabled = booleanPreferencesKey("splash_enabled")
        val splashDuration = stringPreferencesKey("splash_duration")
        val autoOpenPreferred = booleanPreferencesKey("auto_open_preferred")
        val updateInterval = stringPreferencesKey("update_interval")
        val prefId = intPreferencesKey("preferred_id")
        val prefName = stringPreferencesKey("preferred_name")
        val prefRegion = stringPreferencesKey("preferred_region")
        val prefCountry = stringPreferencesKey("preferred_country")
        val prefLat = doublePreferencesKey("preferred_lat")
        val prefLon = doublePreferencesKey("preferred_lon")
    }

    val settings: Flow<AppSettings> = dataStore.data.map { prefs ->
        AppSettings(
            splashEnabled = prefs[Keys.splashEnabled] ?: true,
            splashDuration = prefs[Keys.splashDuration].toEnum(SplashDuration.NORMAL),
            autoOpenPreferred = prefs[Keys.autoOpenPreferred] ?: false,
            updateInterval = prefs[Keys.updateInterval].toEnum(UpdateInterval.H3),
            preferredLocation = prefs.readPreferred()
        )
    }

    suspend fun get(): AppSettings = settings.first()

    suspend fun setSplashEnabled(value: Boolean) = dataStore.edit { it[Keys.splashEnabled] = value }

    suspend fun setSplashDuration(value: SplashDuration) =
        dataStore.edit { it[Keys.splashDuration] = value.name }

    suspend fun setAutoOpenPreferred(value: Boolean) =
        dataStore.edit { it[Keys.autoOpenPreferred] = value }

    suspend fun setPreferredLocation(location: Location?) = dataStore.edit {
        if (location == null) {
            it.remove(Keys.prefId); it.remove(Keys.prefName); it.remove(Keys.prefRegion)
            it.remove(Keys.prefCountry); it.remove(Keys.prefLat); it.remove(Keys.prefLon)
        } else {
            it[Keys.prefId] = location.id
            it[Keys.prefName] = location.name
            it[Keys.prefCountry] = location.countryCode
            it[Keys.prefLat] = location.latitude
            it[Keys.prefLon] = location.longitude
            if (location.region != null) it[Keys.prefRegion] = location.region
            else it.remove(Keys.prefRegion)
        }
    }

    suspend fun clearPreferredIf(id: Int) = dataStore.edit {
        if (it[Keys.prefId] == id) {
            it.remove(Keys.prefId); it.remove(Keys.prefName); it.remove(Keys.prefRegion)
            it.remove(Keys.prefCountry); it.remove(Keys.prefLat); it.remove(Keys.prefLon)
            it[Keys.autoOpenPreferred] = false
        }
    }

    suspend fun setUpdateInterval(value: UpdateInterval) =
        dataStore.edit { it[Keys.updateInterval] = value.name }
}

private inline fun <reified T : Enum<T>> String?.toEnum(default: T): T =
    enumValues<T>().firstOrNull { it.name == this } ?: default

private fun Preferences.readPreferred(): Location? {
    val id = this[SettingsStore.Keys.prefId] ?: return null
    return Location(
        id = id,
        name = this[SettingsStore.Keys.prefName] ?: return null,
        latitude = this[SettingsStore.Keys.prefLat] ?: return null,
        longitude = this[SettingsStore.Keys.prefLon] ?: return null,
        countryCode = this[SettingsStore.Keys.prefCountry] ?: "",
        region = this[SettingsStore.Keys.prefRegion]
    )
}