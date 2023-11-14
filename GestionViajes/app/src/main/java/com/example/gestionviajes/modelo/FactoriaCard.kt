package Modelo

import android.content.Context
import android.content.Intent
import com.example.gestionviajes.AsignarTarea
import com.example.gestionviajes.Camiones
import com.example.gestionviajes.Detalle
import com.example.gestionviajes.Empleados
import com.example.gestionviajes.Inicio
import com.example.gestionviajes.R
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

object FactoriaCard {
    private val db= FirebaseFirestore.getInstance()
    fun inicioAdmin(contexto: Context):ArrayList<Card> {
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




    fun documentoACardCamion(contexto: Context, document: DocumentSnapshot):Card{
        return Card(document.id,"@drawable/"+document.getString("marca").toString(),Intent(contexto,Detalle::class.java),document.getString("km").toString())
    }
    fun documentoACardEmpleado(contexto: Context, document: DocumentSnapshot):Card{
        return Card(document.id,"@drawable/empleado",Intent(contexto,Detalle::class.java),document.getString("telefono").toString())
    }

    fun sincronizar(contexto: Context) {
        val db = FirebaseFirestore.getInstance()
        val camionesCollection = db.collection("camiones")

        camionesCollection.get()
            .addOnSuccessListener { documents ->
                Almacen.camiones.clear()
                for (document in documents) {
                    // Convierte cada documento en un objeto Card y agrégalo a Almacen.camiones
                    val card = documentoACardCamion(contexto,document)
                    Almacen.camiones.add(card)
                }


            }
            .addOnFailureListener { exception ->
                // Manejar errores al obtener documentos
                exception.printStackTrace()
            }

        val empleadosCollection = db.collection("empleados")

        empleadosCollection.get()
            .addOnSuccessListener { documents ->
                Almacen.empleados.clear()
                for (document in documents) {
                    // Convierte cada documento en un objeto Card y agrégalo a Almacen.camiones
                    val card = documentoACardEmpleado(contexto,document)
                    Almacen.empleados.add(card)
                }


            }
            .addOnFailureListener { exception ->
                // Manejar errores al obtener documentos
                exception.printStackTrace()
            }

        val claves = db.collection("claveAdmin")

        claves.get()
            .addOnSuccessListener { documents ->
                Almacen.adminClaves.clear()
                for (document in documents) {
                    Almacen.adminClaves.add(document.getString("clave").toString())
                }
            }
            .addOnFailureListener { exception ->
                // Manejar errores al obtener documentos
                exception.printStackTrace()
            }
    }
}