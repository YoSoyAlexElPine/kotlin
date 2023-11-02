package com.example.recyclerajedrez

import Adaptador.Adaptador
import Modelo.Ajedrecista
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.recyclerajedrez.databinding.ActivityMain2Binding


class Pantalla2 : AppCompatActivity() {

    lateinit var b: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        b = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(b.root)

        var ajedrecistaRecibido = intent.getSerializableExtra("ajedrecista") as Ajedrecista

        b.tvNombre.setText(ajedrecistaRecibido.nombre)
        b.tvCampeonatos.setText(ajedrecistaRecibido.mundiales.toString())
        b.tvNacionalidad.setText(ajedrecistaRecibido.nacionalidad)
        b.tvCuriosidad.setText(ajedrecistaRecibido.cusiosidades);
        b.tvEstilo.setText(ajedrecistaRecibido.estilo)
        b.tvPorcentaje.setText(ajedrecistaRecibido.porcentajeVictorias.toString()+" %")
        b.tvRanking.setText(ajedrecistaRecibido.rankingFIFE.toString())
        b.tvRivalidad.setText(ajedrecistaRecibido.rivalidades)
        b.tvNacionalidad.setText(ajedrecistaRecibido.nacionalidad)

        val imageResource: Int = this.resources.getIdentifier(ajedrecistaRecibido.imagen, null, this.packageName)
        var res: Drawable = this.resources.getDrawable(imageResource)
        b.iPantalla2.setImageDrawable(res)

        b.bVolver.setOnClickListener(){
            var primeraPantalla = Intent(this, MainActivity::class.java)

            startActivity(primeraPantalla)


        }







    }





}