package com.example.mohamedniyaz.moviezapp.moviezApp;

import android.util.Log;

public class ConstantMethods {

    private   final String LOG = "Moviez App Logs : ";
    private static ConstantMethods constantMethods = null;

    public static ConstantMethods newInstance(){
        if (constantMethods == null){
            return new ConstantMethods();
        }
        return constantMethods;
    }

    public void printLogs(String className, String log){
        if (AppConstants.LOG) {
            Log.d(LOG, className + "::" + log);
        }
    }
}
