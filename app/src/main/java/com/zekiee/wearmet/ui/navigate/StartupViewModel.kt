package com.zekiee.wearmet.ui.navigate

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.zekiee.wearmet.domain.model.Location
import com.zekiee.wearmet.local.data.dataStore.SettingsStore
import com.zekiee.wearmet.widget.updater.WidgetUpdateScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class StartupPlan(val splashMillis: Long?, val directLocation: Location?)

class StartupViewModel(application: Application) : AndroidViewModel(application) {

    private val _plan = MutableStateFlow<StartupPlan?>(null)
    val plan: StateFlow<StartupPlan?> = _plan.asStateFlow()

    init {
        viewModelScope.launch {
            val s = SettingsStore(application).get()
            WidgetUpdateScheduler.schedule(application, s.updateInterval, replace = false)
            _plan.value = StartupPlan(
                splashMillis = if (s.splashEnabled) s.splashDuration.millis else null,
                directLocation = if (s.autoOpenPreferred) s.preferredLocation else null
            )
        }
    }
}