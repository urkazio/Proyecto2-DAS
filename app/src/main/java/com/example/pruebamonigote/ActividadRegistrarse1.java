package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActividadRegistrarse1 extends AppCompatActivity {

    public static ActividadRegistrarse1 actividadRegistrarse1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registrarse1);
        actividadRegistrarse1=this;
    }


    public void continuar(View v) {
        Intent intent = new Intent(this, ActividadRegistrarse2.class);
        startActivity(intent);
    }


}