package com.example.reciclelista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.reciclelista.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var b:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}