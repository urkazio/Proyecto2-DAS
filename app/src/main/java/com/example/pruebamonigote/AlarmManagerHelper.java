package com.example.pruebamonigote;

import android.content.Context;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;

public class AlarmManagerHelper {

    public static void startAlarm(Context context) {

        //Esta clase es una gestora de alarmas cuyo objetivo es establecer la alarma, la accion asociada, el intervalo....

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // establecer que la alarma salte cada 2 dias encendiendo la pantalla del movil
        // cuando la alarma salte llamara al receptor de la alarma --> AlarmReceiver
        int dosDiasEnMilis = 2*24*60*60*1000;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP , SystemClock.elapsedRealtime(), dosDiasEnMilis, pendingIntent);
    }
}
