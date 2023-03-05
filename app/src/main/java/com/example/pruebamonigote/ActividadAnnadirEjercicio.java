package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;

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

public class ActividadAnnadirEjercicio extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinner;
    ImageView imagenEjer;
    EditText editNumSeries;
    EditText editNumRepes;

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
        Intent intent = new Intent(this, ActividadPrincipal.class);
        startActivity(intent);
        //cerrar la activiidad enterior
    }
}