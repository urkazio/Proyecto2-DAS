package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class ActividadAnnadirEjercicio extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinner;
    ImageView imagenEjer;
    EditText editNumSeries;
    EditText editNumRepes;
    String nombreRutina = "";
    String descripcionRutina = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_annadir_ejercicio);

        //combobox ejercicio
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ejercicios, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        editNumSeries = findViewById(R.id.editNumSeries);
        editNumSeries.setInputType(InputType.TYPE_CLASS_NUMBER);
        editNumSeries.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "8")});

        editNumRepes = findViewById(R.id.editNumRepes);
        editNumRepes.setInputType(InputType.TYPE_CLASS_NUMBER);
        editNumRepes.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "8")});


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = spinner.getSelectedItem().toString();
        String nombreFotoEjer = text.replaceAll("\\s+","").toLowerCase();
        int id = getResources().getIdentifier(nombreFotoEjer, "drawable", getPackageName());
        imagenEjer = findViewById(R.id.imageView2);
        imagenEjer.setImageResource(id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void annadirejer(View v) {

        String ficheroRutina="";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ficheroRutina = extras.getString("ficheroRutina");
            nombreRutina = extras.getString("nombreRutina");
            descripcionRutina = extras.getString("descripcionRutina");
        }

        editNumSeries = findViewById(R.id.editNumSeries);
        String numSeries = editNumSeries.getText().toString();

        editNumRepes = findViewById(R.id.editNumRepes);
        String numRepes = editNumRepes.getText().toString();

        System.out.println("ficheroRutina: "+ficheroRutina);

        if (numRepes.equals("") || numSeries.equals("")){

            Toast.makeText(this, "No puede haber campos vacios", Toast.LENGTH_LONG).show();

        }else{

            try {
                OutputStreamWriter fichero = new OutputStreamWriter(openFileOutput(ficheroRutina+".txt", Context.MODE_APPEND));

                //Jalon al Pecho,4,10,jalonalpecho

                String nombreEjer = spinner.getSelectedItem().toString();

                String nombreFotoEjer = nombreEjer.replaceAll("\\s+","").toLowerCase();


                fichero.write(nombreEjer+","+numSeries+","+numSeries+","+nombreFotoEjer+"\n");
                fichero.close();

            } catch (IOException e) {}

            Intent intent = new Intent(this,ActividadEjerciciosRutinasPersonal.class);
            intent.putExtra("nombrefichero", ficheroRutina);
            intent.putExtra("nombreRutina",nombreRutina);
            intent.putExtra("descripcionRutina",descripcionRutina);
            startActivity(intent);
            finish(); // cerrar actividad
        }

    }
}