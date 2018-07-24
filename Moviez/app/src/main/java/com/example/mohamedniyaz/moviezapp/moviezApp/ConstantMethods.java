package com.example.mohamedniyaz.moviezapp.moviezApp;

import android.app.Application;
import android.util.Log;

public class ConstantMethods extends Application {

    public  final String LOG = "Moviez App Logs : ";
    public static ConstantMethods constantMethods = null;

    public static ConstantMethods newInstance(){
        if (constantMethods == null){
            return new ConstantMethods();
        }
        return constantMethods;
    }

    public void printLogs(String className, String log){
        Log.d(LOG, className + "::" + log);
    }
}
