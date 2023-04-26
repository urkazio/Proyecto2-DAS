package com.example.pruebamonigote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlarmBroadcastReceiverNotifi extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // cuando salta la alarma se ejecuta este metodo que "la recibe" y ejecuta el codigo pertinente
        System.out.println("salta alarma notificacion ###############################################");

        // este código sirve para llamar a la clase asincrona que ejecuta el archivo PHP cuando la alarma es activada
        String url = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/ugarcia053/WEB/notificacion.php";
        TaskNotificacion task = new TaskNotificacion(url);
        task.execute();

    }
}