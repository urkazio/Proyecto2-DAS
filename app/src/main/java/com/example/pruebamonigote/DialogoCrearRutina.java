package com.example.pruebamonigote;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.widget.Toast;

public class DialogoCrearRutina {
    public void onCreateDialog(ActividadPrincipal actividadPrincipal, String user) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(actividadPrincipal);

        Context context = actividadPrincipal;

        /**####################################################
         ######################   layout    ###################
         ####################################################**/
        // Layout propio para el dialogo
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        dialog.setTitle(R.string.str73);
        dialog.setIcon(R.drawable.logo);
        dialog.setCancelable(false);


        /**####################################################
         ################   titulo y editable    ##############
         ####################################################**/
        // Añadir un TextView para indicar que se debe insertar el "Título"
        final TextView tvNombre = new TextView(context);
        tvNombre.setText(R.string.str74);
        tvNombre.setPadding(100,80,100,20);
        layout.addView(tvNombre); // Se añade a la vista

        // Añadir un EditText para insertar el "Título"
        final EditText titleBox = new EditText(context);
        titleBox.setHint(R.string.str76);
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
        tvDescr.setText(R.string.str75);
        tvDescr.setPadding(100,80,100,20);
        layout.addView(tvDescr); // Se añade a la vista

        // Añadir otro EditText para la insertar el "Descripción"
        final EditText descriptionBox = new EditText(context);
        descriptionBox.setFilters(new InputFilter[] { new InputFilter.LengthFilter(50) }); //limitar a 50 caracteres
        descriptionBox.setHint(R.string.str77);
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
        dialog.setPositiveButton(R.string.str78, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombreRut = titleBox.getText().toString();
                String descripcionRut = descriptionBox.getText().toString();

                // evitar que el nombre de la rutina sea vacio
                if (nombreRut.equals("") || descripcionRut.equals("") || nombreRut.equals(user)){
                    // si el nombre es vacio se indica por toast y se vuelve a abrir el dialogo
                    Toast.makeText(context, R.string.str19, Toast.LENGTH_LONG).show();
                    onCreateDialog(actividadPrincipal, user);


                }else{
                    // si el nombre es correcto se sigue el proceso normal: la rutina a crear estará vacia y no contiene ejercicios
                    // escribir en el fichero "misrutinas"+user+".txt" la nueva rutina creada por el usuario pero antes de debe comprobar que no exista una con dicho nomrbre
                    // NOTA: los ficheros creados por el usuario son ficheros EXTERNOS en la memoria INTERNA

                    try {


                        // buscamos a ver si existe alguna rutina con dicho nombre en el fichero "user+".txt"
                        boolean existe = false;

                        try {

                            // ---ESTE BLOQUE DE CODIGO ES PARA COMPROBAR SI YA EXISTE UNA RUTINA CON NOMBRE SEMEJANTE AL INTRODUCIDO EN EL DIALGO QUE HAYA SIDO CREADA POR EL USUARIO ---:

                            BufferedReader reader = new BufferedReader(new InputStreamReader(context.openFileInput(user+".txt")));
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
                            Toast.makeText(context, R.string.str106, Toast.LENGTH_LONG).show();
                            onCreateDialog(actividadPrincipal,  user);

                        }else{
                            // si no existe una rutina con ese nombre entonces se ejecuta el proceso de creacion
                            // crear el fichero (vacio) donde se almacenaran los ejercicios de la nueva rutina del usuario con nombre "nombreRutina+user+".txt""
                            String nombreRutina = nombreRut.replaceAll("\\s+",""). replaceAll("-", "").toLowerCase();
                            OutputStreamWriter rutinaNueva = new OutputStreamWriter(context.openFileOutput(nombreRutina+user+".txt", Context.MODE_PRIVATE));

                            // referenciar en el fichero que agrupa todas las rutinas del usuario, la nueva que se acaba de crear
                            OutputStreamWriter fichero = new OutputStreamWriter(context.openFileOutput(user+".txt", Context.MODE_APPEND));

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
                                    .setContentTitle(context.getString(R.string.str107))
                                    .setSubText(context.getString(R.string.str113))
                                    .setContentText(context.getString(R.string.str82)+" "+context.getString(R.string.str83)+" " +nombreRut)
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


        dialog.setNegativeButton(R.string.str79, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActividadPrincipal.dialogo=false;
                dialog.cancel();
            }
        });

        dialog.setView(layout); // Añadir el layout completo del dialogo al dialogo
        dialog.show();
    }
}
