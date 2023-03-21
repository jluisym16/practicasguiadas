package com.example.practicaguiada20;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AddressAdapter extends ArrayAdapter<Address> {
    List<Address> direcciones;

    public AddressAdapter(@NonNull Context context, @NonNull List<Address> direcciones) {
        super(context, android.R.layout.simple_list_item_1, direcciones);
        this.direcciones = direcciones;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view,
                        @NonNull ViewGroup parent) {
        Address a = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView text = view.findViewById(android.R.id.text1);
        String localidad = a.getLocality();
        String areaAdmin = a.getAdminArea();
        String pais = a.getCountryName();
        String cp = a.getPostalCode();
        String nombre = a.getFeatureName();
        text.setText("\nCiudad: ");
        text.append(localidad == null ? "desconocido" : localidad);
        text.append("\nArea Administrativa: ");
        text.append(areaAdmin == null ? "desconocido" : areaAdmin);
        text.append("\nPaís: ");
        text.append(pais == null ? "desconocido" : pais);
        text.append("\nCódigo Postal: ");
        text.append(cp == null ? "desconocido" : cp);
        text.append("\nNombre Conocido: ");
        text.append(nombre == null ? "desconocido" : nombre);
        text.append("\n");
        return view;
    }

    public void actualizar(List<Address> direcciones) {
        this.direcciones.clear();
        this.direcciones.addAll(direcciones);
        notifyDataSetChanged();
    }
}

