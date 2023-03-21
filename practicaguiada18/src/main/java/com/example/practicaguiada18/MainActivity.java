package com.example.practicaguiada18;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    private TextView info;
    private String[] acciones = {
            "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE", "POINTER DOWN", "POINTER UP"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = findViewById(R.id.textView);
        findViewById(R.id.imageView).setOnTouchListener(this);
        findViewById(R.id.button).setOnClickListener(
                v -> info.setText("TOCA LA IMAGEN")
        );
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        info.append(acciones[event.getAction() & MotionEvent.ACTION_MASK]);
        info.append("\n");
        for (int i=0; i<event.getPointerCount(); i++) {
            info.append(" puntero: ");
            info.append(String.valueOf(event.getPointerId(i)));
            info.append(" x:");
            info.append(String.valueOf(event.getX(i)));
            info.append(" y:");
            info.append(String.valueOf(event.getY(i)));
            info.append("\n");
        }
        info.append("\n\n");
        return true;
    }
}