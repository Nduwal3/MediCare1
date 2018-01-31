package edu.sfsu.cs.orange.ocr;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import static android.R.attr.id;

/**
 * Created by DDELL on 7/28/2017.
 */

public class vibrator extends Service {

    MediaPlayer media_song;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // The service is starting, due to a call to startService()
        Log.i("LocalService","Received start id" + startId + ":" + intent);

        media_song = MediaPlayer.create(this, R.raw.beep);


        media_song.start();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }
}
