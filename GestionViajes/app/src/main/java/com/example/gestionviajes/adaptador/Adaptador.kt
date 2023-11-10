package com.example.gestionviajes.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionviajes.R

class Adaptador: RecyclerView.Adapter<Adaptador.Vista>() {

    val titulos = arrayOf("Lista camiones","Asignar viaje")
    val imagenes = arrayOf(R.drawable.fantasma,R.drawable.fantasma)
    inner class Vista(vistaItem: View): RecyclerView.ViewHolder(vistaItem){
        var imagen:ImageView
        var titulo:TextView

        init {
            imagen=vistaItem.findViewById(R.id.card_image)
            titulo=vistaItem.findViewById(R.id.card_titulo)
        }
    }

    override fun onCreateViewHolder(vista: ViewGroup, tipoVista: Int): Adaptador.Vista {
        val v=LayoutInflater.from(vista.context).inflate(R.layout.card_layout,vista,false)
        return Vista(v)
    }

    override fun onBindViewHolder(holder: Vista, posicion: Int) {
        holder.titulo.text=titulos[posicion]
        holder.imagen.setImageResource(imagenes[posicion])
    }

    override fun getItemCount(): Int {
        return titulos.size
    }
}