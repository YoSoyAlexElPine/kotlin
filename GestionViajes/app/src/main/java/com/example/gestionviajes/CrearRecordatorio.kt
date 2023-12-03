package com.example.gestionviajes

import Modelo.Almacen
import Modelo.Card
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gestionviajes.databinding.CrearRecordatorioBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class CrearRecordatorio : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private var horaSeleccionada: String = ""
    private var fechaSeleccionada: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val b = CrearRecordatorioBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.bDia.setOnClickListener {
            showDatePicker()
        }

        b.bHora.setOnClickListener {
            showTimePicker()
        }

        b.bEnviar.setOnClickListener {

            if (!b.tbAsunto.text.isNullOrEmpty() &&
                !b.tbContenido.text.isNullOrEmpty()
            ) {

                var asunto = b.tbAsunto.text.toString()
                var contenido = b.tbContenido.text.toString().trim()+"\n\nDia: "+fechaSeleccionada+"\nHora: "+horaSeleccionada

                scheduleNotification(asunto)

                if (!Almacen.recordatorios.any { it.nombre == asunto }) {
                    try {
                        // Guardar en Firestore
                        db.collection("recordatorios").document(asunto).set(
                            hashMapOf(
                                "asunto" to asunto,
                                "contenido" to contenido,
                            )
                        )

                        val card= Card(
                            asunto,
                            "@drawable/recordatorio",
                            Intent(this, Detalle::class.java),
                            contenido
                        )

                        // Agregar a Almacen.camiones
                        Almacen.recordatorios.add(card)

                        b.tbAsunto.setText("")
                        b.tbContenido.setText("")

                    } catch (e: Exception) {
                        // Manejo de excepciones al guardar en Firestore
                        /*db.collection("camiones").document(nombre).set(
                            hashMapOf(
                                "chofer" to chofer,
                                "marca" to marca,
                                "km" to km
                            )
                        )*/

                    }
            }
            } else {
                // Mostrar mensaje si el camión ya existe
                val mensaje =
                    b.tbAsunto.text.toString() + " " + this.getString(R.string.YaExiste)
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, yearSelected, monthOfYear, dayOfMonthOfYear ->
                fechaSeleccionada = "$dayOfMonthOfYear/${monthOfYear + 1}/$yearSelected"
            },
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minuteOfHour ->
                horaSeleccionada = "$hourOfDay:$minuteOfHour"
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }

    @SuppressLint("MissingPermission")
    private fun scheduleNotification(asunto:String) {
        if (fechaSeleccionada.isNotEmpty() && horaSeleccionada.isNotEmpty()) {
            val dateTime = "$fechaSeleccionada $horaSeleccionada"
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val date = dateFormat.parse(dateTime)

            val calendar = Calendar.getInstance()
            calendar.time = date

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(this, NotificationReceiver::class.java)
            intent.putExtra("asunto",asunto)

            val pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }
}
