package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class ActividadRegistrarse4 extends AppCompatActivity {


    EditText editPeso;
    EditText editAltura;
    public static ActividadRegistrarse4 actividadRegistrarse4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registrarse4);
        actividadRegistrarse4=this;

        //RESTRICCIONES para el editText del peso
        editPeso = findViewById(R.id.editPeso);
        editPeso.setInputType(InputType.TYPE_CLASS_NUMBER );
        editPeso.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) }); //limitar a 3 caracteres

        //RESTRICCIONES para el editText de la altura
        editAltura = findViewById(R.id.editAltura);
        editAltura.setInputType(InputType.TYPE_CLASS_NUMBER);
        editAltura.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) }); //limitar a 3 caracteres

    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // guardar el idioma seleccionado a ya que a la hora de rotar sino se pondria
        // por defecto el idioma predetermionado y no el elegido por el usuario
        super.onSaveInstanceState(savedInstanceState);
        String idioma = getResources().getConfiguration().getLocales().get(0).toString();
        savedInstanceState.putString("idioma", idioma);
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        // recuperar el idioma guardado antes de destruir la actividad y aplicarlo
        super.onRestoreInstanceState(savedInstanceState);
        String idioma = savedInstanceState.getString("idioma");

        Locale nuevaloc = new Locale(idioma);
        Locale.setDefault(nuevaloc);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(nuevaloc);
        configuration.setLayoutDirection(nuevaloc);

        //actualizar la configuración de todos los recursos de la aplicación mediante el método updateConfiguration
        Context context = getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());

        // recargar de nuevo la actividad para que tambien tenga efecto en la actividad actual
        finish();
        startActivity(getIntent());
    }

    public void finalizar(View v) {

        String User="";
        String Pass="";
        String Genero="";
        int Edad=-1;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            User = extras.getString("User");
            Pass  = extras.getString("Contraseña");
            Genero = extras.getString("Genero");
            Edad = extras.getInt("Edad");
        }

        if (!editPeso.getText().toString().equals("") && !editAltura.getText().toString().equals("")){

            EditText editPeso = findViewById(R.id.editPeso);
            int Peso = Integer.parseInt(editPeso.getText().toString());

            EditText editAltura = findViewById(R.id.editAltura);
            int Altura = Integer.parseInt(editPeso.getText().toString());

            //se debe añadir toda la informacion recogida en la base de datos
            miBD GestorDB = new miBD (this, "fitproBD", null, 1);
            SQLiteDatabase bd = GestorDB.getWritableDatabase();
            ContentValues nuevo = new ContentValues();

            nuevo.put("Usuario", User);
            nuevo.put("Contraseña", Pass);
            nuevo.put("Genero", Genero);
            nuevo.put("Edad", Edad);
            nuevo.put("Peso", Peso);
            nuevo.put("Altura", Altura);
            bd.insert("Usuarios", null, nuevo);

            Intent intent = new Intent(this, ActividadPrincipal.class);
            intent.putExtra("User",User);
            startActivity(intent);

            /**###################################################################
             //###  Gestion de notificaciones (para cuando se crea una rutina)  ###
             //#################################################################**/

            Context context = this;

            NotificationManager elManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builderNotifi = new NotificationCompat.Builder(context, "2");

            //definicion para el canal
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel elCanal = new NotificationChannel("2", "haGanado", NotificationManager.IMPORTANCE_DEFAULT);
                //configuracion del canal
                elCanal.setDescription("Descripción del canal");
                elCanal.enableLights(true);
                elCanal.setLightColor(Color.RED);
                elManager.createNotificationChannel(elCanal);
            }

            //Definicion de la alerta personalizada al ganar
            builderNotifi.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.logo))
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(""+R.string.str107)
                    .setSubText(""+R.string.str108)
                    .setContentText(R.string.str84+ " " +User+ "! "+R.string.str85)
                    .setAutoCancel(true);


            //recuperacion de contexto (id de la notifi) para luego poder cerrarla
            Bundle extras2 = ((Activity) context).getIntent().getExtras();
            if (extras2 != null) {
                int id = extras2.getInt("id");
                elManager.cancel(id);
            }

            //POR ULTIMO: lanzar la notificacion
            elManager.notify(2,builderNotifi.build());

            /** ##################### fin de gestion de notifiacion #########################**/

            bd.close();

            //cerrar todas las actividades relacionadas con crear cuenta una vez creada la cuenta
            ActividadRegistrarse1.actividadRegistrarse1.finish();
            ActividadRegistrarse2.actividadRegistrarse2.finish();
            ActividadRegistrarse3.actividadRegistrarse3.finish();
            ActividadRegistrarse4.actividadRegistrarse4.finish();


        }else {
            Toast.makeText(this, "Hay campos vacios", Toast.LENGTH_LONG).show();

        }


    }
}