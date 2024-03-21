package com.alperen.workmanager

import android.content.Context
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters

class NewWorkManagerClass (val context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {
    override fun doWork(): Result {
        val message = 1
        println(message)
        return Result.success()
    }

}
