package com.example.praticaguiada8;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int almacenamiento;
    TextView edit;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit = findViewById(R.id.textEdit);
        Button guardar = findViewById(R.id.buttonGuardar);
        guardar.setOnClickListener(this::onClick);
        edit.setOnClickListener(this:: onClick);
         dialog = new AlertDialog.Builder(this).
                setTitle("Selecciona almacenamiento:").
                setSingleChoiceItems(R.array.opcionesAlmacenamiento, 0, this::onOptionClick).
                setPositiveButton("Aceptar", this::onAceptarCancelarClick).
                setNegativeButton("Cancelar", this::onAceptarCancelarClick).
                create();
    }
    public void onClick(View v){
        dialog.show();
    }
    private void onAceptarCancelarClick(DialogInterface dialogo, int cual) {
        if (cual == DialogInterface.BUTTON_POSITIVE) {
            try {
                switch (almacenamiento) {
                    case 0:
                        guardar(openFileOutput("datos.txt",
                                Context.MODE_PRIVATE | Context.MODE_APPEND));
                        break;
                    case 1:
                        File dir = Environment.
                                getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                        File file = new File(dir + "/" + "datos.txt");
                        guardar(new FileOutputStream(file, true));
                        break;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        dialogo.dismiss();
    }

    private void guardar(FileOutputStream stream) {
        try (PrintWriter out = new PrintWriter(
                new OutputStreamWriter(stream, StandardCharsets.UTF_8))) {
            out.println(edit.getText().toString());
        }
    }


    public void onOptionClick(DialogInterface dialogo, int opcion) {
        almacenamiento = opcion;
    }

}