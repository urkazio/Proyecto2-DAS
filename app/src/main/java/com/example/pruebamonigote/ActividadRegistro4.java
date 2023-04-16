package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActividadRegistro4 extends AppCompatActivity {

    private static boolean preferenciasCargadas = false;
    EditText editPeso;
    EditText editAltura;
    String user;
    String pass;
    String genero;
    int edad;
    Context context;
    public static ActividadRegistro4 actividadRegistro4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Context c = this;
        actividadRegistro4=this;
        if (!preferenciasCargadas){
            preferenciasCargadas=true;
            GestorIdiomas.cargarPreferencias(c,actividadRegistro4);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registro4);

        //inicializar el contexto
        context = this;

        //RESTRICCIONES para el editText del peso
        editPeso = findViewById(R.id.editPeso);
        editPeso.setInputType(InputType.TYPE_CLASS_NUMBER );
        editPeso.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) }); //limitar a 3 caracteres

        //RESTRICCIONES para el editText de la altura
        editAltura = findViewById(R.id.editAltura);
        editAltura.setInputType(InputType.TYPE_CLASS_NUMBER);
        editAltura.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) }); //limitar a 3 caracteres

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("User");
            pass = extras.getString("Contraseña");
            genero = extras.getString("Genero");
            edad = extras.getInt("Edad");
        }

        if (savedInstanceState != null) {
            String peso = savedInstanceState.getString("peso");
            String altura = savedInstanceState.getString("altura");
            editPeso.setText(peso);
            editAltura.setText(altura);
        }
    }

    public void finalizar(View v){

        if (!editPeso.getText().toString().equals("") && !editAltura.getText().toString().equals("")){

            editPeso = findViewById(R.id.editPeso);
            int peso = Integer.parseInt(editPeso.getText().toString());

            editAltura = findViewById(R.id.editAltura);
            int altura = Integer.parseInt(editPeso.getText().toString());

            String passEncriptada = EncriptadorContraseñas.encrypt(pass);

            String url = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/ugarcia053/WEB/insertuser.php?user="+user+"&pass="+passEncriptada+"&genero="+genero+"&edad="+edad+"&peso="+peso+"&altura="+altura+"&fotoperfil=default";
            TaskInsertarUsuario task = new TaskInsertarUsuario(url, user, context);
            task.execute();


            /**####################################################################
             //###  Gestion de notificaciones (para cuando se crea un usuario)  ###
             //#################################################################**/

            NotificationManager elManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builderNotifi = new NotificationCompat.Builder(context, "1");

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
                    .setContentTitle(getString(R.string.str107))
                    .setSubText(getString(R.string.str112))
                    .setContentText(getString(R.string.str84)+ " " +user+ "! "+getString(R.string.str85))
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


        }else {
            Toast.makeText(this, "Hay campos vacios", Toast.LENGTH_LONG).show();

        }

    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // guardar el idioma seleccionado a ya que a la hora de rotar sino se pondria
        // por defecto el idioma predetermionado y no el elegido por el usuario
        super.onSaveInstanceState(savedInstanceState);
        preferenciasCargadas=false;
        savedInstanceState.putString("peso", editPeso.getText().toString());
        savedInstanceState.putString("altura", editAltura.getText().toString());
    }

}