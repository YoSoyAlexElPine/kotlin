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

    var b = ActivityMainBinding.inflate(layoutInflater)
    val random = Random.Default

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setContentView(b.root);

        var secuencia = LinkedList<Int>();
        var secuenciaJugador = LinkedList<Int>();
        val numeroAleatorio = random.nextInt(4)

        secuencia.add(numeroAleatorio)

        mostrarSecuencia(secuencia)

        b.ibVerde.setOnClickListener(){
            secuenciaJugador.add(0);
        }
        b.ibAmarillo.setOnClickListener(){
            secuenciaJugador.add(1);
        }

        b.ibRojo.setOnClickListener(){
            secuenciaJugador.add(2);
        }

        b.ibAzul.setOnClickListener(){
            secuenciaJugador.add(3);
        }


    }

    fun mostrarSecuencia(secuencia:LinkedList<Int>) {

        setContentView(b.root);

        b.ibAzul.isEnabled=false
        b.ibRojo.isEnabled=false
        b.ibAmarillo.isEnabled=false
        b.ibVerde.isEnabled=false

        for (i in secuencia) {
            when (i) {
                0 -> {
                    b.ibAzul.setImageResource(R.drawable.verde);
                    temporizador(2)
                    b.ibAzul.setImageResource(R.drawable.verdeo);
                }
                1 -> {
                    b.ibAzul.setImageResource(R.drawable.amarillo);
                    temporizador(2)
                    b.ibAzul.setImageResource(R.drawable.amarilloo);
                }
                2 -> {
                    b.ibAzul.setImageResource(R.drawable.rojo);
                    temporizador(2)
                    b.ibAzul.setImageResource(R.drawable.rojoo);
                }
                3 -> {
                    b.ibAzul.setImageResource(R.drawable.azul);
                    temporizador(2)
                    b.ibAzul.setImageResource(R.drawable.azulo);
                }
            }
            temporizador(1)
        }

        b.ibAzul.isEnabled=true
        b.ibRojo.isEnabled=true
        b.ibAmarillo.isEnabled=true
        b.ibVerde.isEnabled=true

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