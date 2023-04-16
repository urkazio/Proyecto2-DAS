package com.example.pruebamonigote;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.widget.Toast;

import java.util.Locale;

public class GestorIdiomas {

    public static String storeLang = null;

    public static void cambiarIdioma(String s, Context context, Activity a){
        // definir una nueva localización mediante el código de dos letras
        // del idioma y crear una nueva configuración para la aplicación
        Locale nuevaloc = new Locale(s);
        Locale.setDefault(nuevaloc);
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(nuevaloc);
        configuration.setLayoutDirection(nuevaloc);

        //actualizar la configuración de todos los recursos de la aplicación mediante el método updateConfiguration
        Context c = context.createConfigurationContext(configuration);
        context.getResources().updateConfiguration(configuration, c.getResources().getDisplayMetrics());

        System.out.println("fin de cambiarIdioma");

        // recargar de nuevo la actividad para que tambien tenga efecto en la actividad actual

    }

    public static void guardarPreferencias(Context c, Activity a){
        Toast.makeText(c, a.getString(R.string.str126) +" ("+storeLang+")", Toast.LENGTH_LONG).show();
        SharedPreferences prfs = c.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prfs.edit();
        editor.putString("idiomapref", storeLang);
        editor.commit();
    }

    public static String cargarPreferencias(Context c, Activity a){
        SharedPreferences prfs = c.getSharedPreferences("preferencias", Context.MODE_PRIVATE); //ya esta creado asi que lo abre
        String idioma = prfs.getString("idiomapref", "es");
        storeLang = idioma;
        cambiarIdioma(idioma, c, a);
        return idioma;
    }
}
