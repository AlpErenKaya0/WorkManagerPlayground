package com.alperen.workmanager

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WorkManager.getInstance(this).cancelAllWork()
        val newWorkManagerClass = OneTimeWorkRequestBuilder<NewWorkManagerClass>()
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(this).enqueue(newWorkManagerClass)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            WorkManager.getInstance(this).getWorkInfoByIdLiveData(newWorkManagerClass.id)
                .observe(this, Observer { workInfo ->
                    if (workInfo != null && workInfo.state == WorkInfo.State.ENQUEUED) {
                        // Kuyrukta hali hazırda bir işlem var, yeni işlem oluşturmayın
                        println("WorkManager task is already enqueued")
                    } else if (workInfo != null && workInfo.state == WorkInfo.State.FAILED) {
                        // İşlem başarısız oldu, yeni bir işlem oluşturabilirsiniz
                        println("WorkManager task failed :/")
                        WorkManager.getInstance(this).enqueue(newWorkManagerClass)
                    } else if (workInfo != null && workInfo.state == WorkInfo.State.RUNNING) {
                        // İşlem hala çalışıyor, yeni bir işlem oluşturmayın
                        println("WorkManager task is still running")
                    } else if (workInfo != null && workInfo.state.isFinished) {
                        // Önceki işlem tamamlandı veya başlamadı, yeni bir işlem oluşturabilirsiniz
                        WorkManager.getInstance(this).enqueue(newWorkManagerClass)
                    }
                })
        }
        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            // En güncel işlemi iptal et
            WorkManager.getInstance(this).cancelAllWork()
        }
    }


    }

