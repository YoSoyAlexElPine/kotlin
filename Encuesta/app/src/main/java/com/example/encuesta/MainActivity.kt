package com.example.encuesta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.encuesta.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var b=ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        val listaEncuestas: MutableList<Encuesta> = mutableListOf()
        var nombre:String;
        var aux1:String;
        var aux2:String;

        b.btValidar.setOnClickListener {
            if (!b.nombre.text.isEmpty() || b.anonimo1.isActivated){

                if (!b.anonimo1.isActivated){
                    nombre=b.nombre.text.toString()
                }else{
                    nombre="Anonimo"
                }
                if (b.rgEspecialidad.toString()!="-1" && b.rgSo.toString()!="-1"){
                    val aux1 = resources.getResourceEntryName(b.rgEspecialidad.checkedRadioButtonId)
                    val aux2 = resources.getResourceEntryName(b.rgSo.checkedRadioButtonId)

                    listaEncuestas.add(Encuesta(nombre, aux2, aux1, b.txHoras.text.toString().toInt()))
                    b.txEncuestas.setText("")
                    b.txEncuestas.append("Encuesta confirmada :)\n")
                }else{
                    b.txEncuestas.setText("")
                    b.txEncuestas.setText("Selecciona todos los campos obligatorios")
                }
            }

        }

        b.btReiniciar.setOnClickListener(){
            listaEncuestas.clear()
            b.txEncuestas.setText("")
            b.txEncuestas.setText("Lista limpiada con exito")
        }

        b.btCuenta.setOnClickListener(){
            (Toast.makeText(this,"Encuestas realizadas: "+listaEncuestas.size.toString(),Toast.LENGTH_SHORT)).show()
        }


        b.btResumen.setOnClickListener(){
            b.txEncuestas.setText("")
            for (encuesta in listaEncuestas){
                b.txEncuestas.append(encuesta.toString()+"\n")
            }
        }



        b.sbHoras.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progreso: Int, fromUser: Boolean) {
                b.txHoras.setText(progreso.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Se llama cuando se inicia el seguimiento del SeekBar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Se llama cuando se detiene el seguimiento del SeekBar
            }
        })




    }
}