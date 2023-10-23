package Adaptador

import Modelo.Ajedrecista
import Modelo.Almacen
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
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerajedrez.MainActivity
import com.example.recyclerajedrez.MainActivity2
import com.example.recyclerajedrez.R

class Adaptador(var personajes: ArrayList<Ajedrecista>, var context: Context) :
    RecyclerView.Adapter<Adaptador.ViewHolder>() {

    companion object {
        var seleccionado: Int = -1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = personajes.get(position)
        holder.bind(item, context, position, this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        val viewHolder = ViewHolder(vista)

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, MainActivity2::class.java)
            context.startActivity(intent)
        }

        return viewHolder
    }


    override fun getItemCount(): Int {
        return personajes.size
    }







    class ViewHolder(vista: View) :RecyclerView.ViewHolder(vista){

        val nombrePersonaje = vista.findViewById(R.id.txtNombre) as TextView
        val elo = vista.findViewById(R.id.txtElo) as TextView
        val avatar = vista.findViewById(R.id.i_fotoAjedrecista) as ImageView

        val btnDetalleEspcifico = vista.findViewById<Button>(R.id.b_detalle2) as Button


        @SuppressLint("ResourceAsColor")
        fun bind(pers: Ajedrecista, context: Context, pos: Int, miAdaptadorRecycler: Adaptador){
            nombrePersonaje.text = pers.nombre
            elo.text = pers.elo

            if (pers.nombre.equals("Magnus Carlsen")){
                val uri = "@drawable/carlsen"
                val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                var res: Drawable = context.resources.getDrawable(imageResource)
                avatar.setImageDrawable(res)
            }
            else {
                Glide.with(context).load(pers.imagen).into(avatar)
                }

            if (pos == Adaptador.seleccionado) {
                with(nombrePersonaje) {
                    this.setTextColor(resources.getColor(R.color.blue))
                }
                elo.setTextColor(R.color.blue)
            }
            else {
                with(nombrePersonaje) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
                elo.setTextColor(R.color.black)
            }

            itemView.setOnClickListener {
                if (pos == Adaptador.seleccionado){
                    Adaptador.seleccionado = -1
                }
                else {
                    Adaptador.seleccionado = pos
                    Log.e("ACSC0", "Seleccionado: ${Almacen.personajes.get(Adaptador.seleccionado).toString()}")
                }

                miAdaptadorRecycler.notifyDataSetChanged()


                Toast.makeText(context, "Valor seleccionado " +  Adaptador.seleccionado.toString(), Toast.LENGTH_SHORT).show()

            }
            itemView.setOnLongClickListener(View.OnLongClickListener {
                Log.e("ACSCO","Seleccionado con long click: ${Almacen.personajes.get(pos).toString()}")
                Almacen.personajes.removeAt(pos)
                miAdaptadorRecycler.notifyDataSetChanged()
                true
            })


            btnDetalleEspcifico.setOnClickListener {
                Log.e("Fernando","Has pulsado el botón de ${pers}")
                var inte : Intent = Intent(MainActivity.contextoPrincipal, MainActivity2::class.java)
                inte.putExtra("obj",pers)
                ContextCompat.startActivity(MainActivity.contextoPrincipal, inte, null)
            }
        }


    }
}

