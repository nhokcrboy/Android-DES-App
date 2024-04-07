package com.example.androiddes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }
        setContentView(R.layout.activity_main)
    }
}