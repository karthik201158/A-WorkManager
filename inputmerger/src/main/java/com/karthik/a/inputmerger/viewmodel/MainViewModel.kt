package com.karthik.a.inputmerger.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.work.ArrayCreatingInputMerger
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.karthik.a.inputmerger.worker.CaloriesWorker
import com.karthik.a.inputmerger.worker.SleepWorker
import com.karthik.a.inputmerger.worker.StepsWorker
import com.karthik.a.inputmerger.worker.SummaryWorker
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
        OneTimeWorkRequest.Builder(SummaryWorker::class.java)
            //if you're using the default OverwritingInputMerger (no setInputMerger specified), 
            .build()
    }

    fun triggerWorkers() {
        val stepsWorker = OneTimeWorkRequestBuilder<StepsWorker>().build()
        val caloriesWorker = OneTimeWorkRequestBuilder<CaloriesWorker>().build()
        val sleepWorker = OneTimeWorkRequestBuilder<SleepWorker>().build()

        workManager
            .beginWith(listOf(stepsWorker, caloriesWorker, sleepWorker))
            .then(oneTimeWorkReq)
            .enqueue()

        // Optionally, observe the result and update the ViewModel if necessary
    }
}
