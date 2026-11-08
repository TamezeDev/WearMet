package com.zekiee.wearmet.widget.updater

import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.zekiee.wearmet.widget.WearMetWidget

class WidgetUpdateWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        WidgetRefresh.bump()
        WearMetWidget().updateAll(applicationContext)
        return Result.success()
    }
}