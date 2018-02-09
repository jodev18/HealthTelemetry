package com.software.amazing.emotiontelemetry;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class EmotionTelemetrySplash extends AppCompatActivity {

    private Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_emotion_telemetry);

        h = new Handler(this.getMainLooper());

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(EmotionTelemetrySplash.this);

        Boolean permission = sp.getBoolean("has_permission",false);

        if(!permission){
            Dexter.withActivity(EmotionTelemetrySplash.this)
                    .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override public void onPermissionGranted(PermissionGrantedResponse response) {

                            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(EmotionTelemetrySplash.this);

                            SharedPreferences.Editor e = sp.edit();

                            e.putBoolean("has_permission",true);
                            e.commit();

                            startActivity(new Intent(getApplicationContext(),EmotionTelemetryMainActivity.class));
                            finish();
                        }
                        @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
                        @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                    }).check();
        }
        else{
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(),EmotionTelemetryMainActivity.class));
                    finish();
                }
            },1200);
        }



    }
}
