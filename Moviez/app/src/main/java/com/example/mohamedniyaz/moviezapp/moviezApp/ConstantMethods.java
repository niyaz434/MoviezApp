package com.example.mohamedniyaz.moviezapp.moviezApp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.mohamedniyaz.moviezapp.repository.MovieRepository;
import com.example.mohamedniyaz.moviezapp.roomDatabase.MovieDatabase;


public class ConstantMethods {

    private final String TAG = "Moviez App Logs : ";
    private static ConstantMethods constantMethods = null;
    private Handler mHandler = null;

    public static ConstantMethods newInstance(){
        if (constantMethods == null){
            return new ConstantMethods();
        }
        return constantMethods;
    }

    public void printLogs(String className, String log){
        if (AppConstants.LOG) {
            Log.d(TAG, className + "::" + log);
        }
    }

    public Handler getHandler(){
        if (mHandler == null){
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

}
