package com.example.pruebamonigote;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Locale;

public class ActividadRegistrarse extends AppCompatActivity {

    private Context c = this;
    private Activity a = this;

    public static ActividadRegistrarse actividadregistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = this.getWindow().getDecorView().findViewById(android.R.id.content);

        setContentView(R.layout.actividad_registrarse);
        actividadregistrarse = this;

        if (savedInstanceState != null) {

        } else {

        }
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // --> guardar el idioma seleccionado a ya que a la hora de rotar sino se pondria
        //     por defecto el idioma predetermionado y no el elegido por el usuario
        // --> tambien guardar todos los editexte pq al rotar se pierden
        super.onSaveInstanceState(savedInstanceState);


        //obtener los valores a guardar
        EditText edUser = findViewById(R.id.editUser);
        EditText edPass1 = findViewById(R.id.editPass);
        EditText edPass2 = findViewById(R.id.editPass2);
        EditText edEdad = findViewById(R.id.editEdad);
        EditText edPeso = findViewById(R.id.editPeso);
        EditText edAltura = findViewById(R.id.editAltura);

        //guardar los valores obtenidos
        savedInstanceState.putString("idioma", GestorIdiomas.storeLang);
        if (edUser!=null){
            savedInstanceState.putString("user",edUser.getText().toString());
        }if (edPass1!=null){
            savedInstanceState.putString("pass",edPass1.getText().toString());
        }if (edPass2!=null){
            savedInstanceState.putString("pass2",edPass2.getText().toString());
        }if (edEdad!=null){
            savedInstanceState.putString("edad",edEdad.getText().toString());
        }if (edPeso!=null){
            savedInstanceState.putString("peso",edPeso.getText().toString());
        }if (edAltura!=null){
            savedInstanceState.putString("altura",edAltura.getText().toString());
        }
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
        EditText edPass1 = findViewById(R.id.editPass);
        EditText edPass2 = findViewById(R.id.editPass2);
        EditText edEdad = findViewById(R.id.editEdad);
        EditText edPeso = findViewById(R.id.editPeso);
        EditText edAltura = findViewById(R.id.editAltura);

        if (edUser!=null){
            edUser.setText(user);
        }if (edPass1!=null){
            edPass1.setText(pass);
        }if (edPass2!=null){
            edPass2.setText(pass2);
        }if (edEdad!=null){
            edEdad.setText(edad);
        }if (edPeso!=null){
            edPeso.setText(peso);
        }if (edAltura!=null){
            edAltura.setText(altura);
        }

        GestorIdiomas.cambiarIdioma(idioma,c,a);
    }


}