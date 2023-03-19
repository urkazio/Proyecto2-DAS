package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;

public class ActividadEjerciciosRutinasPersonal extends AppCompatActivity {

    TextView tvNombreRutina;
    TextView tvDescripcionRutina;
    String nombreRutina = "";
    String descripcionRutina = "";
    String nombrefichero = "";
    String user = "";
    private Context c = this;
    private Activity a = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_ejercicios_rutinas_personal);
        RecyclerView lalista= findViewById(R.id.elreciclerview);

        tvNombreRutina = findViewById(R.id.tvRutinasPers);
        tvDescripcionRutina = findViewById(R.id.tvDescrRutPers);

        ArrayList<Integer> imagenes = new ArrayList<Integer>();
        ArrayList<String> ejercicios = new ArrayList<String>();
        ArrayList<String> seriesRepes = new ArrayList<String>();


        /** se definen los elementos que van a ir dentro del RecyclerView
         *  dichos elementos serán ajustados en su item_layout **/

        //recoger los parametros pasados junto con el contexto (el nombre de la rutina)

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nombreRutina = extras.getString("nombreRutina");
            descripcionRutina = extras.getString("descripcionRutina");
            nombrefichero = extras.getString("nombrefichero");
            user = extras.getString("user");
        }

        tvNombreRutina.setText(tvNombreRutina.getText().toString()+" " + nombreRutina);
        tvDescripcionRutina.setText(descripcionRutina);


        try {
        BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(nombrefichero+".txt")));


        /** leer mientras haya lineas*/
        while(reader.ready()) {

            String line = reader.readLine();

           //el resto de lineas son de formato --> "NombreEjer,numSeries,numRepes,foto"

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
        //cerrar el fichero
        reader.close();

        } catch (IOException e) {
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

    public void annadirEjercicio (View v){
        Intent intent = new Intent(v.getContext(),ActividadAnnadirEjercicio.class);
        intent.putExtra("ficheroRutina",nombrefichero);
        intent.putExtra("nombreRutina",nombreRutina);
        intent.putExtra("descripcionRutina",descripcionRutina);
        v.getContext().startActivity(intent);
        finish();
    }

    public void eliminarRutina (View v){
        //no se elimina
        String copia="";
        System.out.println("fichero rutinaS usuario: "+user+".txt");
        System.out.println("fichero rutina usuario: "+nombrefichero+".txt");


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(user+".txt")));
            while(reader.ready()) {
                String line = reader.readLine();

                /** cada linea contiene el nombre y descripcion de la rutina separado por comas*/
                String[] elem = line.split(","); ///elem [0] --> logo && elem [1] --> nombre && elem [2] --> descr;

                String nombreRut = elem [1];
                System.out.println("nombreRutina:" +nombreRut);

                System.out.println(nombreRut+user);
                System.out.println(nombrefichero);


                if ((nombreRut).equals(nombrefichero)){

                }else{
                    copia += line+"\n";
                }

            }
            System.out.println(copia);

            try {
                deleteFile(user+".txt");
                OutputStreamWriter fichero = new OutputStreamWriter(openFileOutput(user+".txt", Context.MODE_PRIVATE));
                fichero.write(copia);
                fichero.close();
            } catch (IOException e){  }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        deleteFile(nombrefichero+user+".txt");
        Intent intent = new Intent(this,ActividadPrincipal.class);
        intent.putExtra("User",user);
        startActivity(intent);
        finish();
    }


    public void compartirRutinaSMS (View v){


        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        Context context = this;


        /**####################################################
         ######################   layout    ###################
         ####################################################**/
        // Layout propio para el dialogo
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        dialog.setTitle(getString(R.string.str114));
        dialog.setIcon(R.drawable.logo);
        dialog.setCancelable(false);


        /**####################################################
         ################   titulo y editable    ##############
         ####################################################**/
        // Añadir un TextView para indicar que se debe insertar el "Título"
        final TextView tvTelf = new TextView(context);
        tvTelf.setText(getString(R.string.str118));
        tvTelf.setPadding(100,80,100,20);
        layout.addView(tvTelf); // Se añade a la vista

        // Añadir un EditText para insertar el "Telefono"
        final EditText telfbox = new EditText(context);
        telfbox.setHint(R.string.str115);
        telfbox.setInputType(InputType.TYPE_CLASS_NUMBER);
        telfbox.setFilters(new InputFilter[] { new InputFilter.LengthFilter(9) }); //limitar a 9 caracteres
        telfbox.setPadding(100,20,100,40);
        layout.addView(telfbox); // Por ultimo se añade a la vista


        /**####################################################
         #############   botones y listeners    ###############
         ####################################################**/
        String telefono = "666666666";
        final String[] sms = {""};

        dialog.setPositiveButton(R.string.str78, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String telefono = telfbox.getText().toString();

                // evitar que el nombre de la rutina sea vacio
                if (telefono.equals("")) {

                    // si el nombre es vacio se indica por toast y se vuelve a abrir el dialogo
                    Toast.makeText(context, R.string.str19, Toast.LENGTH_LONG).show();
                    compartirRutinaSMS(v);

                }else{

                    try {

                        BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(nombrefichero+".txt")));

                        int linea = 0;
                        sms[0] += nombreRutina+"\n"+"\n";
                        sms[0] += descripcionRutina+"\n"+"\n";

                        /** leer mientras haya lineas*/
                        while(reader.ready()) {

                            String line = reader.readLine();

                            //el resto de lineas son de formato --> "NombreEjer,numSeries,numRepes,foto"

                            String[] elem = line.split(",");
                            String ejercicio = elem [0];
                            String numSerie = elem [1];
                            String numRepe = elem [2];
                            int numEjer = linea-1;
                            sms[0] += getString(R.string.str116)+numEjer+": "+ejercicio+ " --> "+getString(R.string.str71)+"+: "+numSerie+" & "+getString(R.string.str72)+": "+numRepe+"\n";

                            //actualizar el contador de linea para saber por donde vamos
                            linea++;
                        }
                        //cerrar el fichero
                        reader.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", telefono, null));
                    System.out.println(sms[0]);
                    System.out.println(sms[0].toString());
                    intent.putExtra("sms_body", sms[0].toString());
                    startActivity(intent);
                }
            }
        });


        dialog.setNegativeButton(R.string.str79, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.setView(layout); // Añadir el layout completo del dialogo al dialogo
        dialog.show(); // Por uultimo, mostrarlo.

    }
}
