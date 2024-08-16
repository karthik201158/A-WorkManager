package com.karthik.a.inputmerger.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class StepsWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(ctx, workerParameters) {
    override suspend fun doWork(): Result {
        // Simulate fetching step data
        val steps = 8000
        val outputData = workDataOf("steps" to steps)
        return Result.success(outputData)
    }
}