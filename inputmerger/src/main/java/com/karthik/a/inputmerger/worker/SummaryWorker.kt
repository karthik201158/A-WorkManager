package com.karthik.a.inputmerger.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SummaryWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(ctx, workerParameters) {
    override suspend fun doWork(): Result {
        return try {
            val steps = inputData.getInt("steps", 0)
            val calories = inputData.getInt("calories", 0)
            val sleepDuration = inputData.getString("sleep_duration") ?: "No data"

            val summary = "Steps: $steps, Calories: $calories, Sleep: $sleepDuration"
            val result = getSendResult(summary)

            // Save or send the summary as needed
            // For example, save to shared preferences or database

            return Result.success(result)
        }catch (e: Exception) {
            Log.e(TAG, "Error uploading image", e)
            val result = getSendResult("")
            Result.failure(result)
        }
    }

    // Sending success data/Result back to activity/fragment which then observed in `getWorkInfoByIdLiveData`
    private fun getSendResult(taskOutput: String): Data {
        return workDataOf(
            TASK_OUTPUT to taskOutput
        )
    }

    companion object {
        private const val TAG = "UploadWorker"
        const val TASK_OUTPUT = "taskOutPut"
    }
}