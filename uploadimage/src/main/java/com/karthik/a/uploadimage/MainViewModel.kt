package com.karthik.a.uploadimage

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val workManager: WorkManager,
) : ViewModel() {

    fun uploadImage() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .setConstraints(constraints)
            .build()

        workManager.enqueue(uploadWorkRequest)
    }
}
