package com.example.practicaguiada3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Se identifica el recycler view
        RecyclerView rv= findViewById(R.id.recyclerView);

        //cosa
        rv.setLayoutManager(new LinearLayoutManager(this));

        //se crea un array de información que mostrar en el recyclerView
        List<Centro> l= Arrays.asList(
                new Centro("cosa", "crgsdfgsdf", R.drawable.f1),
                new Centro("cosa", "cgsdfgsdfg", R.drawable.f2),
                new Centro("cosa", "cgsdfgsdf", R.drawable.f3)
        );

        //al rv se le asigna un adapter (centroadapter) para que muestre los datos contenidos en l
        rv.setAdapter(new CentroAdapter(l));
    }
}