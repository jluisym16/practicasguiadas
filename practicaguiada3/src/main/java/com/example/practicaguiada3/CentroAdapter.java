package com.example.practicaguiada3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
//se exitende el recyvlerview porque se usa un rv, adapter porque es un adapter
public class CentroAdapter extends RecyclerView.Adapter<CentroAdapter.ViewHolder>{
        //contenedor de datos
        List<Centro> modelList;

    public CentroAdapter(List<Centro> modelList) {
        this.modelList = modelList;
    }

    //crea la viewHolder nueva y su view asociada y los inicializa pero no completa el contenido de la vista
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila, parent, false);
        return new ViewHolder(v);
    }

    /*
    Asocia una viewHolder con los datos, para ello recupera los datos correspondientes y los usa para completar el diseño del contenedor de vistas
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(modelList.get(position));
    }
    //obtenemos el tamaño del conjunto de datos
    @Override
    public int getItemCount() {
        return modelList.size();
    }

    //se crean las variables que mas tarde se mostrarán los datos en el rv
    static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView nombre;
        private final TextView direccion;
        private final ImageView imagen;

            //constructor donde identificamos los elementos a través de la id
        public ViewHolder(View v){
            super(v);
            nombre= (TextView) v.findViewById(R.id.textView);
            direccion= (TextView) v.findViewById(R.id.textView2);
            imagen= (ImageView) v.findViewById(R.id.imageView);
        }
        //se le asignan los datos de
        public void bind(Centro centro){
            nombre.setText(centro.getNombre());
            direccion.setText(centro.getDireccion());
            imagen.setImageResource(centro.getImagen());
        }
    }


}


