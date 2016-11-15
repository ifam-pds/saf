package br.edu.ifam.saf;


import android.app.Application;
import android.content.Context;

import br.edu.ifam.saf.data.LocalRepository;
import br.edu.ifam.saf.data.LocalRepositoryImpl;

public class MainApplication extends Application {


    private static MainApplication instance;

    public static Context getAppContext() {
        return instance;
    }

    public static LocalRepository getRepository() {
        return LocalRepositoryImpl.getInstance();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MainApplication.instance = this;

    }
}