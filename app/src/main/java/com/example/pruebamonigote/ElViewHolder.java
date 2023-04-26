package com.example.pruebamonigote;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ElViewHolder extends RecyclerView.ViewHolder {
    public TextView nombre;
    public TextView descripcion;

    public ImageView laimagen;
    String user;

    /**
     * Para gestionar la interacción del usuario.
     * Hay que poder acceder a ese control de la selección desde
     * la clase que extiende ViewHolder
     **/
    public boolean[] seleccion;

    public ElViewHolder(@NonNull View itemView, String User) {
        super(itemView);
        nombre = itemView.findViewById(R.id.tvNombreRutinaOEjer);
        descripcion = itemView.findViewById(R.id.tvSeriesRepes);
        laimagen = itemView.findViewById(R.id.imageView);
        user = User;

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
                        intent.putExtra("nombreRutina", nombre.getText().toString());
                        intent.putExtra("descripcionRutina", descripcion.getText().toString());
                        intent.putExtra("nombrefichero", nombreRutina);
                        intent.putExtra("user",user);
                        view.getContext().startActivity(intent);
                    }
                    else if  (actividadActual.contains("ActividadListaRutinasPred")) {
                        // si vengo de ActividadListaRutinasPred quiero que se abra la actividad que lista
                        // las rutinas predeterminadas en la aplicacion
                        Intent intent = new Intent(view.getContext(),ActividadEjerciciosRutinas.class);
                        intent.putExtra("nombreRutina", nombreRutina);
                        view.getContext().startActivity(intent);
                    }

                    //si la imagen es una foto de los contactos, abro el intent del sms para mandar la rutina
                }else if (laimagen.getDrawable().getConstantState() == view.getContext().getResources().getDrawable( R.drawable.contactos).getConstantState()){
                    String sms = ActividadContactos.getSmsBody();
                    String telefono = descripcion.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", telefono, null));
                    intent.putExtra("sms_body", sms);
                    view.getContext().startActivity(intent);

                    //si la imagen no es ni el logo de la app ni la agenda de contactos es un ejercicio y al clickarlo quiero mostrar el video de youtube
                }else{
                    String nombreEjercicio = nombre.getText().toString();
                    Intent intent = new Intent(view.getContext(),ActividadVideo.class);
                    intent.putExtra("nombre_ejercicio", nombreEjercicio);
                    view.getContext().startActivity(intent);

                }
            }
        });

    }

}

