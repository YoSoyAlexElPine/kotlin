package com.example.simondice
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.simondice.databinding.ActivityMainBinding
import java.util.LinkedList
import kotlin.random.Random
import android.os.CountDownTimer
import android.widget.ImageButton
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    val r = Random.Default

    override fun onCreate(savedInstanceState: Bundle?) {

        var b = ActivityMainBinding.inflate(layoutInflater)


        super.onCreate(savedInstanceState)

        setContentView(b.root);

        var secuencia = LinkedList<Int>();
        var secuenciaJugador = LinkedList<Int>();

        b.ibRojo.setImageResource(R.drawable.rojoo);
        b.ibVerde.setImageResource(R.drawable.verdeo);
        b.ibAmarillo.setImageResource(R.drawable.amarilloo);
        b.ibAzul.setImageResource(R.drawable.azulo);

        deshabilitarBotones(b)

        b.bReiniciar.setOnClickListener {

            deshabilitarBotones(b)

            secuenciaJugador.clear()
            b.tvAux.setText(secuenciaJugador.size.toString())
            secuencia.clear()
            b.bComenzar.isEnabled = true
            b.tvPerder.isVisible = false
        }

        b.ibVerde.setOnClickListener {

            b.ibVerde.setImageResource(R.drawable.verde)


            temporizador(0.8) {
                b.ibVerde.setImageResource(R.drawable.verdeo);
            }

            secuenciaJugador.add(0);
            b.tvAux.setText(secuenciaJugador.size.toString())

            verificar(secuencia, secuenciaJugador, b)


        }

        b.ibAmarillo.setOnClickListener {

            b.ibAmarillo.setImageResource(R.drawable.amarillo)

            temporizador(0.8) {
                b.ibAmarillo.setImageResource(R.drawable.amarilloo)
            }



            secuenciaJugador.add(1);
            b.tvAux.setText(secuenciaJugador.size.toString())

            verificar(secuencia, secuenciaJugador, b)


        }

        b.ibRojo.setOnClickListener {

            b.ibRojo.setImageResource(R.drawable.rojo)

            temporizador(0.8) {
                b.ibRojo.setImageResource(R.drawable.rojoo)
            }



            secuenciaJugador.add(2)
            b.tvAux.setText(secuenciaJugador.size.toString())

            verificar(secuencia, secuenciaJugador, b)


        }

        b.ibAzul.setOnClickListener {

            b.ibAzul.setImageResource(R.drawable.azul)

            temporizador(0.8) {
                b.ibAzul.setImageResource(R.drawable.azulo)
            }



            secuenciaJugador.add(3)

            b.tvAux.setText(secuenciaJugador.size.toString())

            verificar(secuencia, secuenciaJugador, b)

        }

        b.bComenzar.setOnClickListener {

            b.bComenzar.isEnabled = false

            secuencia.clear()
            secuencia.add(aleatorio())
            b.tvNivel.setText(secuencia.size.toString())
            mostrarSecuencia(secuencia, b)

        }


    }

    fun verificar(
        secuencia: LinkedList<Int>,
        secuenciaJugador: LinkedList<Int>,
        b: ActivityMainBinding
    ) {

        var acertar: Boolean
        acertar = false

        for (i in 0 until secuenciaJugador.size) {
            acertar = (secuenciaJugador[i] == secuencia[i])
        }

        if (!acertar) {
            deshabilitarBotones(b)

            b.tvPerder.setText("Puntuacion = " + secuencia.size.toString())
            b.tvPerder.isVisible = true

            secuencia.clear()
            secuenciaJugador.clear()
        } else {
            if (secuenciaJugador.size == secuencia.size) {
                secuenciaJugador.clear()


                secuencia.add(aleatorio())

                b.tvAux.setText(secuenciaJugador.size.toString())
                b.tvNivel.setText(secuencia.size.toString())

                mostrarSecuencia(secuencia, b)
            }
        }



        if (secuenciaJugador.size > secuencia.size) {
            b.tvNivel.setText("Error")
        }
    }

    fun aleatorio(): Int {
        return r.nextInt(4)
    }

    fun deshabilitarBotones(b: ActivityMainBinding) {
        b.ibAzul.isEnabled = false
        b.ibRojo.isEnabled = false
        b.ibAmarillo.isEnabled = false
        b.ibVerde.isEnabled = false
    }

    fun mostrarSecuencia(secuencia: LinkedList<Int>, b: ActivityMainBinding) {

        temporizador(0.5){


        var contador: Double = 0.0;

        deshabilitarBotones(b)

        for (i in secuencia) {
            contador = contador + 1.0
            when (i) {
                0 -> {
                    temporizador(contador) {
                        b.ibVerde.setImageResource(R.drawable.verde);
                    }
                    temporizador(contador + 0.8) {
                        b.ibVerde.setImageResource(R.drawable.verdeo);
                    }
                }

                1 -> {
                    temporizador(contador) {
                        b.ibAmarillo.setImageResource(R.drawable.amarillo);
                    }
                    temporizador(contador + 0.8) {
                        b.ibAmarillo.setImageResource(R.drawable.amarilloo);
                    }

                }

                2 -> {
                    temporizador(contador) {
                        b.ibRojo.setImageResource(R.drawable.rojo);
                    }
                    temporizador(contador + 0.8) {
                        b.ibRojo.setImageResource(R.drawable.rojoo);
                    }

                }

                3 -> {
                    temporizador(contador) {
                        b.ibAzul.setImageResource(R.drawable.azul);
                    }
                    temporizador(contador + 0.8) {
                        b.ibAzul.setImageResource(R.drawable.azulo);
                    }

                }

                else -> {
                    b.tvNivel.setText("Error2")
                }
            }
        }

        temporizador(contador + 0.8) {
            b.ibAzul.isEnabled = true
            b.ibRojo.isEnabled = true
            b.ibAmarillo.isEnabled = true
            b.ibVerde.isEnabled = true
        }
    }

    }

    fun temporizador(segundos: Double, accion: () -> Unit) {
        val milis = (segundos * 1000).toLong()
        val temporizador = object : CountDownTimer(milis, milis) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                accion()
            }
        }
        temporizador.start()
    }


}