package com.example.practicaguiada10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CentrosAdapter extends ArrayAdapter<Centro> {
    List<Centro> centros = new ArrayList<>();
    Context context;
    public CentrosAdapter(List<Centro> centros, Context context) {
        super(context, R.layout.activity_lista_centros_fragment, centros);
        this.centros = centros;
        this.context = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater =(LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.activity_lista_centros_fragment, parent, false);
        }
        Centro centro = centros.get(position);
        ((ImageView) convertView.findViewById(R.id.imageView))
                .setImageResource(centro.getLogo());
        ((TextView) convertView.findViewById(R.id.textView))
                .setText(centro.getNombre());
        return convertView;
    }
}
