package com.example.practicaguiada11;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class LlamadasReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String estado = extras.getString(TelephonyManager.EXTRA_STATE);
            if (estado != null && estado.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String numero = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Toast.makeText(context, "Llamada entrante de " + numero, Toast.LENGTH_LONG).show();
            }

        }
    }
}
