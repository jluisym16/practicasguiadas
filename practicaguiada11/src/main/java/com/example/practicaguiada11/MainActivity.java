package com.example.practicaguiada11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> solicitudes = new ArrayList<>();
        check(Manifest.permission.READ_PHONE_STATE, solicitudes);
        check(Manifest.permission.RECEIVE_SMS, solicitudes);
        if (!solicitudes.isEmpty())
            ActivityCompat.requestPermissions(this,
                    solicitudes.toArray(new String[0]), 0);
        // IntentFilter filter = new IntentFilter();
        // filter.addAction("android.intent.action.PHONE_STATE");
        // registerReceiver(new LlamadaReceiver(), filter);
        TelephonyManager gestor = (TelephonyManager)
                getSystemService(Activity.TELEPHONY_SERVICE);
        String operador = gestor.getNetworkOperatorName();
        Toast.makeText(this, "Operador de telefonía: " + operador,
                Toast.LENGTH_LONG).show();
    }
    protected void check(String permiso, List<String> solicitudes) {
        if (ContextCompat.checkSelfPermission(this, permiso) != PackageManager.PERMISSION_GRANTED) solicitudes.add(permiso);
    }
}