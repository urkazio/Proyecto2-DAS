package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ActividadListaRutinasPred extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_lista_rutinas_pred);
        RecyclerView lalista= findViewById(R.id.elreciclerview);

        //ArrayList<Integer> imagenes = new ArrayList<Integer>();
        ArrayList<String> nombres = new ArrayList<String>();
        ArrayList<String> decripciones = new ArrayList<String>();


        /** se definen los elementos que van a ir dentro del RecyclerView
         *  dichos elementos serán ajustados en su item_layout **/

        InputStream fich = getResources().openRawResource(R.raw.rutinaspred );
        BufferedReader reader = new BufferedReader( new InputStreamReader(fich));
        try {
            /** leer mientras haya lineas*/
            while(reader.ready()) {
                String line = reader.readLine();
                /** cada linea contiene el nombre y descripcion de la rutina separado por comas*/
                String[] elem = line.split(","); ///elem [0] --> logo && elem [1] --> nombre && elem [2] --> descr;

                //String logo = elem [0];
                String nombre = elem [1];
                System.out.println(nombre);
                String descr = elem [2];
                System.out.println(descr);

                //imagenes.add(logo);
                nombres.add(nombre);
                decripciones.add(descr);

            }
            fich.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        int[] imagenes= {R.drawable.logo};
        //imagenes.stream().mapToInt(i -> i).toArray()
        ElAdaptadorRecycler eladaptador = new ElAdaptadorRecycler(nombres.toArray(new String[nombres.size()]), imagenes , decripciones.toArray(new String[decripciones.size()]));
        lalista.setAdapter(eladaptador);


        /** definir el aspecto del RecyclerView --> horizontal, vertical, grid... **/
        LinearLayoutManager elLayoutLineal= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        lalista.setLayoutManager(elLayoutLineal);

    }
}