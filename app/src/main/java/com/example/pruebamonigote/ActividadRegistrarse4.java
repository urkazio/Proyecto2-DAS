package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

public class ActividadRegistrarse4 extends AppCompatActivity {


    EditText editPeso;
    EditText editAltura;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registrarse4);

        //RESTRICCIONES para el editText del peso
        editPeso = findViewById(R.id.editPeso);
        editPeso.setInputType(InputType.TYPE_CLASS_NUMBER );
        editPeso.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) }); //limitar a 3 caracteres

        //RESTRICCIONES para el editText de la altura
        editAltura = findViewById(R.id.editAltura);
        editAltura.setInputType(InputType.TYPE_CLASS_NUMBER);
        editAltura.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) }); //limitar a 3 caracteres

    }
    public void finalizar(View v) {
        Intent intent = new Intent(this, ActividadPrincipal.class);
        startActivity(intent);
    }
}