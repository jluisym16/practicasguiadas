package com.example.practicaguiada14;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    private ContentResolver resolver;
    private final String[] cols = new String[]{
            BaseColumns._ID,
            AlumnosContentProvider.COL_NOMBRE,
            AlumnosContentProvider.COL_APELLIDOS,
            AlumnosContentProvider.COL_CURSO
    };
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttonTodos).setOnClickListener(this::consultarTodosClick);
        findViewById(R.id.buttonRegistro).setOnClickListener(this::consultarUnoClick);
        findViewById(R.id.buttonInsertar).setOnClickListener(this::insertarClick);
        findViewById(R.id.buttonEliminar).setOnClickListener(this::eliminarClick);
        textView = findViewById(R.id.textView);
        resolver = getContentResolver();
    }
    private void consultarTodosClick(View view) {
        actualizarConsulta(resolver.query(AlumnosContentProvider.TABLA_ALUMNOS_URI, cols, null, null, null));
    }
    private void consultarUnoClick(View view) {
        pedirProcesarId(this::consultar);
    }
    private void insertarClick(View view) {
    }
    private void eliminarClick(View view) {
        pedirProcesarId(this::eliminar);
    }
    private void consultar(long id) {
        Uri uri = ContentUris.withAppendedId(AlumnosContentProvider.TABLA_ALUMNOS_URI, id);
        actualizarConsulta(resolver.query(uri, cols, null, null, null));
    }
    private void eliminar(long id) {
        Uri uri = ContentUris.withAppendedId(AlumnosContentProvider.TABLA_ALUMNOS_URI, id);
        int n = resolver.delete(uri, null, null);
        if (n == 1) {
            StringBuilder resultado = new StringBuilder("URI DEL ALUMNO ELIMINADO\n\n");
            resultado.append(uri);
            runOnUiThread(() -> textView.setText(resultado));
        } else
            runOnUiThread(() -> textView.setText("NINGÚN ALUMNO ELIMINADO"));
    }

    //apoyo para consultarTodosClick y para consultar. Recibe datos y los muestra en el textView
    private void actualizarConsulta(Cursor cursor) {
        StringBuilder resultado = new StringBuilder("RESULTADO DE LA CONSULTA\n\n");
        if (cursor.moveToFirst()) {
            do {
                resultado.append(cursor.getInt(0));
                resultado.append(": ");
                resultado.append(cursor.getString(1));
                resultado.append(" ");
                resultado.append(cursor.getString(2));
                resultado.append(" (");
                resultado.append(cursor.getString(3));
                resultado.append(")\n");
            } while (cursor.moveToNext());
            runOnUiThread(() -> textView.setText(resultado));
        } else
            runOnUiThread(() -> textView.setText("LA CONSULTA NO RETORNA DATOS"));
    }

    //apoyo para consultarUnoClick y eliminarClick
    private void pedirProcesarId(Consumer<Long> accion) {
        final View layout = getLayoutInflater().inflate(R.layout.dialog, null);
        new AlertDialog.Builder(this).
                setTitle("Identificación del alumno").
                setView(layout).
                setPositiveButton("Aceptar", (d, w) -> {
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText editText = layout.findViewById(R.id.editTextId);
                    long id = Long.parseLong(editText.getText().toString());
                    accion.accept(id);
                }).
                setNegativeButton("Cancelar", (d, w) -> {
                }).
                show();
    }
}