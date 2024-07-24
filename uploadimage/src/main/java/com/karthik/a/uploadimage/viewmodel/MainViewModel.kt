package com.karthik.a.uploadimage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.karthik.a.uploadimage.worker.UploadWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val workManager: WorkManager by lazy { WorkManager.getInstance(application) }

    val oneTimeWorkObserver: LiveData<WorkInfo> by lazy { workManager.getWorkInfoByIdLiveData(oneTimeWorkReq.id) }

    private val constraint: Constraints
        get() = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

    private val oneTimeWorkReq: OneTimeWorkRequest by lazy {
        OneTimeWorkRequest.Builder(UploadWorker::class.java)
           // .setInputData(dataToSend)
            .setConstraints(constraint)
          //  .setInitialDelay(15, TimeUnit.DAYS)
            .build()
    }

    fun uploadImage() {
        workManager.enqueue(oneTimeWorkReq)
    }

    /** Sending data to the worker class */
    private val dataToSend
        get() = workDataOf(
            "" to "Sample Task",
            "" to true
        )
}
