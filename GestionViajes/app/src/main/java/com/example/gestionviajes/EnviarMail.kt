package com.example.gestionviajes

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.gestionviajes.databinding.EnviarMailBinding

class EnviarMail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val b=EnviarMailBinding.inflate(layoutInflater)

        setContentView(b.root)


        b.bEnviar.setOnClickListener(){
            val destinatario = b.tbDestinatario.text.toString()
            val asunto = b.tbDestinatario.text.toString()
            val mensaje = b.tbContenido.text.toString()

            // Crear un Intent con la acción ACTION_SENDTO para enviar el correo
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:") // Establecer el esquema como "mailto:"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(destinatario)) // Establecer el destinatario del correo
                putExtra(Intent.EXTRA_SUBJECT, asunto) // Establecer el asunto del correo
                putExtra(Intent.EXTRA_TEXT, mensaje) // Establecer el cuerpo del correo
            }

            // Verificar si existe alguna aplicación de correo electrónico para manejar el intent

            startActivity(intent)
        }
    }

}