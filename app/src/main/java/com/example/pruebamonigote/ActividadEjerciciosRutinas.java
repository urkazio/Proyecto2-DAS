package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ActividadEjerciciosRutinas extends AppCompatActivity {

    TextView tvNombreRutina;
    TextView tvDescrRutina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_lista_rutinas_pred);
        RecyclerView lalista= findViewById(R.id.elreciclerview);

        tvNombreRutina = findViewById(R.id.tvNombreRutina);
        tvDescrRutina = findViewById(R.id.tvDescrRutina);

       // ArrayList<Integer> imagenes = new ArrayList<Integer>();
        ArrayList<String> ejercicios = new ArrayList<String>();
        ArrayList<String> numSeries = new ArrayList<String>();
        ArrayList<String> numRepes = new ArrayList<String>();


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

                tvNombreRutina.setText("Rutina de " + elem [0]);
                tvDescrRutina.setText(elem [1]);

                String ejercicio = elem [2];
                String numSerie = elem [3];
                String numRepe = elem [3];

                //imagenes.add(Integer.parseInt(logo));
                ejercicios.add(ejercicio);
                numSeries.add(numSerie);
                numRepes.add(numRepe);

            }
            fich.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //imagenes.stream().mapToInt(i -> i).toArray()
        ElAdaptadorRecycler eladaptador = new ElAdaptadorRecycler(ejercicios.toArray(new String[ejercicios.size()]),  ,numSeries.toArray(new String[numSeries.size()]),numRepes.toArray(new String[numRepes.size()]));
        lalista.setAdapter(eladaptador);


        /** definir el aspecto del RecyclerView --> horizontal, vertical, grid... **/
        LinearLayoutManager elLayoutLineal= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        lalista.setLayoutManager(elLayoutLineal);

    }
}