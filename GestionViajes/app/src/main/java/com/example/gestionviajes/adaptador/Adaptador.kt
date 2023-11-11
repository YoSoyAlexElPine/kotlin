package com.example.gestionviajes.adaptador

import Modelo.Almacen
import Modelo.Card
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionviajes.Inicio
import com.example.gestionviajes.R

class Adaptador(var Cards: ArrayList<Card>, var contexto: Context) :
    RecyclerView.Adapter<Adaptador.ViewHolder>() {


    companion object {
        var seleccionado: Int = -1
        lateinit var CardSeleccionado: Card
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Cards.get(position)
        holder.bind(item, contexto, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        val viewHolder = ViewHolder(vista)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return Cards.size
    }


    class ViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {

        val tituloCard = vista.findViewById(R.id.card_titulo) as TextView
        val avatar = vista.findViewById(R.id.card_image) as ImageView

        /*
        * Nose
        * */
        @SuppressLint("ResourceAsColor")
        fun bind(card: Card, contexto: Context, pos: Int, miAdaptadorRecycler: Adaptador) {
            tituloCard.text = card.titulo

            try {
                val imageResource: Int = contexto.resources.getIdentifier(card.imagen, null, contexto.packageName)
                var res: Drawable = contexto.resources.getDrawable(imageResource)
                avatar.setImageDrawable(res)
            }catch (e: Exception){

                var imagen="@drawable/fantasma"
                card.imagen=imagen

                //Glide.with(contexto).load(Card.imagen).into(avatar)

                card.imagen=imagen
                val imageResource: Int = contexto.resources.getIdentifier(imagen, null, contexto.packageName)
                var res: Drawable = contexto.resources.getDrawable(imageResource)
                avatar.setImageDrawable(res)
            }


            if (pos == seleccionado) {
                startActivity(contexto,card.link,null)
            }

            /**
             * Accion de pulsar el item_Card
             * */

            itemView.setOnClickListener {
                if (pos == seleccionado) {
                    seleccionado = -1
                } else {
                    seleccionado = pos
                    Log.e("APS", "Seleccionado: ${Almacen.cards.get(Adaptador.seleccionado)}")
                }

                miAdaptadorRecycler.notifyDataSetChanged()


                CardSeleccionado=card

            }


        }
    }




}