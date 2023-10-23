package Modelo

import com.example.recyclerajedrez.R
import java.util.Locale


object FactoriaPersonaje {
    fun generaPersonaje(): Ajedrecista {
        var nombres = listOf<String>(
            "Garry Kasparov",
            "Anatoly Karpov",
            "Bobby Fischer",
            "Magnus Carlsen",
            "Viswanathan Anand",
            "Jose Capablanca",
            "Veselin Topalov",
            "Judith Polgar",
            "Mikhail Tal",
            "Vladimir Kramnik"
        )

        var imagenes = listOf<String>(
            R.drawable.anand.toString(),
            R.drawable.capablanca.toString(),
            R.drawable.carlsen.toString(),
            R.drawable.fischer.toString(),
            R.drawable.tal.toString(),
            R.drawable.karpov.toString(),
            R.drawable.kasparov.toString(),
            R.drawable.polgar.toString(),
            R.drawable.topalov.toString(),
            R.drawable.vladimir.toString()
        );
        var nombrePersonaje = nombres[(nombres.indices).random()]
        var p = Ajedrecista(
            nombrePersonaje, 2000.toString(), imagenes[(imagenes.indices).random()]
        )

        when(p.nombre){
            "Magnus Carlsen"->p.imagen=R.drawable.carlsen.toString()
            "Viswanathan Anand"->p.imagen=R.drawable.anand.toString()
            "Jose Capablanca"->p.imagen=R.drawable.capablanca.toString()
            "Bobby Fischer"->p.imagen=R.drawable.fischer.toString()
            "Mikhail Tal"->p.imagen=R.drawable.tal.toString()
            "Anatoly Karpov"->p.imagen=R.drawable.karpov.toString()
            "Garry Kasparov"->p.imagen=R.drawable.kasparov.toString()
            "Judith Polgar"->p.imagen=R.drawable.polgar.toString()
            "Veselin Topalov"->p.imagen=R.drawable.topalov.toString()
            "Vladimir Kramnik"->p.imagen=R.drawable.vladimir.toString()
        }

        return p
    }


}