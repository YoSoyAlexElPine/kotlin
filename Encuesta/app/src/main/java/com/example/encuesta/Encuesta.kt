package com.example.encuesta

class Encuesta {
    lateinit var nombre:String
    lateinit var so:String;
    lateinit var especialidad: String
    lateinit var horas:Number


    constructor(nombre: String, so: String, especialidad: String, horas: Number) {
        this.nombre = nombre
        this.so = so
        this.especialidad = especialidad
        this.horas = horas
    }

    override fun toString(): String {
        return "Nombre= '$nombre' SO='$so' Especialidad='$especialidad' Horas=$horas"
    }


}