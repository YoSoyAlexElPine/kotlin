package com.example.planetas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.example.planetas.Planeta


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var texto: TextView = findViewById(R.id.texto)

        var rg:RadioGroup= findViewById(R.id.rg)

        var boton1: Button = findViewById(R.id.boton_1)
        var boton2: Button = findViewById(R.id.boton_2)
        var boton3: Button = findViewById(R.id.boton_3)

        var rd_rocosos:RadioButton=findViewById(R.id.rd_rocosos);
        var rd_GiganteGaseoso:RadioButton=findViewById(R.id.rd_GiganteGaseoso);
        var rd_GiganteHelado:RadioButton=findViewById(R.id.rd_GiganteHelado);


        val planetas = listOf(
            Planeta().apply {
                nombre = "Mercurio"
                tipo = TipoPlaneta.ROCOSO
                radio = 2439.7
                gravedad = 3.7
                masa = 330
                distancia_sol = 58
            },
            Planeta().apply {
                nombre = "Venus"
                tipo = TipoPlaneta.ROCOSO
                radio = 6051.8
                gravedad = 8.87
                masa = 4875
                distancia_sol = 108
            },
            Planeta().apply {
                nombre = "Tierra"
                tipo = TipoPlaneta.ROCOSO
                radio = 6371.0
                gravedad = 9.81
                masa = 5972
                distancia_sol = 149
            },
            Planeta().apply {
                nombre = "Marte"
                tipo = TipoPlaneta.ROCOSO
                radio = 3389.5
                gravedad = 3.71
                masa = 641
                distancia_sol = 227
            },
            Planeta().apply {
                nombre = "Júpiter"
                tipo = TipoPlaneta.GIGANTE_GASEOSO
                radio = 69911.0
                gravedad = 24.79
                masa = 1898600
                distancia_sol = 778
            },
            Planeta().apply {
                nombre = "Saturno"
                tipo = TipoPlaneta.GIGANTE_GASEOSO
                radio = 58232.0
                gravedad = 10.44
                masa = 568460
                distancia_sol = 1434
            },
            Planeta().apply {
                nombre = "Urano"
                tipo = TipoPlaneta.GIGANTE_HELADO
                radio = 25362.0
                gravedad = 8.69
                masa = 86832
                distancia_sol = 2870
            },
            Planeta().apply {
                nombre = "Neptuno"
                tipo = TipoPlaneta.GIGANTE_HELADO
                radio = 24622.0
                gravedad = 11.15
                masa = 102430
                distancia_sol = 4495
            }

        )

        rg.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rd_rocosos -> {
                    boton1.text = "Mercurio"
                    boton2.text = "Venus"
                    boton3.visibility=View.VISIBLE
                    boton3.text="Tierra"
                }
                R.id.rd_GiganteHelado -> {
                    boton1.text = "Urano"
                    boton2.text = "Neptuno"
                    boton3.visibility=View.GONE
                }
                R.id.rd_GiganteGaseoso -> {
                    boton1.text = "Jupiter"
                    boton2.text = "Saturno"
                    boton3.visibility=View.GONE
                }
            }
        }
        boton1.setOnClickListener(){
            if(boton1.text=="Mercurio"){
                texto.setText(planetas[0].toString())
            }
            if(boton1.text=="Jupiter"){
                texto.setText(planetas[4].toString())
            }
            if(boton1.text=="Urano"){
                texto.setText(planetas[6].toString())
            }
        }
        boton2.setOnClickListener(){
            if(boton2.text=="Venus"){
                texto.setText(planetas[1].toString())
            }
            if(boton2.text=="Saturno"){
                texto.setText(planetas[5].toString())
            }
            if(boton2.text=="Neptuno"){
                texto.setText(planetas[0].toString())
            }

        }
        boton3.setOnClickListener(){
            if(boton3.text=="Tierra"){
                texto.setText(planetas[2].toString())
            }
        }



    }






}
