package com.karthik.a.inputmerger.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SleepWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(ctx, workerParameters) {
    override suspend fun doWork(): Result {
        // Simulate fetching sleep data
        val sleepDuration = "7 hours"
        val outputData = workDataOf("sleep_duration" to sleepDuration)
        return Result.success(outputData)
    }
}