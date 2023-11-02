package Adaptador

import AuxiliarDB.Conexion
import Modelo.Ajedrecista
import Modelo.Almacen
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
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
import com.example.recyclerajedrez.Pantalla2
import com.example.recyclerajedrez.R


class Adaptador(var ajedrecistas: ArrayList<Ajedrecista>, var contexto: Context) :
    RecyclerView.Adapter<Adaptador.ViewHolder>() {

    companion object {
        var seleccionado: Int = -1
        lateinit var ajedrecistaSeleccionado: Ajedrecista
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = ajedrecistas.get(position)
        holder.bind(item, contexto, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        val viewHolder = ViewHolder(vista)

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(contexto, Pantalla2::class.java)
            contexto.startActivity(intent)
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return ajedrecistas.size
    }


    class ViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {

        val nombreAjedrecista = vista.findViewById(R.id.txtNombre) as TextView
        val elo = vista.findViewById(R.id.txtElo) as TextView
        val avatar = vista.findViewById(R.id.i_fotoAjedrecista) as ImageView

        val btnDetalleEspecifico = vista.findViewById<Button>(R.id.b_detalle2) as Button

        /*
        * Nose
        * */
        @SuppressLint("ResourceAsColor")
        fun bind(ajedrecista: Ajedrecista, contexto: Context, pos: Int, miAdaptadorRecycler: Adaptador) {
            nombreAjedrecista.text = ajedrecista.nombre
            elo.text = ajedrecista.elo

            try {
                val imageResource: Int = contexto.resources.getIdentifier("@drawable/"+ajedrecista.nombre.toLowerCase(), null, contexto.packageName)
                var res: Drawable = contexto.resources.getDrawable(imageResource)
                avatar.setImageDrawable(res)
            }catch (e: Exception){

                var imagen="@drawable/nuevo"
                ajedrecista.imagen=imagen

                //Glide.with(contexto).load(ajedrecista.imagen).into(avatar)

                ajedrecista.imagen=imagen
                val imageResource: Int = contexto.resources.getIdentifier(imagen, null, contexto.packageName)
                var res: Drawable = contexto.resources.getDrawable(imageResource)
                avatar.setImageDrawable(res)
            }


            if (pos == Adaptador.seleccionado) {
                with(nombreAjedrecista) {
                    this.setTextColor(contexto.resources.getColor(R.color.blue))
                }
                elo.setTextColor(R.color.blue)
            } else {
                with(nombreAjedrecista) {
                    this.setTextColor(contexto.resources.getColor(R.color.black))
                }
                elo.setTextColor(R.color.black)
            }


            /**
             * Accion de pulsar el item_Card
             * */

            itemView.setOnClickListener {
                if (pos == Adaptador.seleccionado) {
                    Adaptador.seleccionado = -1
                } else {
                    Adaptador.seleccionado = pos
                    Log.e("ACSC0", "Seleccionado: ${Almacen.ajedrecistas.get(Adaptador.seleccionado)}")
                }

                miAdaptadorRecycler.notifyDataSetChanged()


                ajedrecistaSeleccionado=ajedrecista

            }
             /*
             * Dejar pulsado item para borrarlo
             * */
            itemView.setOnLongClickListener(View.OnLongClickListener {

                var nombre=Almacen.ajedrecistas[pos].nombre


                Almacen.ajedrecistas.removeAt(pos)

                miAdaptadorRecycler.notifyDataSetChanged()



                true
            })

            /*
            * Boton Detalle
            * */
            btnDetalleEspecifico.setOnClickListener {

                var intent: Intent = Intent(MainActivity.contextoPrincipal, Pantalla2::class.java)
                intent.putExtra("ajedrecista", ajedrecista)
                ContextCompat.startActivity(MainActivity.contextoPrincipal, intent, null)



            }
        }
    }
}
