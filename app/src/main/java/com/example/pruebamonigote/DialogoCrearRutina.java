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

import java.io.FileOutputStream;
import java.io.IOException;
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
                System.out.println(nombreRut);
                String descripcionRut = titleBox.getText().toString();

                // evitar que el nombre de la rutina sea vacio
                if (nombreRut.equals("")){
                    // si el nombre es vacio se indica por toast y se vuelve a abrir el dialogo
                    Toast.makeText(context, "El nombre de la rutina no puede ser vacío", Toast.LENGTH_LONG).show();
                    onCreateDialog(actividadPrincipal,  v);

                }else{
                    // si el nombre es correcto se sigue el proceso normal:
                    // escribir en el fichero "misrutinas.txt" la nueva rutina creada por el usuario
                    // la rutina no esta vacia y no contiene ejercicios
                    try {
                        //el id de la rutina será el num lineas del fichero misRutinas
                        OutputStreamWriter fichero = new OutputStreamWriter(context.openFileOutput("misrutinas.txt", Context.MODE_PRIVATE));
                        fichero.write("Estoy escribiendo en el fichero");
                        System.out.println(nombreRut);
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
