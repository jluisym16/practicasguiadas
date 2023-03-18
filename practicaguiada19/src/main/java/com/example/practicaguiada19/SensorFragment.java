package com.example.practicaguiada19;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import java.util.List;

public class SensorFragment extends Fragment implements SensorEventListener {
    private static final String PARAM_POSICION_SENSOR = "posicion_sensor";
    private TextView sensorInfo;
    private Sensor sensor;
    private SensorManager manager;
    private ActionBar actionBar;

    public SensorFragment() {
        // Required empty public constructor
    }
    public static SensorFragment newInstance(int posicionSensor) {
        SensorFragment fragment = new SensorFragment();
        //Es un mapa  donde guardamos la posicion del sensor
        Bundle args = new Bundle();
        args.putInt(PARAM_POSICION_SENSOR, posicionSensor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            MainActivity activity = (MainActivity) getActivity();
            List<Sensor> sensores = activity.getSensores();
            int posicion = getArguments().getInt(PARAM_POSICION_SENSOR);
            sensor = sensores.get(posicion);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sensor, container, false);
        sensorInfo = v.findViewById(R.id.textViewInfoSensor);
        MainActivity activity = (MainActivity) getActivity();
        actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        manager = activity.getSensorManager();
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        return v;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        sensorInfo.setText(sensor.getName());
        sensorInfo.append("\n\n");
        for (int i=0; i<event.values.length; i++) {
            sensorInfo.append(String.valueOf(event.values[i]));
            sensorInfo.append("\n");
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        actionBar.setDisplayHomeAsUpEnabled(false);
        manager.unregisterListener(this);
    }
}

