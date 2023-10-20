package com.example.planetas

class Planeta {
    lateinit var nombre:String;
    var radio:Double = 0.0;
    var gravedad:Double = 0.0;
    var masa:Int = 0;
    var distancia_sol:Int=0;
    lateinit var tipo:TipoPlaneta;


    constructor(
        nombre: String,
        radio: Double,
        gravedad: Double,
        masa: Int,
        distancia_sol: Int,
        tipo: TipoPlaneta
    ) {
        this.nombre = nombre
        this.radio = radio
        this.gravedad = gravedad
        this.masa = masa
        this.distancia_sol = distancia_sol
        this.tipo = tipo
    }

    constructor(
    ) {
    }
    override fun toString(): String {
        return "nombre='$nombre',\nradio=$radio,\ngravedad=$gravedad,\nmasa=$masa,\ndistancia_sol=$distancia_sol,\ntipo=$tipo"
    }



}
public enum class TipoPlaneta {
    ROCOSO,GIGANTE_GASEOSO,GIGANTE_HELADO
}
