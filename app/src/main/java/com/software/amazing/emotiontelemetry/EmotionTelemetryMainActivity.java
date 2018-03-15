package com.software.amazing.emotiontelemetry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.software.amazing.emotiontelemetry.core.Globals;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmotionTelemetryMainActivity extends AppCompatActivity {

    @BindView(R.id.btnLearn) Button bLearn;
    @BindView(R.id.btnRecognize) Button bRecog;
    @BindView(R.id.btnRegister) Button bReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_respondent_login);

        ButterKnife.bind(this);

        initButtonListeners();

    }

    private void initButtonListeners(){

        bLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLearn();
            }
        });

        bRecog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRecognition();
            }
        });

        bReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegistration();
            }
        });
    }

    private void goToLearn(){
        startActivity(new Intent(getApplicationContext(),TelemetryLogin.class));
    }

    private void goToRecognition(){

        Intent goRecog = new Intent(getApplicationContext(),TelemetryMonitor.class);

        goRecog.putExtra(Globals.INTENT_GOAL_KEY,Globals.GOAL_RECOGNIZE);

        startActivity(goRecog);
    }

    private void goToRegistration(){
        startActivity(new Intent(getApplicationContext(),TelemetrySignup.class));
    }
}
