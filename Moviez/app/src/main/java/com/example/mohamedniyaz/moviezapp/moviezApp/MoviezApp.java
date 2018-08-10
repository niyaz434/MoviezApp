package com.example.mohamedniyaz.moviezapp.moviezApp;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MoviezApp extends Application{
    private final String TAG = "MoviezApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(getApplicationContext());
    }
}
