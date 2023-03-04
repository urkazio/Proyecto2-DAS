package com.example.pruebamonigote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ActividadPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
    }
    public void explorar(View v) {
        Intent intent = new Intent(this, ActividadListaRutinasPred.class);
        startActivity(intent);
    }
    public void crear(View v) {
        //DialogFragment dialogoalerta= new DialogoCrearRutina();
        //dialogoalerta.show(getSupportFragmentManager(), "dialogoCrearRutina");
        DialogoCrearRutina.onCreateDialog(this,v);


    }
    public void ver(View v) {
        Intent intent = new Intent(this, ActividadLogin5.class);
        startActivity(intent);
    }
}