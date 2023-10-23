package Modelo

object FactoriaListaPersonaje {
    fun generaLista(cant:Int):ArrayList<Ajedrecista> {
        var lista = ArrayList<Ajedrecista>(1)
        for(i in 1..cant){
            lista.add(FactoriaPersonaje.generaPersonaje())
        }
        return lista
    }
}