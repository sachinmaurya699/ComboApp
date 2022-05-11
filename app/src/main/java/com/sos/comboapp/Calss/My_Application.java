package com.sos.comboapp.Calss;

import android.app.Application;

import com.onesignal.OneSignal;

public class My_Application extends Application
{
    private static final String ONESIGNAL_APP_ID = "4e2ad8e3-3c68-48bd-a09a-941a5f0deed4";

    @Override
    public void onCreate()
    {
        super.onCreate();
        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);


    }
}
