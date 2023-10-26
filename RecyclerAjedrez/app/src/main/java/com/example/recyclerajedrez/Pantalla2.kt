package com.example.recyclerajedrez

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerajedrez.databinding.ActivityMainBinding

class Pantalla2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)



    }





}