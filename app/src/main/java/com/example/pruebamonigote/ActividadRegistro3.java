package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ActividadRegistro3 extends AppCompatActivity {
    EditText editEdad;
    String user;
    String pass;
    int edad;
    Spinner spinner;
    String genero;
    public static ActividadRegistro3 actividadRegistro3;
    private int mSelectedIndex = 0;
    private static boolean preferenciasCargadas = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Context c = this;
        actividadRegistro3=this;
        if (!preferenciasCargadas){
            preferenciasCargadas=true;
            GestorIdiomas.cargarPreferencias(c,actividadRegistro3);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registro3);

        //combobox
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genero, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //RESTRICCIONES para el editText de la edad
        editEdad = findViewById(R.id.editEdad);
        editEdad.setInputType(InputType.TYPE_CLASS_NUMBER);
        editEdad.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) }); //limitar a 3 caracteres

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("User");
            pass = extras.getString("Contraseña");
        }

        if (savedInstanceState != null) {
            String edad = savedInstanceState.getString("edad");
            editEdad.setText(edad);
            mSelectedIndex = savedInstanceState.getInt("selected_index");
            spinner = findViewById(R.id.spinner);
            spinner.setSelection(mSelectedIndex);
        }

    }

    public void continuar (View v) {

        spinner = findViewById(R.id.spinner);
        genero = spinner.getSelectedItem().toString();
        editEdad = findViewById(R.id.editEdad);
        edad = Integer.parseInt(editEdad.getText().toString());


        if (editEdad.getText().toString().equals("")) {
            Toast.makeText(this, R.string.str133, Toast.LENGTH_LONG).show();

        } else {

            System.out.println("User: " + user + "// pass: " + pass + "// genero: " + genero + "// edad: " + edad);

            Intent i = new Intent(this, ActividadRegistro4.class);
            i.putExtra("User", user);
            i.putExtra("Contraseña", pass);
            i.putExtra("Genero", genero);
            i.putExtra("Edad", edad);
            startActivity(i);
        }
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // guardar el idioma seleccionado a ya que a la hora de rotar sino se pondria
        // por defecto el idioma predetermionado y no el elegido por el usuario
        super.onSaveInstanceState(savedInstanceState);
        preferenciasCargadas=false;
        savedInstanceState.putString("edad", editEdad.getText().toString());
        savedInstanceState.putInt("selected_index", mSelectedIndex);
    }
}