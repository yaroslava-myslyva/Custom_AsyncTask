package com.example.customasynctask

import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlin.concurrent.thread

abstract class AbstractCustomAsyncTask<Input, Output>(var data :Input) {
    private var output: Output? = null
    fun execute() {
        onPreExecute()
        val thread = thread {
            output = doInBackground(data)
            Handler(Looper.getMainLooper()).post { //меняет потоки
                output?.let{
                    onPostExecute(it)
                }
            }
        }
        Log.d("ttt", thread.state.toString())

    }

    fun onPreExecute() {}

    abstract fun doInBackground(value: Input): Output

    abstract fun onProgressUpdate()

    abstract fun onPostExecute(output: Output)
}