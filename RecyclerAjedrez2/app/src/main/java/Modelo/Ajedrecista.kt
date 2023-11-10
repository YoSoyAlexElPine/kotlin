package Modelo

import java.io.Serializable

data class Ajedrecista(
    var nombre:String, var elo:String, var imagen:String,var nacionalidad:String,
    var mundiales:Int,var rankingFIFE:Int,
    var estilo:String,var rivalidades:String,var cusiosidades:String,
    var porcentajeVictorias:Int
) : Serializable