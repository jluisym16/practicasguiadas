package com.example.practicaguiada6;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ActivityResultCallback<ActivityResult> {
TextView texto;
    //Se crea un objeto ActivityResultLauncher
    //sirve?
//    ActivityResultLauncher<Intent> launcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView texto= findViewById(R.id.textView3);
        //Se identifica el botón y se linkea al método "onClick"
        ((Button) findViewById(R.id.button)).setOnClickListener(this::onClick);
        ((Button)findViewById(R.id.botondemo)).setOnClickListener(this::onClick);
//        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                //hace un intent a la clase verificationactivity
                Intent intent = new Intent(this, VerificacionActivity.class);

                //variables donde se almacenan los datos, tanto como el nombre como la pregunta
                //asigna editNombre a "nombre"
                String nombre = ((EditText) findViewById(R.id.editNombre)).getText().toString();
                //Como identifica el textView donde se tiene que poner??
                String pregunta = String.format(getResources().getString(R.string.pregunta), nombre);
                //muestra la pregunta en pantalla
                intent.putExtra("pregunta", pregunta);
                //ejecuta el metodo launch del objeto launcher con el intent como parámetro
//                launcher.launch(intent);
                startActivityForResult(intent, 1);
                break;
            case R.id.botondemo:
                //intent a demoactivity
                Intent intent1=new Intent(this, DemoActivity.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onActivityResult(ActivityResult result) {
        //¿?¿
    if(result.getResultCode()== Activity.RESULT_OK){
        //Obtiene los datos de los botones de la activity verificacion
        //como obtiene los datos de otra class
        Intent datos = result.getData();
        //
        texto.setText("El resultado a la pregunta es: "+(datos.getStringExtra("resultado")));
    }
    }
}