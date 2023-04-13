package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.Manifest;
import java.util.Locale;

public class ActividadLoginRegistro extends AppCompatActivity {
    private static boolean preferenciasCargadas = false;
    private Context c = this;
    private Activity a = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!preferenciasCargadas){
            preferenciasCargadas=true;
            String idioma = GestorIdiomas.cargarPreferencias(c,a);
            Toast.makeText(c, a.getString(R.string.str127) +" ("+idioma+")", Toast.LENGTH_LONG).show();
        }

        System.out.println("hola");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_loginregistro);

        //requestLocationPermission();
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 99);
        }
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // guardar el idioma seleccionado a ya que a la hora de rotar sino se pondria
        // por defecto el idioma predetermionado y no el elegido por el usuario
        super.onSaveInstanceState(savedInstanceState);
        preferenciasCargadas=false;
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
        a.finish();
        a.startActivity(a.getIntent());
    }
    public void idiomaEspañol(View v) {
        GestorIdiomas.storeLang="es";
        GestorIdiomas.cambiarIdioma("es",c,a);
        GestorIdiomas.guardarPreferencias(c,a);
        a.finish();
        a.startActivity(a.getIntent());
    }

}
