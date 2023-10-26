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


        return p
    }


}