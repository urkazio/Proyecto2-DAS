package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

public class ActividadLoginRegistro extends AppCompatActivity {
    public static String storeLang;
    private static boolean preferenciasCargadas = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_loginregistro);

        if (!preferenciasCargadas){
            preferenciasCargadas=true;
            cargarPreferencias();
        }
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // guardar el idioma seleccionado a ya que a la hora de rotar sino se pondria
        // por defecto el idioma predetermionado y no el elegido por el usuario
        super.onSaveInstanceState(savedInstanceState);

        String idioma = getResources().getConfiguration().getLocales().get(0).toString();
        savedInstanceState.putString("idioma", storeLang);
        System.out.println("idioma guardado:" +idioma);

    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {


        // recuperar el idioma guardado antes de destruir la actividad y aplicarlo
        super.onRestoreInstanceState(savedInstanceState);
        String idioma = savedInstanceState.getString("idioma");
        System.out.println("idioma recuperado:" +idioma);
        cambiarIdioma(idioma);
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
        storeLang="en";
        cambiarIdioma("en");
        guardarPreferencias();
    }
    public void idiomaEspañol(View v) {
        storeLang="es";
        cambiarIdioma("es");
        guardarPreferencias();
    }

    public void cambiarIdioma(String s){
        // definir una nueva localización mediante el código de dos letras
        // del idioma y crear una nueva configuración para la aplicación
        Locale nuevaloc = new Locale(s);
        Locale.setDefault(nuevaloc);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(nuevaloc);
        configuration.setLayoutDirection(nuevaloc);

        //actualizar la configuración de todos los recursos de la aplicación mediante el método updateConfiguration
        Context context = getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());

        // recargar de nuevo la actividad para que tambien tenga efecto en la actividad actual
        finish();
        startActivity(getIntent());
    }

    private void guardarPreferencias(){
        Toast.makeText(this, R.string.str126, Toast.LENGTH_LONG).show();
        SharedPreferences prfs = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prfs.edit();
        editor.putString("idiomapref", storeLang);
        editor.commit();

    }

    private void cargarPreferencias(){
        SharedPreferences prfs = getSharedPreferences("preferencias", Context.MODE_PRIVATE); //ya esta creado asi que lo abre
        String idioma = prfs.getString("idiomapref", "es");
        cambiarIdioma(idioma);
    }
}
