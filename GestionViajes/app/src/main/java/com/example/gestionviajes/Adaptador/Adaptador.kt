package com.example.gestionviajes.Adaptador

import Modelo.Card
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionviajes.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Interfaz que define la acción de clic sobre una tarjeta (Card).
 */
interface OnCardClickListener {
    fun onCardClick(card: Card)
}

/**
 * Clase Adaptador para manejar las Cards en un RecyclerView.
 *
 * @property Cards Lista mutable de objetos Card.
 * @property contexto Contexto de la aplicación.
 * @property onCardClickListener Listener para el clic en una Card.
 * @constructor Crea un Adaptador para manejar las Cards en un RecyclerView.
 * @author Autor Alex Pineño Sanchez
 */
class Adaptador(
    var Cards: MutableList<Card>, // Lista mutable de Cards
    var contexto: Context, // Contexto de la aplicación
    private val onCardClickListener: OnCardClickListener // Listener para clic en una Card
) : RecyclerView.Adapter<Adaptador.ViewHolder>() {

    companion object {
        var seleccionado: Int = -1 // Posición seleccionada en la lista de Cards
        lateinit var CardSeleccionado: Card // Card seleccionada
    }

    // Asigna los datos a las vistas de las Cards en el RecyclerView
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Cards[position]
        holder.bind(item, contexto, position, this)
    }

    // Crea un nuevo ViewHolder para las Cards
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(vista)
    }

    // Devuelve la cantidad de Cards en la lista
    override fun getItemCount(): Int {
        return Cards.size
    }

    // Clase interna ViewHolder para gestionar las vistas de las Cards
    inner class ViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {
        val tituloCard = vista.findViewById(R.id.card_titulo) as TextView // TextView para el título de la Card
        val avatar = vista.findViewById(R.id.card_image) as ImageView // ImageView para la imagen de la Card

        // Asigna los datos de la Card a las vistas
        fun bind(card: Card, contexto: Context, pos: Int, miAdaptadorRecycler: Adaptador) {
            tituloCard.text = card.nombre // Asigna el nombre de la Card al TextView

            // Intenta cargar la imagen de la Card
            try {
                val imageResource: Int = contexto.resources.getIdentifier(card.imagen, null, contexto.packageName)
                var res: Drawable = contexto.resources.getDrawable(imageResource)
                avatar.setImageDrawable(res)
            } catch (e: Exception) {
                var imagen = "@drawable/fantasma"
                card.imagen = imagen
                val imageResource: Int = contexto.resources.getIdentifier(imagen, null, contexto.packageName)
                var res: Drawable = contexto.resources.getDrawable(imageResource)
                avatar.setImageDrawable(res)
            }

            // Si la posición es la seleccionada, no se realiza ninguna acción

            // Maneja el clic sobre la Card
            itemView.setOnClickListener {
                if (pos == seleccionado) {
                    seleccionado = -1
                } else {
                    seleccionado = pos
                }

                miAdaptadorRecycler.notifyDataSetChanged() // Notifica cambios en el RecyclerView
                CardSeleccionado = card // Asigna la Card seleccionada

                // Llama al método de la interfaz para manejar la navegación
                onCardClickListener.onCardClick(card)
            }

            // Maneja el clic sobre la Card
            itemView.setOnLongClickListener() {

                MaterialAlertDialogBuilder(contexto, R.style.AlertDialogTheme)
                    .setTitle(contexto.getString(R.string.Informacion))
                    .setMessage(contexto.getString(R.string.InformacionContenido))

                    .setPositiveButton(contexto.getString(R.string.Aceptar)) { dialog, which ->
                        // Respond to positive button press
                    }
                    .show()
                true
            }


            /*itemView.setOnLongClickListener() {

            }*/
        }
    }
}
