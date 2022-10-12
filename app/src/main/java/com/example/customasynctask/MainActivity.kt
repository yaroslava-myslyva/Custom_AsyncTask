package com.example.customasynctask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.customasynctask.databinding.ActivityMainBinding

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

        val customAsyncTask = CustomAsyncTask()
        customAsyncTask.setTextView(binding.seconds)
        customAsyncTask.execute()
    }
}

