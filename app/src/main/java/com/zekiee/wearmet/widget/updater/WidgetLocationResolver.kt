package com.zekiee.wearmet.widget.updater

import android.content.Context
import com.zekiee.wearmet.domain.model.Location
import com.zekiee.wearmet.local.data.dataStore.LastLocationStore
import com.zekiee.wearmet.local.data.dataStore.SettingsStore

object WidgetLocationResolver {
    suspend fun resolve(context: Context): Location? =
        SettingsStore(context).get().preferredLocation
            ?: LastLocationStore(context).get()
}