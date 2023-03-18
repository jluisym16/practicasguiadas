package com.example.practicaguiada5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
//se extiende a arrayAdapter<String> porque en el mainActivity se pasaran los datos de los centros en forma de un array compuesto de string
public class ListAdapter extends ArrayAdapter<String> {

    //se crea un objeto centros para poder operar con él que ha de ser una lista
    private final List<String> centros;

    //se genera un constructor que tenga al menos la lista y se empareja con el objeto que hemos creado previamente
    public ListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.centros = objects;
    }

    //se tienen que hacer mínimo 3 métodos, uno por acción (eliminar, modificar) y otro para poder obtener la vista del menú


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
            convertView= LayoutInflater.from(this.getContext()).inflate(R.menu.list_view, parent, false);
        ((TextView) convertView).setText((getItem(position)));
        return convertView;
    }

    public void eliminar(int position){
        centros.remove(position);
        notifyDataSetChanged();
    }
    public void editar(int position){
        centros.add(position, String.format("%s%s", centros.remove(position), " (editado"));
        notifyDataSetChanged();
    }
}
