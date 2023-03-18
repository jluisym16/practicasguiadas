package com.example.practicaguiada5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private TextView texto;
    private static final String msg="Has seleccionado ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texto= findViewById(R.id.textView);
        ListView list =findViewById(R.menu.list_view);
        list.setAdapter(new ListAdapter(this, R.menu.list_view,new ArrayList(Arrays.asList("nom1", "nom2", "nom3"))));
        registerForContextMenu(list);
    }

    //se sobreescribe el metodo para crear los elementos que estén en la clase menu_principal
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        texto.setText(String.format("%s%s",msg,item.getTitle()));
        return true;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
            case R.id.elim:
                listViewItemAction(item);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void listViewItemAction(MenuItem item) {
        //menu emergente
        ListView l = findViewById(R.menu.list_view);
        ListAdapter a =(ListAdapter) l.getAdapter();
        int position = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
        switch (item.getItemId()) {
            case R.id.edit:
                a.editar(position);
                break;
            case R.id.elim:
                a.remove(Integer.toString(position));
        }

    }
}