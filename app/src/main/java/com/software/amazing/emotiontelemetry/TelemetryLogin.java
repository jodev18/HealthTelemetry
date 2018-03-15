package com.software.amazing.emotiontelemetry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.software.amazing.emotiontelemetry.core.Globals;
import com.software.amazing.emotiontelemetry.db.LoginManager;
import com.software.amazing.emotiontelemetry.objects.LoginObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TelemetryLogin extends AppCompatActivity {

    @BindView(R.id.etLoginUser) EditText username;
    @BindView(R.id.etLoginPass) EditText pass;

    @BindView(R.id.btnLoginRespondent) Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_telemetry_main);
        setTitle("Login");

        ButterKnife.bind(this);

        initLogin();
    }

    private void initLogin(){

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userN = username.getText().toString();
                String userP =  pass.getText().toString();

                if(userN.length() >= 8){
                   if(userP.length() >= 8){
                       LoginManager lm = new LoginManager(TelemetryLogin.this,LoginManager.WRITE);

                       LoginObject loginObject = new LoginObject();
                       loginObject.USERNAME = userN;
                       loginObject.PASSWORD = userP;

                       String loginID = lm.doLogin(loginObject);

                       if(loginID.length() > 0){

                           SharedPreferences sp = PreferenceManager
                                   .getDefaultSharedPreferences(TelemetryLogin.this);

                           SharedPreferences.Editor e = sp.edit();

                           e.putString(Globals.PREF_KEY_LOGIN_ID,loginID);
                           e.commit();

                           startActivity(new Intent(getApplicationContext(),DetectionRecords.class));
                           finish();
                       }
                       else{
                           Snackbar.make(username,"Login failed.",Snackbar.LENGTH_LONG).show();
                       }
                   }
                   else{
                       Snackbar.make(username,"Password must be at " +
                               "least 8 characters in length.",Snackbar.LENGTH_LONG).show();
                   }
                }
                else{
                    Snackbar.make(username,"Username and password must be at " +
                            "least 8 characters in length.",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
