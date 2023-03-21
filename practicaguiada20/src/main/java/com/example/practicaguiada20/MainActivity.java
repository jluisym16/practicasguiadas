package com.example.practicaguiada20;

import static com.example.practicaguiada20.R.id.direccionesNombre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends ListActivity implements LocationListener {
    private TextView coordenadas;
    private TextView direcciones;
    private LocationManager manager;
    private Geocoder geocoder;
    private AddressAdapter adapter;
    private boolean solicitandoPermisos;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordenadas = findViewById(R.id.textViewCoordenadas);
        direcciones = findViewById(direccionesNombre);
        adapter = new AddressAdapter(this, new ArrayList<>());
        setListAdapter(adapter);
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        geocoder = new Geocoder(this, Locale.getDefault());
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            solicitandoPermisos = true;
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!solicitandoPermisos)
            checkProvider();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions, grantResults);
        if (requestCode == 1)
            if (grantResults.length != 2 ||
                    grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                coordenadas.setText("Sin permiso para usar el GPS");
            } else
                checkProvider();
    }

    private void checkProvider() {
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            iniciarListener();
        else {
            new AlertDialog.Builder(this)
                    .setTitle("Ubicación deshabilitada")
                    .setMessage("¿Desea habilitar ubicación?")
                    .setPositiveButton("Aceptar", (iface, id) -> {
                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(i, 2);
                    })
                    .setNegativeButton("Cancelar", (iface, id) -> {
                        coordenadas.setText("Ubicación deshabilitada");
                    })
                    .show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            iniciarListener();
        else
            coordenadas.setText("Ubicación deshabilitada");
    }
    @SuppressLint("MissingPermission")
    private void iniciarListener() {
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null)
            onLocationChanged(location);
        else
            coordenadas.setText("Esperando coordenadas");
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        manager.removeUpdates(this);
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        double alt = location.getAltitude();
        coordenadas.setText("latitud: ");
        coordenadas.append(String.valueOf(lat));
        coordenadas.append("\n");
        coordenadas.append("longitud: ");
        coordenadas.append(String.valueOf(lon));
        coordenadas.append("\n");
        coordenadas.append("altitud: ");
        coordenadas.append(String.valueOf(alt));
        coordenadas.append("\n");
        new GeocoderTask().execute(lat, lon);
    }
    private class GeocoderTask extends AsyncTask<Double, Void, List<Address>> {
        @Override
        protected List<Address> doInBackground(Double... coord) {
            try {
                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                return geocoder.getFromLocation(coord[0], coord[1], 5);
            } catch (IOException e) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(List<Address> addresses) {
            super.onPostExecute(addresses);
            if (addresses == null || addresses.isEmpty())
                Toast.makeText(MainActivity.this, "Error obteniendo dirección: ",
                        Toast.LENGTH_LONG).show();
            else {
                direcciones.setText("Direcciones (");
                direcciones.append(String.valueOf(addresses.size()));
                direcciones.append(")");
                adapter.actualizar(addresses);
            }
        }
    }


}

