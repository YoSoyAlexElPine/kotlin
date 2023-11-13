package Modelo

import android.content.Context
import android.content.Intent
import com.example.gestionviajes.AsignarTarea
import com.example.gestionviajes.Camiones
import com.example.gestionviajes.DetalleCamion
import com.example.gestionviajes.DetalleEmpleado
import com.example.gestionviajes.Empleados
import com.example.gestionviajes.Inicio
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

object FactoriaCard {
    private val db= FirebaseFirestore.getInstance()
    fun inicioAdmin(contexto: Context):ArrayList<Card> {
        val listaCards = arrayListOf(
            Card(
                "Camiones",
                "@drawable/camion",
                Intent(contexto, Camiones::class.java)
            ),
            Card(
                "Empleados",
                "@drawable/logoempleado",
                Intent(contexto, Empleados::class.java)
            ),
            Card(
                "Asignar Viaje",
                "@drawable/tarea",
                Intent(contexto, AsignarTarea::class.java)
            )
        )


        return listaCards
    }

    fun inicioEmpleado(contexto: Context):ArrayList<Card> {
        val listaCards = arrayListOf(
            Card(
                "titulo",
                "@drawable/fantasma",
                Intent(contexto,Inicio::class.java)
            ),
            Card(
                "titulo",
                "@drawable/fantasma",
                Intent(contexto,Inicio::class.java)
            ),
            Card(
                "titulo",
                "@drawable/fantasma",
                Intent(contexto,Inicio::class.java)
            ),
            Card(
                "titulo",
                "@drawable/fantasma",
                Intent(contexto, Inicio::class.java)
            )
        )


        return listaCards
    }


    fun empleados(contexto: Context):ArrayList<Card> {
        val listaCards = arrayListOf(
            Card(
                "Fede",
                "@drawable/empleado",
                Intent(contexto,DetalleEmpleado::class.java)
            ),
            Card(
                "Xuxo",
                "@drawable/empleado",
                Intent(contexto,DetalleEmpleado::class.java)
            ),
            Card(
                "Jesus",
                "@drawable/empleado",
                Intent(contexto,DetalleEmpleado::class.java)
            )
        )


        return listaCards
    }

    fun documentoACardCamion(contexto: Context, document: DocumentSnapshot):Card{
        return Card(document.id,"@drawable/"+document.getString("marca").toString(),Intent(contexto,DetalleCamion::class.java))
    }
    fun documentoACardEmpleado(contexto: Context, document: DocumentSnapshot):Card{
        return Card(document.id,"@drawable/empleado",Intent(contexto,DetalleEmpleado::class.java))
    }
}