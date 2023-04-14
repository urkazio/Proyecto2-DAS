package com.example.pruebamonigote;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ActividadRegistro1 extends AppCompatActivity {

    public static ActividadRegistro1 actividadRegistro1;
    private static boolean preferenciasCargadas = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Context c = this;
        actividadRegistro1=this;
        if (!preferenciasCargadas){
            preferenciasCargadas=true;
            GestorIdiomas.cargarPreferencias(c,actividadRegistro1);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registro1);
    }


    public void continuar (View v) {
        Intent intent = new Intent(this, ActividadRegistro2.class);
        startActivity(intent);
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // guardar el idioma seleccionado a ya que a la hora de rotar sino se pondria
        // por defecto el idioma predetermionado y no el elegido por el usuario
        super.onSaveInstanceState(savedInstanceState);
        preferenciasCargadas=false;
    }

}