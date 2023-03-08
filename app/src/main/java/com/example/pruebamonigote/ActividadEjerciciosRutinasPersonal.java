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

public class ActividadEjerciciosRutinasPersonal extends AppCompatActivity {

    TextView tvNombreRutina;
    //TextView tvDescripcionRutina;
    String nombreRutina = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_ejercicios_rutinas_personal);
        RecyclerView lalista= findViewById(R.id.elreciclerview);

        tvNombreRutina = findViewById(R.id.tvRutinasPers);
        //tvDescripcionRutina = findViewById(R.id.tvDescrRut);

        ArrayList<Integer> imagenes = new ArrayList<Integer>();
        ArrayList<String> ejercicios = new ArrayList<String>();
        ArrayList<String> seriesRepes = new ArrayList<String>();


        /** se definen los elementos que van a ir dentro del RecyclerView
         *  dichos elementos serán ajustados en su item_layout **/

        //recoger los parametros pasados junto con el contexto (el nombre de la rutina)

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
                    tvNombreRutina.setText("Rutina de " + line);

                }else if(linea==1){ // descripcion de la rutina
                    //tvDescripcionRutina.setText(line);

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


        ElAdaptadorRecycler eladaptador = new ElAdaptadorRecycler(ejercicios.toArray(new String[ejercicios.size()]), imagenes.stream().mapToInt(i -> i).toArray() ,seriesRepes.toArray(new String[seriesRepes.size()]));
        lalista.setAdapter(eladaptador);


        /** definir el aspecto del RecyclerView --> horizontal, vertical, grid... **/
        LinearLayoutManager elLayoutLineal= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        lalista.setLayoutManager(elLayoutLineal);

    }
    public void annadirEjercicio (View v){
        Intent intent = new Intent(v.getContext(),ActividadAnnadirEjercicio.class);
        v.getContext().startActivity(intent);
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

        dialog.setTitle("   Añadir número del destinatario");
        dialog.setIcon(R.drawable.logo);
        dialog.setCancelable(false);


        /**####################################################
         ################   titulo y editable    ##############
         ####################################################**/
        // Añadir un TextView para indicar que se debe insertar el "Título"
        final TextView tvTelf = new TextView(context);
        tvTelf.setText("Introduce el número de teléfono del destinatario:");
        tvTelf.setPadding(100,80,100,20);
        layout.addView(tvTelf); // Se añade a la vista

        // Añadir un EditText para insertar el "Telefono"
        final EditText telfbox = new EditText(context);
        telfbox.setHint("Numero de Teléfono");
        telfbox.setInputType(InputType.TYPE_CLASS_NUMBER);
        telfbox.setFilters(new InputFilter[] { new InputFilter.LengthFilter(11) }); //limitar a 11 caracteres
        telfbox.setPadding(100,20,100,40);
        layout.addView(telfbox); // Por ultimo se añade a la vista


        /**####################################################
         #############   botones y listeners    ###############
         ####################################################**/
        String telefono = "666666666";
        final String[] sms = {""};

        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String telefono = telfbox.getText().toString();

                // evitar que el nombre de la rutina sea vacio
                if (telefono.equals("")) {

                    // si el nombre es vacio se indica por toast y se vuelve a abrir el dialogo
                    Toast.makeText(context, "El campo no puede estar vacio", Toast.LENGTH_LONG).show();
                    compartirRutinaSMS(v);

                }else{

                    //cambiar el como se recoge pq esto deberia ser un fichero experno
                    int idRutina = getResources().getIdentifier(nombreRutina, "raw", getPackageName());
                    InputStream fich = getResources().openRawResource(idRutina);
                    BufferedReader reader = new BufferedReader( new InputStreamReader(fich));
                    int linea = 0;

                    try {
                        /** leer mientras haya lineas*/
                        while(reader.ready()) {

                            String line = reader.readLine();

                            if (linea==0){ //nombre de la rutina
                                sms[0] += line.toString()+"\n"+"\n";

                            }else if(linea==1){ // descripcion de la rutina
                                sms[0] += line+"\n"+"\n";

                            }else{ //el resto de lineas son de formato --> "NombreEjer,numSeries,numRepes,foto"

                                String[] elem = line.split(",");
                                String ejercicio = elem [0];
                                String numSerie = elem [1];
                                String numRepe = elem [2];
                                int numEjer = linea-1;
                                sms[0] += "Ejercicio"+numEjer+": "+ejercicio+ " --> nºSeries: "+numSerie+" & nºRepes: "+numRepe+"\n";
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


                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", telefono, null));
                    System.out.println(sms[0]);
                    System.out.println(sms[0].toString());
                    intent.putExtra("sms_body", sms[0].toString());
                    startActivity(intent);
                }
            }
        });


        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.setView(layout); // Añadir el layout completo del dialogo al dialogo
        dialog.show(); // Por uultimo, mostrarlo.



    }
}
