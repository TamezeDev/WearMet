package com.zekiee.wearmet.local.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zekiee.wearmet.domain.model.Location
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore("wearmet_prefs")

class LastLocationStore(private val context: Context) {
    private val id = intPreferencesKey("loc_id")
    private val name = stringPreferencesKey("loc_name")
    private val lat = doublePreferencesKey("loc_lat")
    private val lon = doublePreferencesKey("loc_lon")
    private val country = stringPreferencesKey("loc_country")
    private val region = stringPreferencesKey("loc_region")

    suspend fun save(l: Location) {
        context.dataStore.edit { p ->
            p[id] = l.id
            p[name] = l.name
            p[lat] = l.latitude
            p[lon] = l.longitude
            p[country] = l.countryCode
            if (l.region != null) p[region] = l.region else p.remove(region)
        }
    }

    suspend fun get(): Location? {
        val p = context.dataStore.data.first()
        return Location(
            id = p[id] ?: return null,
            name = p[name] ?: return null,
            latitude = p[lat] ?: return null,
            longitude = p[lon] ?: return null,
            countryCode = p[country].orEmpty(),
            region = p[region]
        )
    }
}