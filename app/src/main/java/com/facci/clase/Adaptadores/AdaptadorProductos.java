package com.facci.clase.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facci.clase.R;

import java.util.ArrayList;

/**
 * Created by juanm on 11/02/2019.
 */

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.ViewHolderDatos> implements View.OnClickListener {

    private View.OnClickListener listener;
    ArrayList<String> lista_productos;
    Context context;
    View view;

    public AdaptadorProductos(ArrayList<String> productos, Context c) {

        /**Obtiene la informacion desde fuera*/
        lista_productos=productos;
        context=c;
    }

    public void setOnClickListener(View.OnClickListener listen){
        this.listener=listen;
    }

    @NonNull
    @Override
    public AdaptadorProductos.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view= LayoutInflater.from(
                viewGroup.getContext()).inflate(R.layout.fila_producto,
                null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorProductos.ViewHolderDatos holder, int i) {
        //Para mostrar los datos en el recycleview

        holder.txt_prod.setText(lista_productos.get(i).toString());
    }

    @Override
    public int getItemCount() {
        return lista_productos.size();
    }

    @Override
    public void onClick(View v) {
        if(listener!=null) listener.onClick(view);
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView txt_prod;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            txt_prod=(TextView) itemView.findViewById(R.id.txt_nombre);
        }
    }
}
