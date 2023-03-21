package com.example.practicaguiada21;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        //Guardamos la referencia al VideoView
        VideoView videoView=findViewById(R.id.videoView);

        //Mostrar video desde la RAW
        // getResources().openRawResource(R.raw.ejemplo);

        //Creamos una Uri con la ruta al fichero que hemos guardado en la carpeta res/raw
        //  Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.ejemplo);

        //Mostrar video desde Almacenamiento Externo
        File carpeta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        Uri uri=Uri.fromFile(new File(carpeta+"/vacas.mp4"));

        //Mostrar Video desde Internet
        //Uri uri = Uri.parse("https://assets.mixkit.co/videos/download/mixkit-redfrog-on-a-log-1487-medium.mp4");
        videoView.setVideoURI(uri);

        //Creamos un objeto mediaControler
        MediaController mediaController=new MediaController(this);
        mediaController.setAnchorView(videoView);

        //Le asignamos al objeto mediaController, la media player
        mediaController.setMediaPlayer(videoView);

        //Asignamos al VideoView el MediaController
        videoView.setMediaController(mediaController);
        videoView.start();
    }

}
