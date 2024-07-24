package com.karthik.a.uploadimage.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(ctx, workerParameters) {

    companion object {
       private const val TAG = "UploadWorker"
        const val TASK_OUTPUT = "taskOutPut"
        const val IS_DONE = "isDone"
    }

    override suspend fun doWork(): Result {
        return try {
            Log.d(TAG, "Starting image upload...")
            // Simulate network delay (replace with actual upload logic)
            delay(3000) // Simulate 3 seconds of upload time
            Log.d(TAG, "Image upload successful!")
            val result = getSendResult(true, "Success")
            Result.success(result)// if work is success
        } catch (e: Exception) {
            Log.e(TAG, "Error uploading image", e)
            val result = getSendResult(false, "Failure")
            Result.failure(result)
        }
    }

    // Sending success data/Result back to activity/fragment which then observed in `getWorkInfoByIdLiveData`
    private fun getSendResult(isDone: Boolean, taskOutput: String): Data {
        return workDataOf(
            TASK_OUTPUT to taskOutput,
            IS_DONE to isDone
        )
    }

}