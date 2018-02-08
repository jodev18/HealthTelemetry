package com.software.amazing.emotiontelemetry.core;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by myxroft on 07/10/2017.
 */

public class EmotionTelemetryApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
