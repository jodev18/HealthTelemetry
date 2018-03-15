package com.software.amazing.emotiontelemetry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TelemetryRecords extends AppCompatActivity {

    @BindView(R.id.lvDetectRecord) ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection_records);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        initFAB();
        initRecords();
    }

    private void initRecords(){

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(TelemetryRecords.this);

        String userName = sp.getString("LOGIN_ID","");

//        Snackbar.make(lv,userName,Snackbar.LENGTH_LONG).show();



    }

    private void initFAB(){

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabDetection);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goDetect = new Intent(getApplicationContext(),TelemetryMonitor.class);
                goDetect.putExtra("GOAL","RECORDING");
                startActivity(goDetect);
            }
        });
    }

}
