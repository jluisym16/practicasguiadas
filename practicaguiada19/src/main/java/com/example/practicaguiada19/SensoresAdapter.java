package com.example.practicaguiada19;

import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SensoresAdapter extends ArrayAdapter<Sensor> {
    List<Sensor> sensores;


    public SensoresAdapter(@NonNull Context context, int resource, @NonNull List<Sensor> objects){
        super(context, resource, objects);
        sensores= objects;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Sensor sensor=getItem(position);
        convertView= LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1,parent, false);
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(sensor.getName());
        return convertView;
    }
}
