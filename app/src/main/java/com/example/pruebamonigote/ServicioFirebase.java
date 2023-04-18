package com.example.pruebamonigote;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class ServicioFirebase extends FirebaseMessagingService {

    public ServicioFirebase() {

    }

    // Recepción de mensajes
    //  * Si la aplicación está en primer plano --> No se muestra notificación (a no ser que la programemos nosotros)
    //  * Si la aplicación está en background --> Se muestra una notificación en la barra y NO se ejecuta el método onMessageReceived(...)
    public void onMessageReceived(RemoteMessage remoteMessage) {


        //si el mensaje viene con datos
        if (remoteMessage.getData().size() > 0) {

        }
        if (remoteMessage.getNotification() != null) {

            /**###################################################################
             //###  Gestion de notificaciones (para cuando se crea una rutina)  ###
             //#################################################################**/
            Context context = ActividadLoginRegistro.contextoEstatico;

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
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setSubText(remoteMessage.getNotification().getBody())
                    //.setContentText(context.getString(R.string.str82)+" "+context.getString(R.string.str83)+" " +nombreRut)
                    .setAutoCancel(true);


            //recuperacion de contexto (id de la notifi) para luego poder cerrarla
            Bundle extras = ((Activity) context).getIntent().getExtras();
            if (extras != null) {
                int id = extras.getInt("id");
                elManager.cancel(id);
            }

            //POR ULTIMO: lanzar la notificacion
            elManager.notify(1,builderNotifi.build());

            /** ##################### fin de gestion de notifiacion ###################**/

        }
    }
}
