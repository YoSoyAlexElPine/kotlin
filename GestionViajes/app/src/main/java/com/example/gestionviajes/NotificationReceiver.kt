package com.example.gestionviajes

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        // Mostrar la notificación
        val notificationManager = NotificationManagerCompat.from(context)

        val channelId = "Your_Channel_ID"
        createNotificationChannel(context, channelId)

        val asunto = intent.getStringExtra("asunto").toString()

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.fantasma)
            .setContentTitle(asunto)
            .setContentText("Recordatorio")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(123, builder.build())
    }

    private fun createNotificationChannel(context: Context, channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel Name"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
