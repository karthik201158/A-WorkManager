package com.karthik.a.uploadimage

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.karthik.a.uploadimage.viewmodel.MainViewModel
import com.karthik.a.uploadimage.worker.UploadWorker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.uploadImage()
        getWorkInfo()
    }

  private  fun getWorkInfo(){
        viewModel.oneTimeWorkObserver.observe(this, Observer { workInfo ->
            // If there are no matching work info, do nothing
            // WorkInfo : it contains info about the work like work id , status etc....
                if (workInfo == null) {
                    return@Observer
                }
                /* workInfo.state.name returns the state of the work
                * There are of 4 states BLOCKED -> ENQUEUED -> RUNNING -> SUCCEEDED*/
                Log.d("Notification Status Update: ", workInfo.state.name)

                if (workInfo.state.isFinished) {
                    Toast.makeText(this@MainActivity,
                        "result: " + workInfo.outputData.getString(UploadWorker.TASK_OUTPUT),
                        Toast.LENGTH_SHORT).show()
                }
            })

    }
}