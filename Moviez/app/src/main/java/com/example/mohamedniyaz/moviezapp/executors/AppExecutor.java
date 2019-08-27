package com.example.mohamedniyaz.moviezapp.executors;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {

    private Executor appExecutor;

    public AppExecutor(Executor executor) {
        this.appExecutor = executor;
    }

    public AppExecutor() {
        this(Executors.newSingleThreadExecutor());
    }

    public Executor getAppExecutor() {
        return appExecutor;
    }
}
