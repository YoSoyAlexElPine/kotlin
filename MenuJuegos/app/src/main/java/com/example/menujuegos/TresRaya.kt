package com.example.menujuegos

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.menujuegos.databinding.TresRayaBinding

class TresRaya : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tres_raya)

        var b=TresRayaBinding.inflate(layoutInflater);
        setContentView(b.root)

        var pulsado:Boolean;
        var turno:Int;
        turno=0

        b.tvTitulo.setText(intent.getStringExtra("nombre"))

        var ib_array = Array(3) { Array(3) { ImageButton(this) } }

        b.tvGanador.isVisible=false;
        ib_array[0][0]=b.ib1
        ib_array[0][1]=b.ib2
        ib_array[0][2]=b.ib3
        ib_array[1][0]=b.ib4
        ib_array[1][1]=b.ib5
        ib_array[1][2]=b.ib6
        ib_array[2][0]=b.ib7
        ib_array[2][1]=b.ib8
        ib_array[2][2]=b.ib9


        b.ib1.setOnClickListener {
            if(!b.tvGanador.isVisible) {
                ib_array = accion(ib_array, b.ib1, turno)
                turno++
            }
        }

        b.ib2.setOnClickListener {
            if(!b.tvGanador.isVisible) {
            ib_array=accion(ib_array,b.ib2,turno)
            turno++
            }
        }
        b.ib3.setOnClickListener {
            if(!b.tvGanador.isVisible) {

            ib_array=accion(ib_array,b.ib3,turno)
            turno++
        }
        }
        b.ib4.setOnClickListener {
            if(!b.tvGanador.isVisible) {

            ib_array=accion(ib_array,b.ib4,turno)
            turno++
        }
        }
        b.ib5.setOnClickListener {
            if(!b.tvGanador.isVisible) {

            ib_array=accion(ib_array,b.ib5,turno)
            turno++
        }
        }
        b.ib6.setOnClickListener {
            if(!b.tvGanador.isVisible) {

            ib_array=accion(ib_array,b.ib6,turno)
            turno++
        }
        }
        b.ib7.setOnClickListener {
            if(!b.tvGanador.isVisible) {

            ib_array=accion(ib_array,b.ib7,turno)
            turno++
        }
        }
        b.ib8.setOnClickListener {
            if(!b.tvGanador.isVisible) {

            ib_array=accion(ib_array,b.ib8,turno)
            turno++
        }
        }
        b.ib9.setOnClickListener {
            if (!b.tvGanador.isVisible) {

            ib_array = accion(ib_array, b.ib9, turno)
            turno++
        }
        }


        b.button.setOnClickListener(){
            b.ib1.setImageResource(R.drawable.blanco);
            b.ib2.setImageResource(R.drawable.blanco);
            b.ib3.setImageResource(R.drawable.blanco);
            b.ib4.setImageResource(R.drawable.blanco);
            b.ib5.setImageResource(R.drawable.blanco);
            b.ib6.setImageResource(R.drawable.blanco);
            b.ib7.setImageResource(R.drawable.blanco);
            b.ib8.setImageResource(R.drawable.blanco);
            b.ib9.setImageResource(R.drawable.blanco);
            b.tvGanador.isVisible=false;
        }

        b.bVolver3.setOnClickListener(){
            finish()
        }

    }

    private fun accion(ibArray: Array<Array<ImageButton>>,ib:ImageButton,turno:Int): Array<Array<ImageButton>> {

        var fila = 0
        var columna = 0
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (ibArray[i][j] == ib) {
                    fila = i
                    columna = j
                    break
                }
            }
        }

        val objetoDrawable = ib.drawable
        val esBlanco:Boolean;

        esBlanco = (objetoDrawable.constantState == resources.getDrawable(R.drawable.blanco).constantState)

        if (turno % 2 == 0) {
            if (esBlanco) {
                ibArray[fila][columna].setImageResource(R.drawable.cruz)
            }
        } else {
            if (esBlanco) {
                ibArray[fila][columna].setImageResource(R.drawable.circulo)
            }
        }
        val tv=findViewById<TextView>(R.id.tv_ganador)
        if (verificarGanador(ibArray)!=0){
            when (verificarGanador(ibArray)) {
                1->{
                    tv.setText("Jugador X ha ganado")
                    tv.isVisible=true;
                }
                2->{
                    tv.setText("Jugador O ha ganado")
                    tv.isVisible=true;
                }
            }
        }

        return ibArray

    }


    private fun verificarGanador(ibArray: Array<Array <ImageButton>>): Int {

        var contadorX:Int=0;
        var contadorO:Int=0;
        var ib:ImageButton;
        var retorno:Int;


        for(i in 0 until 3){

            contadorX=0
            contadorO=0

            for (j in 0 until 3){
                ib=ibArray[i][j];
                if (ib.drawable.constantState==resources.getDrawable(R.drawable.circulo).constantState){
                    contadorO++;
                }
                if (ib.drawable.constantState==resources.getDrawable(R.drawable.cruz).constantState){
                    contadorX++;
                }
            }
            if (contadorX==3){
                return 1;
            }
            if (contadorO==3){
                return 2;
            }
        }

        for(i in 0 until 3){

            contadorX=0
            contadorO=0

            for (j in 0 until 3){
                ib=ibArray[j][i];
                if (ib.drawable.constantState==resources.getDrawable(R.drawable.circulo).constantState){
                    contadorO++;
                }
                if (ib.drawable.constantState==resources.getDrawable(R.drawable.cruz).constantState){
                    contadorX++;
                }
            }
            if (contadorX==3){
                return 1;
            }
            if (contadorO==3){
                return 2;
            }
        }

        contadorX=0
        contadorO=0

        for (j in 0 until 3){
            ib=ibArray[j][j];
            if (ib.drawable.constantState==resources.getDrawable(R.drawable.circulo).constantState){
                contadorO++;
            }
            if (ib.drawable.constantState==resources.getDrawable(R.drawable.cruz).constantState){
                contadorX++;
            }
        }

        if (contadorX==3){
            return 1;
        }
        if (contadorO==3){
            return 2;
        }

        return 0
    }

}