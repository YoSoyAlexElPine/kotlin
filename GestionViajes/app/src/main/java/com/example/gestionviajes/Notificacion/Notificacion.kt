package com.example.gestionviajes.Notificacion

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


    @SuppressLint("MissingPermission")
    fun mostrarNotificacion(context: Context, titulo: String, contenido: String) {
        // Crear un identificador único para la notificación
        val notificationId = 1

        // Crear un canal de notificación (solo es necesario para Android Oreo y superior)
        val channelId = "mi_canal"
        val channelName = "Nombre del Canal"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            // Crear el canal de notificación
            val notificationManager =
                context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        // Crear la notificación usando NotificationCompat.Builder
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Icono de la notificación
            .setContentTitle(titulo) // Título de la notificación
            .setContentText(contenido) // Contenido de la notificación
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Prioridad de la notificación

        // Mostrar la notificación
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(notificationId, builder.build())
    }
