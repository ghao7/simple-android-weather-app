package com.example.guhao.myweather.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Timer;

public class UpdateService extends Service {
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
        Timer timer = new Timer();
        timer.schedule(new Update(),0 , 3000);
    }

}
