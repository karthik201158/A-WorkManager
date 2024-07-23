package com.karthik.a.uploadimage

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(ctx, workerParameters) {

    companion object {
        const val TAG = "UploadWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            Log.d(TAG, "Starting image upload...")
            // Simulate network delay (replace with actual upload logic)
            delay(3000) // Simulate 3 seconds of upload time
            Log.d(TAG, "Image upload successful!")
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Error uploading image", e)
            Result.failure()
        }
    }

}