package AuxiliarDB

import Modelo.Card
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.gestionviajes.AuxiliarDB.AdminConexion
import com.example.gestionviajes.Detalle

/**
 * Objeto Singleton para gestionar operaciones en la base de datos.
 *
 * Este objeto proporciona funciones para operar la base de datos, como agregar, eliminar, modificar y buscar registros.
 * También proporciona una función para obtener la lista de empleados desde la base de datos.
 *
 * @property DATABASE_NAME Nombre de la base de datos.
 * @property DATABASE_VERSION Número de versión de la base de datos.
 * @constructor Crea un objeto para gestionar operaciones en la base de datos.
 * @author Alex Pineño Sanchez
 */
object Conexion {

    private var DATABASE_NAME = "camiones.db3" // Nombre de la base de datos
    private var DATABASE_VERSION = 1 // Número de versión de la base de datos

    /**
     * Función para cambiar el nombre de la base de datos.
     *
     * @param nacionalidadBD Nuevo nombre de la base de datos.
     */
    fun cambiarBD(nacionalidadBD: String) {
        this.DATABASE_NAME = nacionalidadBD
    }

    /**
     * Agrega una tarjeta (registro) a la base de datos.
     *
     * @param contexto Contexto de la actividad.
     * @param p Tarjeta a agregar.
     * @return Código de la fila insertada.
     */
    fun addCard(contexto: AppCompatActivity, p: Card): Long {
        val admin = AdminConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase
        val registro = ContentValues()

        registro.put("nombre", p.nombre)
        registro.put("marca", "@drawable/"+p.imagen)
        registro.put("km",p.detalle)

        val codigo = bd.insert("camiones", null, registro)
        bd.close()
        return codigo
    }

    /**
     * Elimina una tarjeta (registro) de la base de datos.
     *
     * @param contexto Contexto de la actividad.
     * @param nombre Nombre de la tarjeta a eliminar.
     * @return Cantidad de filas eliminadas.
     */
    fun delCard(contexto: AppCompatActivity, nombre: String): Int {
        val admin = AdminConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase

        val cant = bd.delete("camiones", "nombre=?", arrayOf(nombre))
        bd.close()
        return cant
    }

    /**
     * Modifica una tarjeta (registro) de la base de datos.
     *
     * @param contexto Contexto de la actividad.
     * @param nombre Nombre de la tarjeta a modificar.
     * @param p Nueva tarjeta con los datos actualizados.
     * @param objeto Objeto relacionado con la tarjeta.
     * @return Cantidad de filas modificadas.
     */
    fun modCard(contexto: AppCompatActivity, nombre: String, p: Card, objeto: String): Int {
        val admin = AdminConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase
        val registro = ContentValues()

        registro.put("km", p.detalle)

        val cant = bd.update("camiones", registro, "nombre=?", arrayOf(nombre))
        bd.close()
        return cant
    }

    /**
     * Busca una tarjeta (registro) en la base de datos por nombre y la devuelve como un objeto Card.
     *
     * @param contexto Contexto de la actividad.
     * @param nombre Nombre de la tarjeta a buscar.
     * @return Objeto Card encontrado o null si no se encuentra.
     */
    fun buscarCard(contexto: AppCompatActivity, nombre: String): Card? {
        var p: Card? = null
        val admin = AdminConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.readableDatabase

        val fila = bd.rawQuery("SELECT telefono FROM empleados WHERE nombre=?", arrayOf(nombre))

        if (fila.moveToFirst()) {
            p = Card(nombre, "drawable/", Intent(contexto, Detalle::class.java), fila.getString(0))
        }
        bd.close()
        return p
    }

    /**
     * Obtiene la lista de empleados como una lista de objetos Card desde la base de datos.
     *
     * @param contexto Contexto de la actividad.
     * @return Lista de objetos Card (empleados) desde la base de datos.
     */
    fun obtenerEmpleados(contexto: Context): MutableList<Card> {
        val Cards: MutableList<Card> = mutableListOf()

        val admin = AdminConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.readableDatabase

        val fila = bd.rawQuery("select nombre,marca,km from empleados", null)
        while (fila.moveToNext()) {
            val imagen = "@drawable/"+fila.getString(1)
            val km = fila.getString(2)
            val p = Card(fila.getString(0), imagen, Intent(contexto, Detalle::class.java), km)
            Cards.add(p)
        }
        bd.close()
        return Cards
    }
}
