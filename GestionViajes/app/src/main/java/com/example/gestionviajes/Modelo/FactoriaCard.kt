package Modelo

import android.content.Context
import android.content.Intent
import com.example.gestionviajes.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

/**
 * FactoriaCard es un objeto singleton que contiene métodos para generar objetos Card y sincronizar datos con Firestore.
 * @author Alex Pineño Sanchez
 */
object FactoriaCard {
    private val db = FirebaseFirestore.getInstance()

    /**
     * Genera una lista de Cards para la pantalla de inicio del administrador.
     * @param contexto El contexto actual de la aplicación.
     * @return Una lista de objetos Card.
     */
    fun inicioAdmin(contexto: Context): ArrayList<Card> {
        val listaCards = arrayListOf(
            Card(
                contexto.getString(R.string.Camiones),
                "@drawable/camion",
                Intent(contexto, Camiones::class.java),
                ""
            ),
            Card(
                contexto.getString(R.string.Empleados),
                "@drawable/logoempleado",
                Intent(contexto, Empleados::class.java),
                ""
            ),
            Card(
                contexto.getString(R.string.AsignarViaje),
                "@drawable/tarea",
                Intent(contexto, AsignarTarea::class.java),
                ""
            )
        )
        return listaCards
    }

    /**
     * Convierte un DocumentSnapshot relacionado con un camión en un objeto Card.
     * @param contexto El contexto actual de la aplicación.
     * @param document El DocumentSnapshot que contiene los datos del camión.
     * @return Un objeto Card con los detalles del camión.
     */
    fun documentoACardCamion(contexto: Context, document: DocumentSnapshot): Card {
        return Card(
            document.id,
            "@drawable/" + document.getString("marca").toString(),
            Intent(contexto, Detalle::class.java),
            document.getString("km").toString()
        )
    }

    /**
     * Convierte un DocumentSnapshot relacionado con un empleado en un objeto Card.
     * @param contexto El contexto actual de la aplicación.
     * @param document El DocumentSnapshot que contiene los datos del empleado.
     * @return Un objeto Card con los detalles del empleado.
     */
    fun documentoACardEmpleado(contexto: Context, document: DocumentSnapshot): Card {
        return Card(
            document.id,
            "@drawable/empleado",
            Intent(contexto, Detalle::class.java),
            document.getString("telefono").toString()
        )
    }

    /**
     * Sincroniza datos de Firestore para camiones, empleados y claves de administrador.
     * @param contexto El contexto actual de la aplicación.
     */
    fun sincronizar(contexto: Context) {
        val db = FirebaseFirestore.getInstance()

        // Sincronización de datos para la colección 'camiones'
        val camionesCollection = db.collection("camiones")
        camionesCollection.get()
            .addOnSuccessListener { documents ->
                Almacen.camiones.clear()
                for (document in documents) {
                    val card = documentoACardCamion(contexto, document)
                    Almacen.camiones.add(card)
                }
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }

        // Sincronización de datos para la colección 'empleados'
        val empleadosCollection = db.collection("empleados")
        empleadosCollection.get()
            .addOnSuccessListener { documents ->
                Almacen.empleados.clear()
                for (document in documents) {
                    val card = documentoACardEmpleado(contexto, document)
                    Almacen.empleados.add(card)
                }
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }

        // Sincronización de datos para la colección 'claveAdmin'
        val claves = db.collection("claveAdmin")
        claves.get()
            .addOnSuccessListener { documents ->
                Almacen.adminClaves.clear()
                for (document in documents) {
                    Almacen.adminClaves.add(document.getString("clave").toString())
                }
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }
}