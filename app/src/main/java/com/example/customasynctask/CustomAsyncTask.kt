package com.example.customasynctask

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import java.util.*

class CustomAsyncTask : AsyncTask<Unit, Int, Int>() {
    private var seconds = 0
    private lateinit var textView: TextView

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: Unit): Int? {
        Log.d("ttt", "start doInBackground ${Thread.currentThread()}")

        val thread = Thread {
            Looper.prepare()
            Looper.myLooper()?.let {
                Handler(it).post {
                    for (i in 0..10) {
                        Thread.sleep(1000)
                        Log.d("ttt", "doInBackground myLooper $i ${Thread.currentThread()}")
                        seconds = i
                        publishProgress()
                    }
                }
            }
            Looper.loop()
        }
        thread.start()
        return seconds
    }

    override fun onProgressUpdate(vararg values: Int?) {
        Handler(Looper.getMainLooper()).post {
            textView.text = String.format(Locale.getDefault(), "%02d:%02d:%02d", 0, 0, seconds)
            Log.d("ttt", "onProgressUpdate mainLooper $seconds ${Thread.currentThread()}")
        }
    }

    override fun onPostExecute(result: Int?) {
        Looper.myLooper()?.let { Handler(it).post {
            Log.d("ttt", "onPostExecute ${Thread.currentThread()}")
        } }
        super.onPostExecute(result)
    }

    fun setTextView(textView: TextView) {
        this.textView = textView
    }
}