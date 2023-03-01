package com.example.pruebamonigote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ElAdaptadorRecycler extends RecyclerView.Adapter<ElViewHolder> {
    private String[] losnombres;
    private String[] lasdescrpciones;
    private int[] lasimagenes;
    private boolean[] seleccionados;

    /**
     * El adaptador es el controlador del ViewHolder (RecyclerView)
     **/

    public ElAdaptadorRecycler (String[] nombres, int[] imagenes, String[] decripciones)
    {
        //constructora
        losnombres=nombres;
        lasimagenes=imagenes;
        lasdescrpciones=decripciones;
        seleccionados=new boolean[nombres.length];
    }

    @NonNull
    /** implementacion (sobreescritura) de los metodos que son extendidos (interfaces) **/

    @Override
    /** Creacion del viewHolder **/
    public ElViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /** Obtener el objeto ViewHolder*/
        View elLayoutDeCadaItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_listarutinas,null);
        ElViewHolder evh = new ElViewHolder(elLayoutDeCadaItem);

        /** “Pasar” el atributo del adaptador al atributo del ViewHolder **/
        evh.seleccion = seleccionados;
        return evh;
    }

    @Override
    /** metodo que coloca las imagenes en su posicion dentro del viewholder */
    public void onBindViewHolder(@NonNull ElViewHolder holder, int position) {
        holder.nombre.setText(losnombres[position]);
        holder.descripcion.setText(lasdescrpciones[position]);
        holder.laimagen.setImageResource(lasimagenes[position]);
    }

    @Override
    /** getter del numero de items en el viewholder **/
    public int getItemCount() {
        return losnombres.length;
    }
}
