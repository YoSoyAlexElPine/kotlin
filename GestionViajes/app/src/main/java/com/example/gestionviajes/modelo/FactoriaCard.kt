package Modelo

import android.content.Context
import android.content.Intent
import com.example.gestionviajes.AsignarTarea
import com.example.gestionviajes.Camiones
import com.example.gestionviajes.Inicio

object FactoriaCard {
    fun inicioAdmin(contexto: Context):ArrayList<Card> {
        val listaCards = arrayListOf(
            Card(
                "Camiones",
                "@drawable/camion",
                Intent(contexto, Camiones::class.java)
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

    fun camiones(contexto: Context):ArrayList<Card> {
        val listaCards = arrayListOf(
            Card(
                "Iveco naranja",
                "@drawable/iveco",
                Intent(contexto,Inicio::class.java)
            ),
            Card(
                "Scania 1",
                "@drawable/scania",
                Intent(contexto,Inicio::class.java)
            ),
            Card(
                "Scania 2",
                "@drawable/scania",
                Intent(contexto,Inicio::class.java)
            ),
            Card(
                "Mercedes",
                "@drawable/mercedes",
                Intent(contexto, Inicio::class.java)
            )
        )


        return listaCards
    }
}