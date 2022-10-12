package com.example.customasynctask

import android.os.Handler
import android.os.Looper
import kotlin.concurrent.thread

abstract class AbstractCustomAsyncTask<Input, Output>(data: Input) {
    private var output: Output? = null
    var data = data
        set(value) {
            onProgressUpdate(value)
            field = value
        }

    fun execute() {
        onPreExecute()
        thread {
            output = doInBackground(data)
            Handler(Looper.getMainLooper()).post { //меняет потоки
                output?.let {
                    onPostExecute(it)
                }
            }
        }
    }


    fun onPreExecute() {}

    abstract fun doInBackground(value: Input): Output

    abstract fun onProgressUpdate(value: Input)

    abstract fun onPostExecute(output: Output)

}