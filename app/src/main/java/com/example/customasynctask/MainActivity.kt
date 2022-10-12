package com.example.customasynctask

import android.icu.util.Output
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.customasynctask.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("ttt", "activity ${Thread.currentThread()}")

        binding.button.setOnClickListener {
            Log.d("ttt", "button on click listener ${Thread.currentThread()}")
        }

        val customAsyncTask = object : AbstractCustomAsyncTask<Int, Int>(0) {

            override fun onProgressUpdate(value: Int) {
                Handler(Looper.getMainLooper()).post { //меняет потоки
                    Log.d("ttt", "set text ${Thread.currentThread()}")
                    binding.seconds.text =
                        String.format(Locale.getDefault(), "%02d:%02d:%02d", 0, 0, value)
                }
            }

            override fun onPostExecute(output: Int) {
                Log.d("ttt", "onPostExecute ${Thread.currentThread()}")
            }

            override fun doInBackground(value: Int): Int {
                for (i in 1..10) {
                    Thread.sleep(1000)
                    data++
                    Log.d("ttt", "doInBackground $data ${Thread.currentThread()}")
                }
                return data
            }
        }
        customAsyncTask.execute()
    }
}

