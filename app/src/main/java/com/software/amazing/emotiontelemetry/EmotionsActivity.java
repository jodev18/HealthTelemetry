package com.software.amazing.emotiontelemetry;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmotionsActivity extends AppCompatActivity {

    @BindView(R.id.fab) FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotions);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initFab();

        getAllItems();
    }

    private void getAllItems(){

    }

    private void initFab(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(EmotionsActivity.this);

                ab.setTitle("Add Emotion");

                View vx = EmotionsActivity.this.getLayoutInflater()
                        .inflate(R.layout.dialog_input,null);

                EditText et = (EditText)vx.findViewById(R.id.etInputEmotion);

                ab.setView(vx);

                AlertDialog ad = ab.create();

                ad.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {


                    }
                });


            }
        });
    }

}
