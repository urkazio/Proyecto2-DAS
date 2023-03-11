package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class ActividadRegistrarse2 extends AppCompatActivity {

    EditText editPass;
    EditText editPass2;
    EditText editUser;
    public static ActividadRegistrarse2 actividadRegistrarse2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registrarse2);
        actividadRegistrarse2=this;

        //RESTRICCIONES para el editText de la contraseña
        editPass = findViewById(R.id.editPass);
        editPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editPass2 = findViewById(R.id.editPass2);
        editPass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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

    public void continuar(View v) {

        editUser = findViewById(R.id.editUser);
        String user=editUser.getText().toString();
        String pass1=editPass.getText().toString();
        String pass2=editPass2.getText().toString();

        // comprobar que contraseñas no vacias e iguales
        if (pass1.equals(pass2) && !pass1.isEmpty() && !pass2.isEmpty() && !user.isEmpty() ){

            EditText edituser = findViewById(R.id.editUser);
            String User = edituser.getText().toString();

            // se debe comprobar que no exista un usuario con dicho nombre en la bbdd
            // para ello hago un select cualquiera con clausula WHERE metiendo el usuario introducido en el editText
            miBD GestorDB = new miBD (this, "fitproBD", null, 1);
            SQLiteDatabase bd = GestorDB.getWritableDatabase();
            String[] campos = new String[] {"Altura"};
            String[] argumentos = new String[] {User};
            Cursor c = bd.query("Usuarios",campos,"Usuario=?",argumentos,null,null,null);

            if (c.moveToFirst()){

                Toast.makeText(this, "Ese nombre de usuario ya ha sido usado", Toast.LENGTH_LONG).show();

            }else{
                Intent intent = new Intent(this, ActividadRegistrarse3.class);
                intent.putExtra("User",User);
                intent.putExtra("Contraseña",pass1);
                startActivity(intent);
            }
            c.close();
            bd.close();


        }else{
            Toast.makeText(this, "Hay campos vacios o las contraseñas no coinciden", Toast.LENGTH_LONG).show();
        }

    }
}