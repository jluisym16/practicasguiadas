package com.example.practicaguiada4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CentrosAdapter extends ArrayAdapter<Centro> {
    public CentrosAdapter(@NonNull Context context, int resource, @NonNull List<Centro> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //se crea un objeto para que se pueda obtener el Item que esté en el int posicion
        Centro c = getItem(position);

        //esto hace que si no hay una view creada, se crea una que contenga todos los elementos que haya en el layout "fila"
        if(convertView== null)
            convertView= LayoutInflater.from(this.getContext()).inflate(R.layout.item, parent, false);

        //le asigna a los elementos de la view item los datos que se especifiquen en el array de datos que se definirá en la mainaxtivity
        //se hace con cast para no tener que crear y asignarle una variable de un solo uso a cada elemento
        ((ImageView) convertView.findViewById(R.id.imageView)).setImageResource(c.getImagen());
        ((TextView) convertView.findViewById(R.id.textView)).setText(c.getNombre());
        ((TextView) convertView.findViewById(R.id.textView3)).setText(c.getDireccion());

        return convertView;
    }
}
