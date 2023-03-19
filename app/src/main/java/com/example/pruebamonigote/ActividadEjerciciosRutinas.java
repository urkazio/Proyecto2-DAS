package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ActividadEjerciciosRutinas extends AppCompatActivity {

    TextView tvNombreRutina;
    TextView tvDescripcionRutina;
    private Context c = this;
    private Activity a = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_ejercicios_rutinas);
        RecyclerView lalista= findViewById(R.id.elreciclerview);

        tvNombreRutina = findViewById(R.id.tvRutinasPers);
        tvDescripcionRutina = findViewById(R.id.tvDescrRutPers);

        ArrayList<Integer> imagenes = new ArrayList<Integer>();
        ArrayList<String> ejercicios = new ArrayList<String>();
        ArrayList<String> seriesRepes = new ArrayList<String>();


        /** se definen los elementos que van a ir dentro del RecyclerView
         *  dichos elementos serán ajustados en su item_layout **/

        //recoger los parametros pasados junto con el contexto (el nombre de la rutina)
        String nombreRutina = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nombreRutina = extras.getString("nombreRutina");
        }

        int idRutina = getResources().getIdentifier(nombreRutina, "raw", getPackageName());
        InputStream fich = getResources().openRawResource(idRutina);
        BufferedReader reader = new BufferedReader( new InputStreamReader(fich));
        int linea = 0;

        try {
            /** leer mientras haya lineas*/
            while(reader.ready()) {

                String line = reader.readLine();

                if (linea==0){ //nombre de la rutina
                    tvNombreRutina.setText(tvNombreRutina.getText().toString()+" " + line);

                }else if(linea==1){ // descripcion de la rutina
                    tvDescripcionRutina.setText(line);

                }else{ //el resto de lineas son de formato --> "NombreEjer,numSeries,numRepes,foto"

                    String[] elem = line.split(",");
                    String ejercicio = elem [0];
                    String numSerie = elem [1];
                    String numRepe = elem [2];
                    String imagen = elem [3];

                    int id = getResources().getIdentifier(imagen, "drawable", getPackageName());

                    imagenes.add(id);
                    ejercicios.add(ejercicio);
                    seriesRepes.add("nºSeries x nºRepes:\n"+numSerie+ " x " +numRepe);
                }
                //actualizar el contador de linea para saber por donde vamos
                linea++;
            }
            //cerrar el fichero
            fich.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        ElAdaptadorRecycler eladaptador = new ElAdaptadorRecycler(ejercicios.toArray(new String[ejercicios.size()]), imagenes.stream().mapToInt(i -> i).toArray() ,seriesRepes.toArray(new String[seriesRepes.size()]),"");
        lalista.setAdapter(eladaptador);


        /** definir el aspecto del RecyclerView --> horizontal, vertical, grid... **/
        LinearLayoutManager elLayoutLineal= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        lalista.setLayoutManager(elLayoutLineal);

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
}