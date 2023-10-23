package Modelo


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

        var razas = listOf<String>("Orco", "Hombre", "Hobbit", "Elfo")
        var imagenes = listOf<String>(
            "https://loremflickr.com/g/320/240/paris",
            "https://loremflickr.com/320/240/dog",
            "https://loremflickr.com/320/240",
            "https://loremflickr.com/320/240/brazil,rio",
            "https://loremflickr.com/320/240/paris,girl/all"
        );
        var nombrePersonaje = nombres[(nombres.indices).random()]
        var p = Ajedrecista(
            nombrePersonaje, razas[(razas.indices).random()], imagenes[(imagenes.indices).random()]
        )
        if (p.nombre.equals("Gandalf")) p.imagen = "gandalf"

        return p
    }
}