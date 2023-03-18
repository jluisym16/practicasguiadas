package com.example.practicaguiada6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VerificacionActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);

        //onclick
        ((Button) findViewById(R.id.aceptar)).setOnClickListener(this::onClick);
        ((Button) findViewById(R.id.rechazar)).setOnClickListener(this::onClick);

        //obtiene de values el valor para "pregunta" y lo muestra en pantalla
        ((TextView) findViewById(R.id.textView2)).setText(getIntent().getStringExtra("pregunta"));
    }

    @Override
    public void onClick(View view) {
        //se crea un intent para poder enviar datos a otro intent en otra clase
    Intent intent = new Intent();
    switch (view.getId()){
        case R.id.aceptar:
            intent.putExtra("resultado", "SI");
            break;
        case R.id.rechazar:
            intent.putExtra("resultado", "NO");
            break;
    }
    //??
    setResult(RESULT_OK, intent);
    finish();
    }
}