package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.EditText;

import java.util.Locale;

public class ActividadRegistrarse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registrarse);
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // --> guardar el idioma seleccionado a ya que a la hora de rotar sino se pondria
        //     por defecto el idioma predetermionado y no el elegido por el usuario
        // --> tambien guardar todos los editexte pq al rotar se pierden
        super.onSaveInstanceState(savedInstanceState);

        //obtener los valores a guardar
        String idioma = getResources().getConfiguration().getLocales().get(0).toString();
        EditText edUser = findViewById(R.id.editUser);
        EditText edPass1 = findViewById(R.id.editPass);
        EditText edPass2 = findViewById(R.id.editPass2);
        EditText edEdad = findViewById(R.id.editEdad);
        EditText edPeso = findViewById(R.id.editPeso);
        EditText edAltura = findViewById(R.id.editAltura);

        //guardar los valores obtenidos
        savedInstanceState.putString("idioma", idioma);
        savedInstanceState.putString("user",edUser.getText().toString());
        savedInstanceState.putString("pass",edPass1.getText().toString());
        savedInstanceState.putString("pass2",edPass2.getText().toString());
        savedInstanceState.putString("edad",edEdad.getText().toString());
        savedInstanceState.putString("peso",edPeso.getText().toString());
        savedInstanceState.putString("altura",edAltura.getText().toString());
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        // recuperar los valores guardados antes de destruir la actividad y aplicarlos
        super.onRestoreInstanceState(savedInstanceState);

        String idioma = savedInstanceState.getString("idioma");
        String user = savedInstanceState.getString("user");
        String pass = savedInstanceState.getString("pass");
        String pass2 = savedInstanceState.getString("pass2");
        String edad = savedInstanceState.getString("edad");
        String peso = savedInstanceState.getString("peso");
        String altura = savedInstanceState.getString("altura");

        //voler a establecer los valores guardados en los editext correspondientes
        EditText edUser = findViewById(R.id.editUser);
        edUser.setText(user);
        EditText edPass1 = findViewById(R.id.editPass);
        edPass1.setText(pass);
        EditText edPass2 = findViewById(R.id.editPass2);
        edPass2.setText(pass2);
        EditText edEdad = findViewById(R.id.editEdad);
        edEdad.setText(edad);
        EditText edPeso = findViewById(R.id.editPeso);
        edPeso.setText(peso);
        EditText edAltura = findViewById(R.id.editAltura);
        edAltura.setText(altura);

        //aplicar de nuevo el idioma guardado antes de destruir
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
}