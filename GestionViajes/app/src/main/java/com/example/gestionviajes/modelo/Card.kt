package Modelo

import android.content.Intent
import java.io.Serializable

data class Card(
    var titulo:String,var imagen:String,var link: Intent,var detalle:String
) : Serializable