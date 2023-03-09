package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ActividadPrincipal extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        // importar android Manifest
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }


        // crear el fichero interno "misrutinasNombreuser.txt" para que posteriormente el usuario pueda añadir rutinas
        // path --> /app/app/com.example.pruebamonigote/files/misrutinas.txt
        // obtener el nombre de usuario de la base de datos para que cada user tenga un fichero dedicado
        String user ="urko";
        Context context = this;
        System.out.println("Existe:"+fileExist("misrutinas"+user+".txt"));

        // crear el fichero que contiene el indice de las rutinas creadas por el usuario 'user' en caso de no existir
        if (!fileExist("misrutinas"+user+".txt")){
            try {
                OutputStreamWriter fichero = new OutputStreamWriter(context.openFileOutput("misrutinas"+user+".txt", Context.MODE_PRIVATE));
                fichero.close();
            } catch (IOException e) { }
        }


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
        dR.onCreateDialog(this, v);


    }
    public void ver(View v) {
        Intent intent = new Intent(this, ActividadMisRutinas.class);
        startActivity(intent);
    }
}