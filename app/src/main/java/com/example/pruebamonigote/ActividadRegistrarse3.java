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
import android.widget.Spinner;
import android.widget.Toast;

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