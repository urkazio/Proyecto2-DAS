package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Locale;

public class ActividadPrincipal extends AppCompatActivity {

    String user ="";
    AlertDialog.Builder dialog;
    public static boolean dialogo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("User");
        }

        Context context = this;

        // pedir el permiso para pedir notifiaciones
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }

        TextView tvRutinasPers = findViewById(R.id.tvRutinasPers);
        tvRutinasPers.setText(getString(R.string.str117)+" "+user+"! "+getString(R.string.str25));


        // crear el fichero "Nombreuser.txt" que contiene el indice de las rutinas creadas por el usuario 'user' en caso de no existir
        // path --> /app/app/com.example.pruebamonigote/files/Nombreuser.txt
        if (!fileExist(user+".txt")){
            try {
                OutputStreamWriter fichero = new OutputStreamWriter(context.openFileOutput(user+".txt", Context.MODE_PRIVATE));
                fichero.close();
            } catch (IOException e) { }
        }
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // guardar el idioma seleccionado a ya que a la hora de rotar sino se pondria
        // por defecto el idioma predetermionado y no el elegido por el usuario
        super.onSaveInstanceState(savedInstanceState);
        String idioma = getResources().getConfiguration().getLocales().get(0).toString();
        savedInstanceState.putString("idioma", idioma);
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        // recuperar el idioma guardado antes de destruir la actividad y aplicarlo
        super.onRestoreInstanceState(savedInstanceState);
        String idioma = savedInstanceState.getString("idioma");

        Locale nuevaloc = new Locale(idioma);
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

    public boolean fileExist(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    public void explorar(View v) {
        Intent intent = new Intent(this, ActividadListaRutinasPred.class);
        startActivity(intent);
    }
    public void crear(View v) {
        DialogoCrearRutina dR = new DialogoCrearRutina();
        dR.onCreateDialog(this, user);
        ActividadPrincipal.dialogo=true;

    }

    public void ver(View v) {
        Intent intent = new Intent(this, ActividadMisRutinas.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dialogo){
            DialogoCrearRutina dR = new DialogoCrearRutina();
            dR.onCreateDialog(this, user);
        }
    }


}