package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ActividadMisRutinas extends AppCompatActivity {
    String idRutina;
    String user ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_mis_rutinas);
        RecyclerView lalista= findViewById(R.id.elreciclerview);

        ArrayList<Integer> imagenes = new ArrayList<Integer>();
        ArrayList<String> nombres = new ArrayList<String>();
        ArrayList<String> decripciones = new ArrayList<String>();


        /** se definen los elementos que van a ir dentro del RecyclerView
         *  dichos elementos serán ajustados en su item_layout **/


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("user");
        }

        System.out.println("fichero -->  "+user+".txt");

        try {
            BufferedReader fich = new BufferedReader(new InputStreamReader(openFileInput(user+".txt")));
            while(fich.ready()) {
                String line = fich.readLine();
                /** cada linea contiene el nombre y descripcion de la rutina separado por comas*/
                String[] elem = line.split(","); ///elem [0] --> idRutina && elem [1] --> logo && elem [2] --> nombre && elem [3] --> descr;


                String logo = elem [0];
                System.out.println(logo);
                String nombre = elem [1];
                System.out.println(nombre);
                String descr = elem [2];
                System.out.println(descr);

                int id = getResources().getIdentifier(logo, "drawable", getPackageName());

                imagenes.add(id);
                nombres.add(nombre);
                decripciones.add(descr);

            }
            fich.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ElAdaptadorRecycler eladaptador = new ElAdaptadorRecycler(nombres.toArray(new String[nombres.size()]), imagenes.stream().mapToInt(i -> i).toArray() , decripciones.toArray(new String[decripciones.size()]), user);
        lalista.setAdapter(eladaptador);


        /** definir el aspecto del RecyclerView --> horizontal, vertical, grid... **/
        LinearLayoutManager elLayoutLineal= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        lalista.setLayoutManager(elLayoutLineal);

    }

}