package com.example.gestionviajes.Notificacion

import Modelo.Card
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.gestionviajes.Detalle

/**
 * Muestra una notificación en el dispositivo.
 * @param context El contexto actual de la aplicación.
 * @param titulo El título de la notificación.
 * @param contenido El contenido de la notificación.
 * @param card La instancia de Card a enviar al detalle.
 * @author Alex Pineño Sanchez
 */
@SuppressLint("MissingPermission", "LaunchActivityFromNotification")
fun mostrarNotificacion(context: Context, titulo: String, contenido: String, card: Card) {
    // Crear un identificador único para la notificación
    val notificacionId = 1

    // Crear un canal de notificación (solo es necesario para Android Oreo y superior)
    val canalId = "mi_canal"
    val canalNombre = "Nombre del Canal"
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            canalId,
            canalNombre,
            NotificationManager.IMPORTANCE_DEFAULT
        )

        // Crear el canal de notificación
        val notificationManager =
            context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }

    // Crear un intent para el PendingIntent
    val intent = Intent(context, NotificationReceiver::class.java)

    intent.putExtra("objeto", "empleado")
    intent.putExtra("marca", card.imagen)
    intent.putExtra("nombre", card.nombre)
    intent.putExtra("detalle", card.detalle)
    intent.putExtra("link", card.link)


    // Crear un PendingIntent con el intent
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        notificacionId,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    // Ajustar el PendingIntent al builder de la notificación
    val builder = NotificationCompat.Builder(context, canalId)
        .setSmallIcon(android.R.drawable.ic_dialog_info) // Icono de la notificación
        .setContentTitle(titulo) // Título de la notificación
        .setContentText(contenido) // Contenido de la notificación
        .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Prioridad de la notificación
        .setContentIntent(pendingIntent) // Establecer el PendingIntent

    // Mostrar la notificación
    val notificationManagerCompat = NotificationManagerCompat.from(context)
    notificationManagerCompat.notify(notificacionId, builder.build())
}

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(contexto: Context, intent: Intent) {
        val intentDetalles = Intent(contexto, Detalle::class.java)

        val nombre = intent.getStringExtra("nombre").toString()
        val marca = intent.getStringExtra("marca").toString()
        val detalle = intent.getStringExtra("detalle").toString()


        intentDetalles.putExtra("objeto", "empleado")
        intentDetalles.putExtra("marca", marca)
        intentDetalles.putExtra("nombre", nombre)
        intentDetalles.putExtra("detalle", detalle)

        intentDetalles.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        // Inicia la actividad del detalle
        contexto.startActivity(intentDetalles)
    }
}
