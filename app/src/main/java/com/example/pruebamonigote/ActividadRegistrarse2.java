package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActividadRegistrarse2 extends AppCompatActivity {

    EditText editPass;
    EditText editPass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registrarse2);

        //RESTRICCIONES para el editText de la contraseña
        editPass = findViewById(R.id.editPass);
        editPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editPass2 = findViewById(R.id.editPass2);
        editPass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    public void continuar(View v) {
        String pass1=editPass.getText().toString();
        String pass2=editPass2.getText().toString();

        // comprobar que contraseñas no vacias e iguales
        if (pass1.equals(pass2) && !pass1.isEmpty() && !pass2.isEmpty() ){
            Intent intent = new Intent(this, ActividadRegistrarse3.class);
            //pasar extras
            startActivity(intent);
        }else{
            Toast.makeText(this, "Las contraseñas no coinciden o están vacias", Toast.LENGTH_LONG).show();
        }

    }
}