package Modelo

import android.content.Intent
import java.io.Serializable

/**
 * Clase que representa una tarjeta (Card).
 *
 * Esta clase define la estructura de una tarjeta con varios atributos que incluyen nombre, imagen,
 * enlace (link) y detalle. Implementa la interfaz Serializable para permitir la serialización de objetos.
 *
 * @property nombre Nombre de la tarjeta.
 * @property imagen Imagen asociada a la tarjeta.
 * @property link Enlace asociado a la tarjeta (puede ser un Intent u otro tipo de enlace).
 * @property detalle Detalle o información adicional de la tarjeta.
 * @constructor Crea un objeto Card con los parámetros dados.
 * @param nombre Nombre de la tarjeta.
 * @param imagen Imagen asociada a la tarjeta.
 * @param link Enlace asociado a la tarjeta.
 * @param detalle Detalle o información adicional de la tarjeta.
 * @author Alex Pineño Sanchez
 */
data class Card(
    var nombre: String,
    var imagen: String,
    var link: Intent,
    var detalle: String
) : Serializable
