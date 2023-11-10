package com.example.menumuiltijuegos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.menumuiltijuegos.databinding.ActivityMainBinding
import kotlin.random.Random

lateinit var binding: ActivityMainBinding;

var contador1=0
var contador2=0
val random = Random


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.dados)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inicio()


            binding.boton1.setOnClickListener() {
                if (contador1<=4) {
                contador1++
                binding.tiradasContador1.setText(contador1.toString())

                val numeroAleatorio = random.nextInt(6)

                when (numeroAleatorio) {
                    0 -> {
                        binding.imagen1.setImageResource(R.drawable.uno)
                        var suma = binding.puntosContador1.text.toString().toInt() + 1
                        binding.puntosContador1.setText(suma.toString())
                    }

                    1 -> {
                        binding.imagen1.setImageResource(R.drawable.dos)
                        var suma = binding.puntosContador1.text.toString().toInt() + 2
                        binding.puntosContador1.setText(suma.toString())
                    }

                    2 -> {
                        binding.imagen1.setImageResource(R.drawable.tres)
                        var suma = binding.puntosContador1.text.toString().toInt() + 3
                        binding.puntosContador1.setText(suma.toString())
                    }

                    3 -> {
                        binding.imagen1.setImageResource(R.drawable.cuatro)
                        var suma = binding.puntosContador1.text.toString().toInt() + 4
                        binding.puntosContador1.setText(suma.toString())
                    }

                    4 -> {
                        binding.imagen1.setImageResource(R.drawable.cinco)
                        var suma = binding.puntosContador1.text.toString().toInt() + 5
                        binding.puntosContador1.setText(suma.toString())
                    }

                    5 -> {
                        binding.imagen1.setImageResource(R.drawable.seis)
                        var suma = binding.puntosContador1.text.toString().toInt() + 6
                        binding.puntosContador1.setText(suma.toString())
                    }

                }
                    verificarFinJuego()
            }

        }



            binding.boton2.setOnClickListener() {
                if (contador2<=4) {
                contador2++
                binding.tiradasContador2.setText(contador2.toString())

                val numeroAleatorio = random.nextInt(6)

                when (numeroAleatorio) {
                    0 -> {
                        binding.imagen2.setImageResource(R.drawable.uno)
                        var suma = binding.puntosContador2.text.toString().toInt() + 1
                        binding.puntosContador2.setText(suma.toString())
                    }

                    1 -> {
                        binding.imagen2.setImageResource(R.drawable.dos)
                        var suma = binding.puntosContador2.text.toString().toInt() + 2
                        binding.puntosContador2.setText(suma.toString())
                    }

                    2 -> {
                        binding.imagen2.setImageResource(R.drawable.tres)
                        var suma = binding.puntosContador2.text.toString().toInt() + 3
                        binding.puntosContador2.setText(suma.toString())
                    }

                    3 -> {
                        binding.imagen2.setImageResource(R.drawable.cuatro)
                        var suma = binding.puntosContador2.text.toString().toInt() + 4
                        binding.puntosContador2.setText(suma.toString())
                    }

                    4 -> {
                        binding.imagen2.setImageResource(R.drawable.cinco)
                        var suma = binding.puntosContador2.text.toString().toInt() + 5
                        binding.puntosContador2.setText(suma.toString())
                    }

                    5 -> {
                        binding.imagen2.setImageResource(R.drawable.seis)
                        var suma = binding.puntosContador2.text.toString().toInt() + 6
                        binding.puntosContador2.setText(suma.toString())
                    }

                }
                    verificarFinJuego()
            }

        }

        binding.reiniciar.setOnClickListener(){

            contador1=0
            contador2=0

            binding.puntosContador1.setText("0")
            binding.puntosContador2.setText("0")

            binding.imagen1.setImageResource(R.drawable.inicio)
            binding.imagen2.setImageResource(R.drawable.inicio)

            inicio()
        }

    }


    fun verificarFinJuego(){

        if (contador2>=5 && contador1>=5){

            binding.reiniciar.isVisible=true

            //Thread.sleep(3000)

            var puntosContador2=binding.puntosContador2.text.toString().toInt()
            var puntosContador1=binding.puntosContador1.text.toString().toInt()



            if (puntosContador2 > puntosContador1){

                binding.ganador2.isVisible=true

                var texto = binding.ganadasContador2.text.toString().toInt() + 1

                binding.ganadasContador2.setText(texto.toString())

            } else {
                binding.ganador1.isVisible=true

                var texto = binding.ganadasContador1.text.toString().toInt() + 1

                binding.ganadasContador1.setText(texto.toString())
            }
        }
    }

    fun inicio(){
        binding.ganador1.isVisible=false
        binding.ganador2.isVisible=false
        binding.reiniciar.isVisible=false
        binding.imagen1.setImageResource(R.drawable.inicio)
        binding.imagen2.setImageResource(R.drawable.inicio)
    }
}