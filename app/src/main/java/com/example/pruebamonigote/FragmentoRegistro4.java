package com.example.pruebamonigote;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentoRegistro4 extends Fragment {
    EditText editPeso;
    EditText editAltura;
    public FragmentoRegistro4() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActividadRegistrarse.numeroFragmento=4;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragmento_registro4, container, false);
    }
    @Override
    public void onViewCreated(@Nullable View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);


        //RESTRICCIONES para el editText del peso
        editPeso = getView().findViewById(R.id.editPeso);
        editPeso.setInputType(InputType.TYPE_CLASS_NUMBER );
        editPeso.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) }); //limitar a 3 caracteres

        //RESTRICCIONES para el editText de la altura
        editAltura = getView().findViewById(R.id.editAltura);
        editAltura.setInputType(InputType.TYPE_CLASS_NUMBER);
        editAltura.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) }); //limitar a 3 caracteres

        Button b = getView().findViewById(R.id.btnAnndirRutina);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String User = getArguments().getString("User");
                String Contraseña = getArguments().getString("Contraseña");
                String Genero = getArguments().getString("Genero");
                int Edad = getArguments().getInt("Edad");


                if (!editPeso.getText().toString().equals("") && !editAltura.getText().toString().equals("")){

                    EditText editPeso = getView().findViewById(R.id.editPeso);
                    int Peso = Integer.parseInt(editPeso.getText().toString());

                    EditText editAltura = getView().findViewById(R.id.editAltura);
                    int Altura = Integer.parseInt(editPeso.getText().toString());

                    //se debe añadir toda la informacion recogida en la base de datos
                    miBD GestorDB = new miBD (getActivity(), "fitproBD", null, 1);
                    SQLiteDatabase bd = GestorDB.getWritableDatabase();
                    //bd.execSQL("delete from "+ "Usuarios");
                    ContentValues nuevo = new ContentValues();

                    nuevo.put("Usuario", User);
                    nuevo.put("Contraseña", Contraseña);
                    nuevo.put("Genero", Genero);
                    nuevo.put("Edad", Edad);
                    nuevo.put("Peso", Peso);
                    nuevo.put("Altura", Altura);
                    bd.insert("Usuarios", null, nuevo);


                    Intent intent = new Intent(getActivity(), ActividadPrincipal.class);
                    intent.putExtra("User",User);
                    startActivity(intent);


                    /**###################################################################
                     //###  Gestion de notificaciones (para cuando se crea una rutina)  ###
                     //#################################################################**/

                    //obtener el contexto de la actividad
                    Context context = getActivity();

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
                            .setContentTitle(getString(R.string.str107))
                            .setSubText(getString(R.string.str112))
                            .setContentText(getString(R.string.str84)+ " " +User+ "! "+getString(R.string.str85))
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

                }else {
                    Toast.makeText(getActivity(), "Hay campos vacios", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}