package com.example.gestionviajes

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.util.Pair

class Presentacion : AppCompatActivity() {

    lateinit var topAnimation:Animation
    lateinit var bottomAnimation:Animation

    lateinit var imagen:ImageView
    lateinit var titulo:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.presentacion)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation)
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation)

        imagen=findViewById(R.id.iv_fantasma)
        titulo=findViewById(R.id.tv_tituloPresentacion)

        imagen.animation=topAnimation
        titulo.animation=bottomAnimation


        Handler().postDelayed({
            finish()
            val intent = Intent(this, Registro::class.java)

            val pairs = arrayOf(
                Pair<View, String>(imagen, "presentacion_logo"),
                Pair<View, String>(titulo, "presentacion_titulo")
            )

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                val options = ActivityOptions.makeSceneTransitionAnimation(this@Presentacion, *pairs)
                startActivity(intent, options.toBundle())
            } else {
                startActivity(intent)
            }

        }, 3000)


    }
}