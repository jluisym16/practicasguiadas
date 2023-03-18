package com.example.practicaguiada6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class DemoActivity extends AppCompatActivity{//porque no onclick listener

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ((Button)findViewById(R.id.web)).setOnClickListener(this::onClick);
        ((Button)findViewById(R.id.llamada)).setOnClickListener(view -> onClick(view));
        ((Button)findViewById(R.id.camara)).setOnClickListener(this::onClick);
        ((Button)findViewById(R.id.correo)).setOnClickListener(this::onClick);
        ((Button)findViewById(R.id.mapa)).setOnClickListener(this::onClick);
    }


    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.web:
                intent.setDataAndType(Uri.parse("https://www.google.com"), Intent.ACTION_VIEW);
                break;
            case R.id.llamada:
                intent.setDataAndType(Uri.parse("tel:660446217"), Intent.ACTION_DIAL);
                break;
            case R.id.camara:
                intent.setType(MediaStore.ACTION_IMAGE_CAPTURE);
                break;
            case R.id.correo:
                intent.setType(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "asunto");
                intent.putExtra(Intent.EXTRA_TEXT, "Texto del correo");
                intent.putExtra(Intent.EXTRA_EMAIL, "correo@correo.es");
                break;
            case R.id.mapa:
                intent.setDataAndType(Uri.parse("geo:43.359792, -5.85579"), Intent.ACTION_VIEW);
                break;
        }
        startActivity(intent);
    }
}