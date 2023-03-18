package com.example.practicaguiada19;

import android.hardware.Sensor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

public class ListaSensoresFragment extends ListFragment {
    //Se genera con codigo el fragmento para usar menos código en comparación con uno autogenerado
    ArrayAdapter<Sensor> adapter;

    public ListaSensoresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lista_sensores, container, false);
        setListAdapter(new SensoresAdapter(inflater.getContext(), android.R.layout.simple_list_item_1, ((MainActivity) getActivity()).getSensores()));
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Fragment fragment = SensorFragment.newInstance(position);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack("sensor_fragment")
                .commit();
    }
}
