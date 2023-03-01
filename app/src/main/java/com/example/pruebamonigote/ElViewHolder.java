package com.example.pruebamonigote;

import android.graphics.Color;
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
        descripcion = itemView.findViewById(R.id.tvDescr);
        laimagen = itemView.findViewById(R.id.imageView);

        /**listener del itemView para interraccionar con las imagenes al ser clickadas**/
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (seleccion[getAdapterPosition()] == true) {
                    seleccion[getAdapterPosition()] = false;
                    laimagen.setColorFilter(null);
                }else if ((laimagen.getColorFilter() == null)) {
                    laimagen.setColorFilter(Color.BLUE);
                }else{
                    laimagen.setColorFilter(Color.WHITE);
                    // deselecciona la imagen (para que vuelva a no tener color)
                    seleccion[getAdapterPosition()] = true;
                }
            }
        });
    }
}

