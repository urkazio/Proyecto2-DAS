package com.example.pruebamonigote;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ElViewHolder extends RecyclerView.ViewHolder {
    public TextView nombre;
    public TextView descripcion;

    public ImageView laimagen;

    /**
     * Para gestionar la interacción del usuario.
     * Hay que poder acceder a ese control de la selección desde
     * la clase que extiende ViewHolder
     **/
    public boolean[] seleccion;

    public ElViewHolder(@NonNull View itemView) {
        super(itemView);
        nombre = itemView.findViewById(R.id.tvNombreEjer);
        descripcion = itemView.findViewById(R.id.tvSeriesRepes);
        laimagen = itemView.findViewById(R.id.imageView);

        /**listener del itemView para interraccionar con las imagenes al ser clickadas**/
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // obtener el nombre de la rutina ne minusculas y sin espacios ya
                // que asi se lama el txt que contiene los ejercicios
                String nombreRutina = nombre.getText().toString().replaceAll("\\s+",""). replaceAll("-", "").toLowerCase();

                // solo quiero que se abra la ActividadEjerciciosRutinas si clicko en un
                // cardview estando en ActividadListaRutinasPred. El elemento diferenciador
                // de este cardview es que la imagen es el logo de mi app
                if (laimagen.getDrawable().getConstantState() == view.getContext().getResources().getDrawable( R.drawable.logo).getConstantState()){

                    String actividadActual = view.getContext().toString();

                    // a este punto se puede llegar desde dos actividades:
                    // ActividadMisRutinas o ActividadListaRutinasPred
                    // depende desde donde vengamos, al hacer click en un cardview
                    // se quiere abrir una u otra actividad nueva.

                    if (actividadActual.contains("ActividadMisRutinas")){
                        // si vengo de ActividadMisRutinas quiero que se abra la actividad que lista mis rutinas personalizadas
                        // que ademas dicha interfaz contiene un boton de añadir nuevo ejercicio
                        Intent intent = new Intent(view.getContext(),ActividadEjerciciosRutinasPersonal.class);
                        intent.putExtra("nombreRutina", nombreRutina);
                        view.getContext().startActivity(intent);
                    }
                    else if  (actividadActual.contains("ActividadListaRutinasPred")) {
                        // si vengo de ActividadListaRutinasPred quiero que se abra la actividad que lista
                        // las rutinas predeterminadas en la aplicacion
                        Intent intent = new Intent(view.getContext(),ActividadEjerciciosRutinas.class);
                        intent.putExtra("nombreRutina", nombreRutina);
                        view.getContext().startActivity(intent);
                    }
                }
            }
        });

    }

}

