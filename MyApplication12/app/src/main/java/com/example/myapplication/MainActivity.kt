package com.example.myapplication

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        var b = ActivityMainBinding.inflate(layoutInflater)


        // Inicializar el reproductor
        mediaPlayer = MediaPlayer.create(this, R.raw.sonido) // Reemplaza "tu_archivo_mp3" con el nombre de tu archivo MP3 en res/raw
        mediaPlayer?.isLooping = false // Puedes configurar si deseas que se repita

        // Iniciar la reproducción
        mediaPlayer?.start()









        // Detener la reproducción al finalizar
        mediaPlayer?.setOnCompletionListener {
            stopAudio()
        }




        b.button.setOnClickListener(){

            // Carga el vector drawable
            val drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.baseline_currency_bitcoin_24)

            // Cambia el tinte del drawable
            drawable?.setColorFilter(ContextCompat.getColor(this, R.color.rojo), PorterDuff.Mode.SRC_IN)

            // Asigna el drawable modificado a tu ImageView
            b.imageView3.setImageDrawable(drawable)
        }

        b.button2.setOnClickListener(){
            // Carga el vector drawable
            val drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.baseline_currency_bitcoin_24)

            // Cambia el tinte del drawable
            drawable?.setColorFilter(ContextCompat.getColor(this, R.color.verde), PorterDuff.Mode.SRC_IN)

            // Asigna el drawable modificado a tu ImageView
            b.imageView3.setImageDrawable(drawable)
        }

        b.button4.setOnClickListener(){
            // Carga el vector drawable
            val drawable: Drawable? = ContextCompat.getDrawable(this, R.drawable.baseline_currency_bitcoin_24)

            // Cambia el tinte del drawable
            drawable?.setColorFilter(ContextCompat.getColor(this, R.color.amarillo), PorterDuff.Mode.SRC_IN)

            // Asigna el drawable modificado a tu ImageView
            b.imageView3.setImageDrawable(drawable)
        }



    }
    private fun stopAudio() {
        mediaPlayer?.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAudio()
    }
}