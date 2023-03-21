package com.example.practicaguiada21;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class GrabarVideoActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private ImageButton grabar;
    private ImageButton stop;
    private ImageButton reproducir;
    private SurfaceView surface;
    private MediaRecorder recorder;
    private MediaPlayer player;
    private Uri outUri;
    private Uri videoUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabadora_video);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        grabar = findViewById(R.id.buttonGrabar);
        grabar.setOnClickListener(this::grabarClick);

        stop = findViewById(R.id.buttonStop);
        stop.setOnClickListener(this::pararClick);

        reproducir = findViewById(R.id.buttonReproducir);
        reproducir.setOnClickListener(this::reproducirClick);

        surface = findViewById(R.id.surfaceView);
        surface.getHolder().addCallback(this);

        recorder = new MediaRecorder();
        player = new MediaPlayer();
        player.setOnCompletionListener(this::onCompletion);
        player.setOnPreparedListener(this::onPrepared);
        player.setOnVideoSizeChangedListener(this::onVideoSizeChanged);

        ContentValues values = new ContentValues();
        outUri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
    private void grabarClick(View v) {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, 1);

}

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            videoUri = data.getData();
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                    grabar.getLayoutParams();
            params.weight = .5f;
            grabar.setLayoutParams(params);
            botones(false);
        }
    }
    private void pararClick(View v) {
        player.reset();
        botones(false);
    }
    private void reproducirClick(View v) {
        botones(true);
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(videoUri,
                    new String[]{ MediaStore.Images.Media.DATA },
                    null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            player.setDataSource(cursor.getString(column_index));
            player.prepareAsync();
        } catch (IOException e) {
            Log.e(getLocalClassName(), e.getLocalizedMessage());
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }
    private void botones(boolean stopVisible) {
        grabar.setVisibility(stopVisible ? View.GONE : View.VISIBLE);
        stop.setVisibility(stopVisible ? View.VISIBLE : View.GONE);
        reproducir.setVisibility(stopVisible ? View.GONE : View.VISIBLE);
    }
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        player.setDisplay(holder);
    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
    }
    private void onPrepared(MediaPlayer player) {
        player.start();
    }
    private void onCompletion(MediaPlayer player) {
        player.reset();
        botones(false);
    }
    private void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        View videoLayout = findViewById(R.id.videoLayout);
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