package com.example.practicaguiada11;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            byte[][] pdus = (byte[][]) extras.get("pdus");
            for (byte[] pdu : pdus) {
                SmsMessage msg = SmsMessage.createFromPdu(pdu);
                String phone = msg.getDisplayOriginatingAddress();
                String cuerpo = msg.getDisplayMessageBody();

            }
        }
    }
    }
