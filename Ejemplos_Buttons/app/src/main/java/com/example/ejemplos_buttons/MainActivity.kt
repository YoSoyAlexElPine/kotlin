package com.example.ejemplos_buttons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton

class MainActivity : AppCompatActivity() {

    lateinit var edTexto:EditText;
    lateinit var boton: Button;
    lateinit var checkBox: CheckBox;
    lateinit var botonRadio1:RadioButton;
    lateinit var botonRadio2:RadioButton;
    lateinit var botonRadio3:RadioButton;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edTexto=findViewById(R.id.miTexto);
        boton=findViewById(R.id.boton_pulsame);
        checkBox=findViewById(R.id.checkBox);

        botonRadio1=findViewById(R.id.botonRadio1);
        botonRadio2=findViewById(R.id.botonRadio2);
        botonRadio3=findViewById(R.id.botonRadio3);

        


        checkBox.setOnClickListener{
            if(checkBox.isChecked){
                edTexto.setText("Licencia Aceptada");
            } else {
                edTexto.setText("Licencia sin aceptar")
            }
        };


        boton.setOnClickListener {
            if (botonRadio1.isActivated){
                edTexto.setText("")
            }
        }
    }
}