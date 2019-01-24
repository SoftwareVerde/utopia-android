package com.softwareverde.utopia;

import android.app.Application;

public class MainApplication extends Application {
    private static boolean _isDev;

    @Override
    public void onCreate() {
        super.onCreate();
        _isDev = !this.getString(R.string.environment).equals(getString(R.string.production));

        // Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
        //     @Override
        //     public void uncaughtException(Thread thread, Throwable ex) {
        //         System.exit(1);
        //     }
        // });
    }

    public static boolean isDev() {
        return _isDev;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}