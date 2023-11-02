package Modelo

object FactoriaListaPersonaje {
    fun generaLista(cant:Int):ArrayList<Ajedrecista> {
        val listaAjedrecistas = arrayListOf(
            Ajedrecista(
                "Kasparov",
                "2849",
                "@drawable/kasparov",
                "Ruso",
                5,
                1,
                "Ajedrez agresivo",
                "Rivalidad con Anatoly Karpov",
                "Campeón del mundo más joven",
                72
            ),
            Ajedrecista(
                "Karpov",
                "2720",
                "@drawable/karpov",
                "Ruso",
                3,
                6,
                "Ajedrez posicional",
                "Rivalidad con Garry Kasparov",
                "Mundiales de ajedrez ganados",
                66
            ),
            Ajedrecista(
                "Fischer",
                "2785",
                "@drawable/fischer",
                "Estadounidense",
                0,
                5,
                "Ajedrez táctico",
                "Rivalidad con Boris Spassky",
                "Partida del siglo",
                71
            ),
            Ajedrecista(
                "Carlsen",
                "2847",
                "@drawable/carlsen",
                "Noruego",
                5,
                1,
                "Ajedrez universal",
                "Rivalidad con Vishwanathan Anand",
                "Campeón actual del mundo",
                59
            ),
            Ajedrecista(
                "Anand",
                "2783",
                "@drawable/anand",
                "Indio",
                5,
                10,
                "Ajedrez táctico",
                "Rivalidad con Magnus Carlsen",
                "Gran Maestro",
                61
            ),
            Ajedrecista(
                "Capablanca",
                "2725",
                "@drawable/capablanca",
                "Cubano",
                1,
                2,
                "Ajedrez posicional",
                "Rivalidad con Emanuel Lasker",
                "Capablanca-Pokorny",
                64
            ),
            Ajedrecista(
                "Topalov",
                "2813",
                "@drawable/topalov",
                "Búlgaro",
                1,
                4,
                "Ajedrez agresivo",
                "Rivalidad con Vladimir Kramnik",
                "Topalov-Kramnik",
                55
            ),
            Ajedrecista(
                "Polgar",
                "2675",
                "@drawable/polgar",
                "Húngara",
                0,
                50,
                "Ajedrez táctico",
                "Rivalidad con sus hermanas Susan y Sofia",
                "Las Polgar",
                68
            ),
            Ajedrecista(
                "Tal",
                "2700",
                "@drawable/tal",
                "Letón",
                1,
                11,
                "Ajedrez agresivo",
                "Rivalidad con Mikhail Botvinnik",
                "El Mago de Riga",
                67
            ),
            Ajedrecista(
                "Kramnik",
                "2777",
                "@drawable/vladimir",
                "Ruso",
                2,
                3,
                "Ajedrez universal",
                "Rivalidad con Veselin Topalov",
                "Kramnik-Topalov 2006",
                62
            )
        )


        return listaAjedrecistas
    }
}