package com.example.practicaguiada4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //datos
        List<Centro> centro = new ArrayList<>(Arrays.asList(
                new Centro("dfa", "asdf", R.drawable.ggsffsdg)
        ));

        //se crea un adapter y se usa
        CentrosAdapter adapter = new CentrosAdapter(this, R.layout.item, centro);
        setListAdapter(adapter);

        //cuando se pulse un item se usa el metodo onItemClick
        getListView().setOnItemClickListener(this::onItemClick);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //se crea un objeto centro para poder obtener la posicion de él
        Centro c = (Centro) adapterView.getItemAtPosition(i);
        Toast.makeText(this, "testo", Toast.LENGTH_SHORT).show();
    }
}