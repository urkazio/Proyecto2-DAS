package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActividadLogin1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_login1);
    }

    public void comenzar(View v){
          Intent intent = new Intent(this, ActividadLogin2.class);
          startActivity(intent);
    }

    public void tengoCuenta(View v){
        Intent intent = new Intent(this, ActividadLogin3.class);
        startActivity(intent);
    }
}