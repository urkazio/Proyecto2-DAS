package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class ActividadRegistrarse3 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText editEdad;
    public static ActividadRegistrarse3 actividadRegistrarse3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registrarse3);
        actividadRegistrarse3=this;

        //combobox
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genero, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //RESTRICCIONES para el editText de la edad
        editEdad = findViewById(R.id.editEdad);
        editEdad.setInputType(InputType.TYPE_CLASS_NUMBER);
        editEdad.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) }); //limitar a 3 caracteres

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void continuar(View v) {

        String User="";
        String Pass="";


        Spinner spinner = findViewById(R.id.spinner);
        String Genero = spinner.getSelectedItem().toString();

        editEdad = findViewById(R.id.editEdad);

        if (editEdad.getText().toString().equals("")){
            Toast.makeText(this, "Hay campos vacios", Toast.LENGTH_LONG).show();

        }else{
            int Edad = Integer.parseInt(editEdad.getText().toString());

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                User = extras.getString("User");
                Pass  = extras.getString("Contraseña");
            }

            Intent intent = new Intent(this, ActividadRegistrarse4.class);
            intent.putExtra("User",User);
            intent.putExtra("Contraseña",Pass);
            intent.putExtra("Genero",Genero);
            intent.putExtra("Edad",Edad);
            startActivity(intent);
        }

    }
}