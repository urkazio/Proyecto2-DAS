package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActividadLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_login);
    }

    public void logearse(View v){
        //Identificarse contra la base de datos
        Intent intent = new Intent(this, ActividadLogin.class);
        //poner los extras de los datos del usuario
        startActivity(intent);

    }
}