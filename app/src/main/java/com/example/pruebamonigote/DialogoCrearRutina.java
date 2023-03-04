package com.example.pruebamonigote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialogoCrearRutina {
    public static void onCreateDialog(ActividadPrincipal actividadPrincipal, View v) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(actividadPrincipal);

        Context context = actividadPrincipal;

        // Layout propio para el dialogo
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        //el id de la rutina será el num lineas del fichero misRutinas

        dialog.setTitle("   Nueva Rutina Vacía");

        // Añadir un TextView para indicar que se debe insertar el "Título"
        final TextView tvNombre = new TextView(context);
        tvNombre.setText("Introduce el nombre de la rutina");
        tvNombre.setPadding(100,80,100,20);
        layout.addView(tvNombre); // Se añade a la vista

        // Añadir un EditText para insertar el "Título"
        final EditText titleBox = new EditText(context);
        titleBox.setHint("Nombre");
        titleBox.setPadding(100,20,100,40);
        layout.addView(titleBox); // Se añade a la vista

        // Añadir otro TextView para indicar que se debe insertar el "Descripción"
        final TextView tvDescr = new TextView(context);
        tvDescr.setText("Añade una breve descripción a la rutina (máx 50 caracteres)");
        tvDescr.setPadding(100,80,100,20);
        layout.addView(tvDescr); // Se añade a la vista

        // Añadir otro EditText para la insertar el "Descripción"
        final EditText descriptionBox = new EditText(context);
        descriptionBox.setFilters(new InputFilter[] { new InputFilter.LengthFilter(50) }); //limitar a 50 caracteres
        descriptionBox.setHint("Descripción");
        descriptionBox.setPadding(100,20,100,40);
        layout.addView(descriptionBox); // Se añade a la vista

        dialog.setIcon(R.drawable.logo);

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //m_Text = input.getText().toString();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.setView(layout); // Again this is a set method, not add
        dialog.show();
    }
}
