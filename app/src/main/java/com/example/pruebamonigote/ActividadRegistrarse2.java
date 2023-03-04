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
        editPass.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        editPass2 = findViewById(R.id.editPass2);
        editPass.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);


    }

    public void continuar(View v) {
        if (editPass.equals(editPass2)){
            Intent intent = new Intent(this, ActividadRegistrarse3.class);
            //pasar extras
            startActivity(intent);
        }else{
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
        }

    }
}