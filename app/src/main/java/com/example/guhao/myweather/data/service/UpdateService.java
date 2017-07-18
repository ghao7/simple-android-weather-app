package com.example.guhao.myweather.data.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class UpdateService extends Service {
    private final String TAG = "";

    public UpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: service");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        WeatherConstant.updateRawWeather();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int aMinute = 60 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + aMinute;
        Intent i = new Intent(this, UpdateService.class);
        PendingIntent pi = PendingIntent.getService(this, 0 , i, 0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);

    }
}
