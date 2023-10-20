package com.example.simondice

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.simondice.databinding.ActivityMainBinding
import java.util.LinkedList
import kotlin.random.Random
import android.os.CountDownTimer

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root);

        val random = Random.Default
        val numeroAleatorio = random.nextInt(4) // Genera un número aleatorio entre 0 y 3
        var contador: Int;

        var secuencia = LinkedList<Int>();
        var secuenciaJugador = LinkedList<Int>();

        secuencia.add(numeroAleatorio)

        for (i in secuencia) {
            when (i) {
                0 -> {
                    b.ibAzul.setImageResource(R.drawable.verde);
                    temporizador(2)
                }
                1 -> {

                }
                2 -> {

                }
                3 -> {

                }


            }
        }




        contador = 0;


    }

    fun temporizador(segundos:Int){

        var milis:Long;
        milis= (segundos*1000).toLong()

        val temporizador = object : CountDownTimer(milis, milis) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
            }
        }
    }


}