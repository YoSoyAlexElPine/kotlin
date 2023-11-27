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

/**
 * Actividad de presentación que muestra una animación inicial y transición a la actividad de Registro.
 * Utiliza animaciones y transiciones de escena para la presentación.
 * @author = Alex Pineño Sanchez
 */
class Presentacion : AppCompatActivity() {

    lateinit var topAnimation: Animation
    lateinit var bottomAnimation: Animation

    lateinit var imagen: ImageView
    lateinit var titulo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.presentacion)

        // Establece la pantalla en modo de pantalla completa
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Cargar animaciones
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)

        // Vincula las vistas con las animaciones
        imagen = findViewById(R.id.iv_fantasma)
        titulo = findViewById(R.id.tv_tituloPresentacion)

        imagen.animation = topAnimation
        titulo.animation = bottomAnimation

        // Delay para mostrar la presentación antes de pasar a la siguiente actividad (Registro)
        Handler().postDelayed({
            finish()
            val intent = Intent(this, Registro::class.java)

            // Define transiciones de elementos para versiones posteriores a Lollipop
            val pairs = arrayOf(
                Pair<View, String>(imagen, "presentacion_logo"),
                Pair<View, String>(titulo, "presentacion_titulo")
            )

            // Realiza la transición si la versión del SDK es compatible, de lo contrario, inicia la actividad sin transición.
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                val options = ActivityOptions.makeSceneTransitionAnimation(this@Presentacion, *pairs)
                startActivity(intent, options.toBundle())
            } else {
                startActivity(intent)
            }

        }, 3000) // Retardo de 3 segundos antes de pasar a la siguiente actividad
    }
}
