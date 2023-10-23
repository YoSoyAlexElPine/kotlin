package com.example.formulario2activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.formulario2activitys.databinding.ActivityMainBinding

class Salida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salida)

        var b= ActivityMainBinding.inflate(layoutInflater);
        setContentView(b.root)



    }
}