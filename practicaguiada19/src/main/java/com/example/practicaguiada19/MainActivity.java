package com.example.practicaguiada19;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Collections;
import java.util.List;
/*
Los sensores se representan con la clase sensor
Se quiere crear una lista de sensores para poder mostrarlo en el ListView con un adaptador
Lo primero en el oncreate es obtener la lista de sensores
Se borra el adaptador para usarlo en el fragmento
 */
public class MainActivity extends AppCompatActivity {
    List<Sensor> sensores;
    SensorManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensores = manager.getSensorList(Sensor.TYPE_ALL);
    }

    public SensorManager getSensorManager() {
        return manager;
    }

    public List<Sensor> getSensores() {
        return Collections.unmodifiableList(sensores);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            FragmentManager manager = getSupportFragmentManager();
            if (manager.getBackStackEntryCount() > 0)
                manager.popBackStack();
            return true;
        }
        return false;
    }
}
