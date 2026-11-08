package com.zekiee.wearmet.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.zekiee.wearmet.domain.model.Location
import com.zekiee.wearmet.local.data.dataStore.AppSettings
import com.zekiee.wearmet.local.data.dataStore.SettingsStore
import com.zekiee.wearmet.local.data.dataStore.SplashDuration
import com.zekiee.wearmet.local.data.dataStore.UpdateInterval
import com.zekiee.wearmet.widget.updater.WidgetRefresh
import com.zekiee.wearmet.widget.updater.WidgetUpdateScheduler
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val store = SettingsStore(application)

    val settings: StateFlow<AppSettings> = store.settings.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AppSettings()
    )

    fun setSplashEnabled(value: Boolean) {
        viewModelScope.launch { store.setSplashEnabled(value) }
    }

    fun setSplashDuration(value: SplashDuration) {
        viewModelScope.launch { store.setSplashDuration(value) }
    }

    fun setAutoOpen(value: Boolean) {
        viewModelScope.launch { store.setAutoOpenPreferred(value) }
    }

    fun setPreferred(location: Location) {
        viewModelScope.launch {
            store.setPreferredLocation(location)
            WidgetRefresh.refreshAll(getApplication())
        }
    }

    fun setUpdateInterval(value: UpdateInterval) {
        viewModelScope.launch {
            store.setUpdateInterval(value)
            WidgetUpdateScheduler.schedule(getApplication(), value, replace = true)
        }
    }
}