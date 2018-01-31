package edu.sfsu.cs.orange.ocr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by DDELL on 7/28/2017.
 */

public class Alarm_receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e ("we are in the receiver","yay!");

        Intent service_intent = new Intent(context , vibrator.class);
        context.startService(service_intent);
       // vibrator v = (vibrator) context.getSystemService( context.VIBRATOR_SERVICE);
        //v.vibrate(5000);
    }
}