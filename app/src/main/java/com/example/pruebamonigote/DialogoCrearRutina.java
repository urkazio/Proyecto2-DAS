package com.example.pruebamonigote;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.Manifest;
import android.widget.Toast;

public class DialogoCrearRutina {
    public void onCreateDialog(ActividadPrincipal actividadPrincipal, View v) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(actividadPrincipal);

        Context context = actividadPrincipal;

        /**####################################################
         ######################   layout    ###################
         ####################################################**/
        // Layout propio para el dialogo
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        dialog.setTitle("   Nueva Rutina Vacía");
        dialog.setIcon(R.drawable.logo);
        dialog.setCancelable(false);


        /**####################################################
         ################   titulo y editable    ##############
         ####################################################**/
        // Añadir un TextView para indicar que se debe insertar el "Título"
        final TextView tvNombre = new TextView(context);
        tvNombre.setText("Introduce el nombre de la rutina:");
        tvNombre.setPadding(100,80,100,20);
        layout.addView(tvNombre); // Se añade a la vista

        // Añadir un EditText para insertar el "Título"
        final EditText titleBox = new EditText(context);
        titleBox.setHint("Nombre");
        titleBox.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) }); //limitar a 12 caracteres
        titleBox.setPadding(100,20,100,40);
        //añadir restriccion para solo permitir caracteres a-z y A-Z
        titleBox.setFilters(new InputFilter[]{
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if (src.equals("")) {
                            return src;
                        }
                        if (src.toString().matches("[a-zA-Z ]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        });
        layout.addView(titleBox); // Por ultimo se añade a la vista


        /**####################################################
         #############   descripcion y editable    ############
         ####################################################**/
        // Añadir otro TextView para indicar que se debe insertar el "Descripción"
        final TextView tvDescr = new TextView(context);
        tvDescr.setText("Añade una breve descripción a la rutina (máx 50 caracteres):");
        tvDescr.setPadding(100,80,100,20);
        layout.addView(tvDescr); // Se añade a la vista

        // Añadir otro EditText para la insertar el "Descripción"
        final EditText descriptionBox = new EditText(context);
        descriptionBox.setFilters(new InputFilter[] { new InputFilter.LengthFilter(50) }); //limitar a 50 caracteres
        descriptionBox.setHint("Descripción");
        descriptionBox.setPadding(100,20,100,40);
        //añadir restriccion para solo permitir caracteres a-z y A-Z
        descriptionBox.setFilters(new InputFilter[]{
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if (src.equals("")) {
                            return src;
                        }
                        if (src.toString().matches("[a-zA-Z ]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        });
        layout.addView(descriptionBox); // Por ultimo se añade a la vista


        /**####################################################
         #############   botones y listeners    ###############
         ####################################################**/
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombreRut = titleBox.getText().toString();
                String descripcionRut = descriptionBox.getText().toString();

                // evitar que el nombre de la rutina sea vacio
                if (nombreRut.equals("") || descripcionRut.equals("")){
                    // si el nombre es vacio se indica por toast y se vuelve a abrir el dialogo
                    Toast.makeText(context, "No puede haber campos vacios", Toast.LENGTH_LONG).show();
                    onCreateDialog(actividadPrincipal,  v);

                }else{
                    // si el nombre es correcto se sigue el proceso normal: la rutina a crear estará vacia y no contiene ejercicios
                    // escribir en el fichero "misrutinas"+user+".txt" la nueva rutina creada por el usuario pero antes de debe comprobar que no exista una con dicho nomrbre
                    // NOTA: los ficheros creados por el usuario son ficheros EXTERNOS en la memoria INTERNA

                    try {

                        // obtener el nombre de usuario mediante una consulta a la base de datos para saber de quien son las rutinas personalziadas (ya que la app puede ser multiuser)
                        String user ="urko";

                        // buscamos a ver si existe alguna rutina con dicho nombre en el fichero "misrutinas"+user+".txt"
                        boolean existe = false;

                        try {

                            // ---ESTE BLOQUE DE CODIGO ES PARA COMPROBAR SI YA EXISTE UNA RUTINA CON NOMBRE SEMEJANTE AL INTRODUCIDO EN EL DIALGO QUE HAYA SIDO CREADA POR EL USUARIO ---:

                            BufferedReader reader = new BufferedReader(new InputStreamReader(context.openFileInput("misrutinas"+user+".txt")));
                            while(reader.ready() && !existe) {

                                //obtener cada linea del fichero misrutinas del usuario y quedarnos con el elemento que hace referencia al nombre
                                String linea = reader.readLine().replaceAll("\\s+",""). replaceAll("-", "").toLowerCase();
                                String[] elem = linea.split(","); /// elem [0] --> logo && elem [1] --> nombre && elem [2] --> descr;
                                String nombre = elem [1];


                                // obtener el nombre de la rutina en  minusculas y sin espacios ya que asi se lama el txt que contiene los ejercicios
                                String nombreRutina = nombreRut.replaceAll("\\s+",""). replaceAll("-", "").toLowerCase();


                                if (nombreRutina.equals(nombre)) {
                                    existe = true;
                                }
                            }
                                reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (existe){
                            // si la rutina existe lo indicamos por toast y volvemos a lanzar el dialogo
                            Toast.makeText(context, "Ya eixte una rutina con dicho nombre", Toast.LENGTH_LONG).show();
                            onCreateDialog(actividadPrincipal,  v);

                        }else{
                            // si no existe una rutina con ese nombre entonces se ejecuta el proceso de creacion
                            // crear el fichero (vacio) donde se almacenaran los ejercicios de la nueva rutina del usuario
                            String nombreRutina = nombreRut.replaceAll("\\s+",""). replaceAll("-", "").toLowerCase();
                            OutputStreamWriter rutinaNueva = new OutputStreamWriter(context.openFileOutput(nombreRutina+".txt", Context.MODE_PRIVATE));

                            // referenciar en el fichero que agrupa todas las rutinas del usuario, la nueva que se acaba de crear
                            // el id de la rutina será el conjunto del nombre del user y el num lineas del fichero misRutinas --> “rutina”+user+nºrutina
                            OutputStreamWriter fichero = new OutputStreamWriter(context.openFileOutput("misrutinas"+user+".txt", Context.MODE_APPEND));

                            // se debe gardar --> logo(nombre en @drawable), Nombre Rutina(tal cual se escribió), Descripcion Rutina(tal cual se escribió)
                            fichero.write("logo,"+nombreRut+","+descriptionBox.getText().toString()+"\n");
                            fichero.close();


                            /**###################################################################
                             //###  Gestion de notificaciones (para cuando se crea una rutina)  ###
                             //#################################################################**/

                            NotificationManager elManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                            NotificationCompat.Builder builderNotifi = new NotificationCompat.Builder(context, "1");

                            //definicion para el canal
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationChannel elCanal = new NotificationChannel("1", "haGanado", NotificationManager.IMPORTANCE_DEFAULT);
                                //configuracion del canal
                                elCanal.setDescription("Descripción del canal");
                                elCanal.enableLights(true);
                                elCanal.setLightColor(Color.RED);
                                elManager.createNotificationChannel(elCanal);
                            }

                            //Definicion de la alerta personalizada al ganar
                            builderNotifi.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.logo))
                                    .setSmallIcon(R.drawable.logo)
                                    .setContentTitle("Notificacion de Fit Pro")
                                    .setSubText("Nueva rutina vacía")
                                    .setContentText("Enhorabuena! Has creado la nueva rutina " +nombreRut+ " con exito!!!")
                                    .setAutoCancel(true);


                            //recuperacion de contexto (id de la notifi) para luego poder cerrarla
                            Bundle extras = ((Activity) context).getIntent().getExtras();
                            if (extras != null) {
                                int id = extras.getInt("id");
                                elManager.cancel(id);
                            }

                            //POR ULTIMO: lanzar la notificacion
                            elManager.notify(1,builderNotifi.build());

                            /** ##################### fin de gestion de notifiacion #########################**/
                        }

                    } catch (IOException e){
                        System.out.println("Expecepcion");
                    }
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
