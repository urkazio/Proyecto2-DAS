package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActividadLoginRegistro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_loginregistro);
    }

    public void comenzar(View v){
          Intent intent = new Intent(this, ActividadRegistrarse1.class);
          startActivity(intent);
    }

    public void tengoCuenta(View v){
        Intent intent = new Intent(this, ActividadLogin.class);
        startActivity(intent);
    }
}