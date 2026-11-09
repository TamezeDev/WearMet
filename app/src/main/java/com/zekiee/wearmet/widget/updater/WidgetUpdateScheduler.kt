package com.zekiee.wearmet.widget.updater

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.zekiee.wearmet.local.data.dataStore.UpdateInterval
import java.util.concurrent.TimeUnit

object WidgetUpdateScheduler {

    private const val WORK_NAME = "widget_update"

    fun schedule(context: Context, interval: UpdateInterval, replace: Boolean) {
        val request = PeriodicWorkRequestBuilder<WidgetUpdateWorker>(
            interval.hours, TimeUnit.HOURS
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            if (replace) ExistingPeriodicWorkPolicy.UPDATE else ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    fun retryWhenOnline(context: Context) {
        val request = OneTimeWorkRequestBuilder<WidgetUpdateWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()
        WorkManager.getInstance(context).enqueueUniqueWork(
            "widget_retry",
            ExistingWorkPolicy.APPEND_OR_REPLACE,
            request
        )
    }
}
