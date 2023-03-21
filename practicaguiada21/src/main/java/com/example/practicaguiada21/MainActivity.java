package com.example.practicaguiada21;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.videoActivity).setOnClickListener(this::respuesta);
        findViewById(R.id.mediaPlayerVideoActivity).setOnClickListener(this::respuesta);
        findViewById(R.id.mediaRecorderAudioActivity).setOnClickListener(this::respuesta);
        findViewById(R.id.grabarVideoActivity).setOnClickListener(this::respuesta);
        comprobarSolicitarPermisos(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA);
    }
    public void comprobarSolicitarPermisos(String...permisos){
        List<String> lista=new ArrayList<>();
        for(String permiso:permisos)
            if(checkSelfPermission(permiso)!= PackageManager.PERMISSION_GRANTED){
                lista.add(permiso);
            }
        if (!lista.isEmpty()){
            requestPermissions(lista.toArray(new String[lista.size()]),1);
        }
    }
    private void respuesta(View view){
        Class<?>clase=null;
        switch (view.getId()){
            case R.id.videoActivity:
                clase=VideoActivity.class;
                break;
            case R.id.mediaPlayerVideoActivity:
                clase= MediaPlayerActivity.class;
                break;
            case R.id.mediaRecorderAudioActivity:
                clase=MediaRecorderAudioActivity.class;
                break;
            case R.id.grabarVideoActivity:
                clase=GrabarVideoActivity.class;
                break;
        }
        startActivity(new Intent(this,clase));
    }
}