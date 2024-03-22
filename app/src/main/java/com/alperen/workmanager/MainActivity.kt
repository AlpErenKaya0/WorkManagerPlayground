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
        val newWorkManagerClass: WorkRequest = OneTimeWorkRequestBuilder<NewWorkManagerClass>()
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()
        //aşağıdaki instance satırı Observer'daki ENQUEUED'da güncel işlem varsa işlem yapılmasını engelleyecek
        // ve isFinished durumunda veya WorkManager'ın iptal edilmesi durumunda instance'ının yeniden alınmasını sağlayacak
        //yapı kurulabilir
        WorkManager.getInstance(this).enqueue(newWorkManagerClass)
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(newWorkManagerClass.id).observe(this,
            Observer {workInfo->
                if (workInfo != null && workInfo.state.isFinished) {
                    println("TESTtest")
                    // aşağıdaki yemedi
                    /*
                WorkManager.getInstance(this).enqueue(newWorkManagerClass)
                     */
//bu çalışıyor ama her seferinde onCreate'de başlatılan gibi bunun için de ayrı kontrolör başlatmamız gerekir, olmaz
                    /*
                    val newWorkManagerClass = OneTimeWorkRequestBuilder<NewWorkManagerClass>()
                        .setInitialDelay(10, TimeUnit.SECONDS)
                        .build()
                    WorkManager.getInstance(this).enqueue(newWorkManagerClass)


                     */
                }
                else if (workInfo != null && workInfo.state == WorkInfo.State.ENQUEUED){
                    println("enqueue")


                }
            })
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {



/*
            WorkManager.getInstance(this).getWorkInfoByIdLiveData(newWorkManagerClass.id).observe(this, Observer { workInfo ->
                if (workInfo != null && workInfo.state == WorkInfo.State.ENQUEUED) {
                    WorkManager.getInstance(this).enqueue(newWorkManagerClass)
                    println("WorkManager task is already enqueued")
                } else if (workInfo != null && workInfo.state == WorkInfo.State.FAILED) {
                    println("WorkManager task failed :/")
                    WorkManager.getInstance(this).enqueue(newWorkManagerClass)
                } else if (workInfo != null && workInfo.state == WorkInfo.State.RUNNING) {
                    println("WorkManager task is still running")
                    WorkManager.getInstance(this).enqueue(newWorkManagerClass)
                }
                else if (workInfo!=null &&workInfo.state.isFinished){
                    // Yeni bir istek sıraya alınacak çünkü önceki işlem tamamlanmış veya başlamamış
                    WorkManager.getInstance(this).enqueue(newWorkManagerClass)
                }

            })
*/
        }
        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            // En güncel işlemi iptal et
            WorkManager.getInstance(this).cancelAllWork()
        }
    }
}
