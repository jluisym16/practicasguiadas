package com.example.practicaguiada21;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MediaRecorderAudioActivity extends AppCompatActivity {
    TextView estado;
    ImageButton grabar;
    ImageButton detener;
    ImageButton reproducir;
    String file;
    MediaRecorder recorder;
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_recorder_audio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        estado = findViewById(R.id.textViewEstado);
        grabar = findViewById(R.id.botonMicrofono);
        grabar.setOnClickListener(this::grabarClick);
        detener = findViewById(R.id.parar);
        detener.setOnClickListener(this::pararClick);
        detener.setEnabled(false);
        reproducir = findViewById(R.id.reproducir);
        reproducir.setOnClickListener(this::reproducirClick);
        file = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                .getAbsolutePath() + "/grabacion.3gp";
        estado.setText(file);
    }
    private void grabarClick(View v) {
        grabar.setEnabled(false);
        detener.setEnabled(true);
        reproducir.setEnabled(false);
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(file);
        try {
            recorder.prepare();
            recorder.start();
            estado.setText("grabación iniciada");
        } catch (IOException e) {
            estado.setText(e.getLocalizedMessage());
        }
    }
    private void pararClick(View v) {
        grabar.setEnabled(true);
        detener.setEnabled(false);
        reproducir.setEnabled(true);
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
            estado.setText("grabación detenida");
        }
        else if (player != null) {
            player.stop();
            player.release();
            player = null;
            estado.setText("reproducción detenida");
        }
    }
    private void reproducirClick(View v) {
        grabar.setEnabled(false);
        detener.setEnabled(true);
        reproducir.setEnabled(false);
        player = new MediaPlayer();
        try {
            player.setDataSource(file);
            player.prepare();
            player.start();
            estado.setText("reproduciendo grabación");
        } catch (IOException e) {
            estado.setText(e.getLocalizedMessage());
}
}
}