package com.alperen.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WorkManager.getInstance(this).cancelAllWork()
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val newWorkManagerClass: WorkRequest = OneTimeWorkRequestBuilder<NewWorkManagerClass>()
                .setInitialDelay(60, TimeUnit.SECONDS)
                .build()
            WorkManager.getInstance(this).enqueue(newWorkManagerClass)
        }
        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            // En güncel işlemi iptal et
            WorkManager.getInstance(this).cancelAllWork()
        }
    }
}
