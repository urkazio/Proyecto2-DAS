package com.example.pruebamonigote;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        // acciones que se ejecutan tras activar la alarma broadcast de actualizacion del widget

        System.out.println("salta alarma widget ###############################################");

        // obtener las vistas remotas
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_rutinas_pred);

        // Obtener todos los nombres de las rutinas en el fichero "rutinaspred.txt" file
        String[] routineNames = WidgetRutinasPred.getRoutineNames(context);
        String widgetElements = WidgetRutinasPred.getWidgetElements(context, routineNames);

        // separar los elementos del widget
        String[] elem = widgetElements.split(",");
        String nombrerutina = elem [0];
        String descripcion = elem [1];
        String ejercicios = elem [2];

        // obtener fecha actual
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);

        // colocar los elementos el el layout remoto
        remoteViews.setTextViewText(R.id.title_text_view, nombrerutina);
        remoteViews.setTextViewText(R.id.description_text_view, descripcion);
        remoteViews.setTextViewText(R.id.exercises_text_view, ejercicios);
        remoteViews.setTextViewText(R.id.rutina_diaria_text_view, formattedDate);

        ComponentName tipowidget = new ComponentName(context, WidgetRutinasPred.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(tipowidget, remoteViews);
    }
}