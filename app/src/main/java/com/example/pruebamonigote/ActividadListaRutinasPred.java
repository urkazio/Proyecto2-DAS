package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

public class ActividadListaRutinasPred extends AppCompatActivity {

    private Context c = this;
    private Activity a = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_lista_rutinas_pred);
        RecyclerView lalista= findViewById(R.id.elreciclerview);

        ArrayList<Integer> imagenes = new ArrayList<Integer>();
        ArrayList<String> nombres = new ArrayList<String>();
        ArrayList<String> decripciones = new ArrayList<String>();


        /** se definen los elementos que van a ir dentro del RecyclerView
         *  dichos elementos serán ajustados en su item_layout **/

        String idioma = getResources().getConfiguration().getLocales().get(0).toString();
        InputStream fich = null;

        if (idioma.contains("en")){ //si estamos en ingles se muestran las rutinas prediseñadas en ingles
            fich = getResources().openRawResource(R.raw.rutinaspreden);
        }else if (idioma.contains("es")){ // en caso contrario, en castellano
            System.out.println("idioma actual: "+ idioma);
            fich = getResources().openRawResource(R.raw.rutinaspred);
        }

        BufferedReader reader = new BufferedReader( new InputStreamReader(fich));
        try {
            /** leer mientras haya lineas*/
            while(reader.ready()) {
                String line = reader.readLine();
                /** cada linea contiene el nombre y descripcion de la rutina separado por comas*/
                String[] elem = line.split(","); ///elem [0] --> logo && elem [1] --> nombre && elem [2] --> descr;

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
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        ElAdaptadorRecycler eladaptador = new ElAdaptadorRecycler(nombres.toArray(new String[nombres.size()]), imagenes.stream().mapToInt(i -> i).toArray() , decripciones.toArray(new String[decripciones.size()]),"");
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