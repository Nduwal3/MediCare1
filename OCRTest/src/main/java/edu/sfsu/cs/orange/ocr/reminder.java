package edu.sfsu.cs.orange.ocr;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import static edu.sfsu.cs.orange.ocr.R.id.unset_alarm;

public class reminder extends AppCompatActivity {
    AlarmManager alarmManager;
    Button set_alarm;
    Button unset_alarm;
    TextView message;
    TimePicker alarm_TimePicker;
    Context context;
    PendingIntent pending_intent;


    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        this.context = this;
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        alarm_TimePicker = (TimePicker)findViewById(R.id.timePicker);
        message = (TextView)findViewById(R.id.message);

        final Calendar calendar = Calendar.getInstance();

        final Intent intent = new Intent ( this.context , Alarm_receiver.class);

        set_alarm= (Button)findViewById(R.id.set_alarm);
        set_alarm.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                //Setting calendar instance with the hour and minute  we picked
                calendar.set(Calendar.HOUR_OF_DAY,alarm_TimePicker.getHour());
                calendar.set(Calendar.MINUTE,alarm_TimePicker.getMinute());
                int hour = alarm_TimePicker.getHour();
                int minute= alarm_TimePicker.getMinute();

                //convert int to string
                String hourString = String.valueOf(hour);
                String minString = String.valueOf(minute);

                if(hour>12){
                    hourString = String.valueOf(hour - 12);
                }
                if(minute < 10){
                    minString = "0" + String.valueOf(minute);
                }

                set_alarm_text("Alarm on at "+ hourString + ":"+ minString +"!");

                //pendingIntent delays the intent untl the specified time
                pending_intent = PendingIntent.getBroadcast(getApplicationContext() , 0 ,intent , PendingIntent.FLAG_UPDATE_CURRENT);

                //set alarmManager
               // alarmManager.set(AlarmManager.RTC_WAKEUP , System.currentTimeMillis(),pending_intent);

               alarmManager.set(AlarmManager.RTC_WAKEUP , calendar.getTimeInMillis(),pending_intent);

            }


        });
        unset_alarm = (Button)findViewById(R.id.unset_alarm);
        unset_alarm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                set_alarm_text("Alarm off!");
                alarmManager.cancel(pending_intent);

            }
        });

    }

    private void set_alarm_text(String output) {

            message.setText(output);


    }
}
