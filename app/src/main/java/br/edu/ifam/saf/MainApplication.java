package br.edu.ifam.saf;


import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {


    private static MainApplication instance;

    public static Context getAppContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MainApplication.instance = this;

    }
}