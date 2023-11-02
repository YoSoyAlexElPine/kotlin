package com.example.barranavegacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.barranavegacion.databinding.ActivityMainBinding

lateinit var b: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        b=ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)


        b.toolbar.title="      Titulo Toolbar"
        b.toolbar.setLogo(R.drawable.carrito);

    }
}