package com.example.practicaguiada21;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MediaPlayerActivity extends AppCompatActivity implements SurfaceHolder.Callback {


    private enum Estado {INACTIVO, INICIADO, PAUSADO}

    private MediaPlayer mediaplayer;
    private SurfaceView surface;
    private ImageButton almacenamientoCompartido, playPause, url, stop;
    private Estado estado = Estado.INACTIVO;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_pleyer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        (almacenamientoCompartido = findViewById(R.id.almacenamiento))
                .setOnClickListener(this::almacenamientoClick);
        (url = findViewById(R.id.internet))
                .setOnClickListener(this::urlClick);
        (playPause = findViewById(R.id.play))
                .setOnClickListener(this::playPauseClick);
        (stop = findViewById(R.id.parar))
                .setOnClickListener(this::stopClick);
        surface = findViewById(R.id.surfaceView);
        surface.getHolder().addCallback(this);
        mediaplayer=new MediaPlayer();
        mediaplayer.setOnPreparedListener(this::onPrepared);
        mediaplayer.setOnVideoSizeChangedListener(this::onVideoSizeChanged);
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaplayer.setLooping(true);
    }




    @Override
    protected void onPause() {
        super.onPause();
        if (estado == Estado.INICIADO)
            mediaplayer.pause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaplayer.release();
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        mediaplayer.setDisplay(surface.getHolder());
        if (estado == Estado.INACTIVO) {
            if (uri != null)
                setDataSource();
        } else if (estado == Estado.INICIADO)
            mediaplayer.start();
        else
            mediaplayer.seekTo(mediaplayer.getCurrentPosition());
    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
    }
    private void onPrepared(MediaPlayer mediaPlayer) {
        almacenamientoCompartido.setVisibility(View.GONE);
        url.setVisibility(View.GONE);
        playPause.setVisibility(View.VISIBLE);
        stop.setVisibility(View.VISIBLE);
        estado = Estado.INICIADO;
        playPause.setImageResource(R.drawable.pausecirculo);
        mediaplayer.start();
    }
    private void almacenamientoClick(View v) {
        Intent intent = new Intent()
                .setType("video/*")
                .setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a file"), 1);
    }

    private void urlClick(View v) {
        final EditText editText = new EditText(this);
        new AlertDialog.Builder(this)
                .setView(editText)
                .setTitle("URL del vídeo")
                .setPositiveButton("Aceptar", (d, w) -> {
                    uri = Uri.parse(editText.getText().toString());
                    setDataSource();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void playPauseClick(View v) {
        if (estado != Estado.INICIADO) {
            mediaplayer.start();
            estado = Estado.INICIADO;
            playPause.setImageResource(R.drawable.pausecirculo);
        } else {
            mediaplayer.pause();
            estado = Estado.PAUSADO;
            playPause.setImageResource(R.drawable.playcirculo);
        }
    }

    private void stopClick(View v) {
        mediaplayer.reset();
        estado = Estado.INACTIVO;
        almacenamientoCompartido.setVisibility(View.VISIBLE);
       url.setVisibility(View.VISIBLE);
        playPause.setVisibility(View.GONE);
        stop.setVisibility(View.GONE);
        playPause.setImageResource(R.drawable.pausecirculo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            uri = data.getData();
        }
    }
    private void setDataSource() {
        try {
           mediaplayer.setDataSource(this, uri);
            uri = null;
            mediaplayer.prepareAsync();
        } catch (IOException | IllegalArgumentException e) {
            Log.e(getResources().getString(R.string.app_name),
                    e.getLocalizedMessage());
        }
    }

    private void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {
        View videoLayout = findViewById(R.id.videoView);
        int maxWidth = videoLayout.getWidth();
        int maxHeight = videoLayout.getHeight();
        ViewGroup.LayoutParams surfaceParams = surface.getLayoutParams();
        surfaceParams.width = width;
        surfaceParams.height = (int) (((float) height / (float) width) * maxWidth);
        if (surfaceParams.height > maxHeight) {
            surfaceParams.height = maxHeight;
            surfaceParams.width = (int) (((float) width / (float) height) * maxHeight);
        }
        surface.setLayoutParams(surfaceParams);
    }
}
