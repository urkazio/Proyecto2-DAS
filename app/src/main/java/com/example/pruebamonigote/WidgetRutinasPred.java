package com.example.pruebamonigote;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class WidgetRutinasPred extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            // Es recomendable generar un método que realice la actualización de una instancia concreta
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Obtener todos los nombres de las rutinas en el fichero "rutinaspred.txt" file
        String[] routineNames = getRoutineNames(context);
        String widgetElements = getWidgetElements(context, routineNames);

        String[] elem = widgetElements.split(",");
        String nombrerutina = elem [0];
        String descripcion = elem [1];
        String ejercicios = elem [2];

        // Crear un intent para abrir la actividad ActividadListaRutinasPred donde se listan todas las rutinas
        Intent intent = new Intent(context, ActividadListaRutinasPred.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Actualizar el widget con los campos modificados
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_rutinas_pred);
        views.setTextViewText(R.id.title_text_view, nombrerutina);
        views.setTextViewText(R.id.description_text_view, descripcion);
        views.setTextViewText(R.id.exercises_text_view, ejercicios);

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);
        views.setTextViewText(R.id.rutina_diaria_text_view, formattedDate);
        views.setOnClickPendingIntent(R.id.layout_widget, pendingIntent); // Set the PendingIntent to the widget layout

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    public static String getWidgetElements(Context context, String[] routineNames){

        // Coger una rutina aleatoria del conjunto de rutinas prediseñadas
        Random random = new Random();
        String routineName = routineNames[random.nextInt(routineNames.length)];

        String[] parts = routineName.split(",");
        String nombrerutina = parts[1]; //
        String descripcion = parts[2]; //


        String ficheroRutina = nombrerutina.replaceAll("\\s+",""). replaceAll("-", "").toLowerCase();
        int idRutina = context.getResources().getIdentifier(ficheroRutina, "raw", context.getPackageName());
        InputStream fich = context.getResources().openRawResource(idRutina);
        BufferedReader reader = new BufferedReader( new InputStreamReader(fich));
        int linea = 0;
        String ejercicios = "";

        try {
            //leer mientras haya lineas
            while(reader.ready()) {

                String line = reader.readLine();

                if (linea>1){ //el resto de lineas son de formato --> "NombreEjer,numSeries,numRepes,foto"

                    String[] elem = line.split(",");
                    String ejercicio = elem [0];
                    String numSerie = elem [1];
                    String numRepe = elem [2];
                    int numeroEjer = linea-1;

                    ejercicios = ejercicios + Integer.toString(numeroEjer)+") "+ ejercicio+" || "+numSerie+ " x " +numRepe+"\n";

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

        return nombrerutina+","+descripcion+","+ejercicios;
    }

    public static String[] getRoutineNames(Context context) {

        // a partir del contexto de la primer actividad, obtener el idioma en el que se encuentra la aplicacion a la hora de añadir un widget
        String idioma = ActividadLoginRegistro.contextoEstatico.getResources().getConfiguration().getLocales().get(0).toString();

        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = null;

        if (idioma.contains("en")){ //si estamos en ingles se muestran las rutinas prediseñadas en ingles
            inputStream = context.getResources().openRawResource(R.raw.rutinaspreden);
        }else if (idioma.contains("es")){ // en caso contrario, en castellano
            inputStream = context.getResources().openRawResource(R.raw.rutinaspred);
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString().split("\n");
    }

    @Override
    public void onEnabled(Context context) {

        /** Al crear el widget por rimera vez de debe añadir una alarma para que se actualice
         *  la rutina cada dia a las 00:00:00 de tal manera que cada dia se tenga una rutina diferente
         */

        // En primer lugar se debe crear una alarma que mande un mensaje broadcast con el obetivo de actualizar la rutina
        // La clase AlarmManagerBroadcastReceiver gestiona el evento al saltar la alarma
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 7475, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Obtener un objeto Calendar y establecer la hora, minutos y segundos a 00:00:00
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // Obtener la zona horaria de España y establecerla en el objeto Calendar para que salte a las 00:00:00 Española
        TimeZone timeZone = TimeZone.getTimeZone("Europe/Madrid");
        calendar.setTimeZone(timeZone);

        long triggerAtMillis = calendar.getTimeInMillis();
        long intervalMillis = AlarmManager.INTERVAL_DAY;

        //Finalmente, establecer la alarma diaria en el AlarmManager utilizando el método setRepeating()
        am.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, pi);

    }

}
