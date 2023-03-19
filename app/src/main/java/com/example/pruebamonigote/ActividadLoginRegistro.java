package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

public class ActividadLoginRegistro extends AppCompatActivity {
    private static boolean preferenciasCargadas = false;
    private Context c = this;
    private Activity a = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_loginregistro);

        if (!preferenciasCargadas){
            preferenciasCargadas=true;
            String idioma = GestorIdiomas.cargarPreferencias(c,a);
            Toast.makeText(c, a.getString(R.string.str127) +" ("+idioma+")", Toast.LENGTH_LONG).show();
        }
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // guardar el idioma seleccionado a ya que a la hora de rotar sino se pondria
        // por defecto el idioma predetermionado y no el elegido por el usuario
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("idioma", GestorIdiomas.storeLang);

    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        // recuperar el idioma guardado antes de destruir la actividad y aplicarlo
        super.onRestoreInstanceState(savedInstanceState);
        String idioma = savedInstanceState.getString("idioma");
        GestorIdiomas.cambiarIdioma(idioma,c,a);
    }


    public void comenzar(View v){
        Intent intent = new Intent(this, ActividadRegistrarse.class);
        startActivity(intent);
    }

    public void tengoCuenta(View v){
        Intent intent = new Intent(this, ActividadLogin.class);
        startActivity(intent);
    }

    public void idiomaIngles(View v) {
        GestorIdiomas.storeLang="en";
        GestorIdiomas.cambiarIdioma("en",c,a);
        GestorIdiomas.guardarPreferencias(c,a);
    }
    public void idiomaEspañol(View v) {
        GestorIdiomas.storeLang="es";
        GestorIdiomas.cambiarIdioma("es",c,a);
        GestorIdiomas.guardarPreferencias(c,a);
    }

}
