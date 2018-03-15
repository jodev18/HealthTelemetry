package com.software.amazing.emotiontelemetry;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.software.amazing.emotiontelemetry.db.LoginManager;
import com.software.amazing.emotiontelemetry.db.RespondentManager;
import com.software.amazing.emotiontelemetry.objects.LoginObject;
import com.software.amazing.emotiontelemetry.objects.Respondent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TelemetrySignup extends AppCompatActivity {

    @BindView(R.id.etName) EditText eName;
    @BindView(R.id.etAge) EditText eAge;
    @BindView(R.id.spGender) Spinner sGender;
    @BindView(R.id.etUser) EditText eUser;
    @BindView(R.id.etPass) EditText ePass;
    @BindView(R.id.etConfPass) EditText eCPass;

    @BindView(R.id.btnSaveRespondent) Button bSaveResp;

    String selectedGender;

    private Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respondent_registration);
        setTitle("Registration");


        ButterKnife.bind(this);

        h = new Handler(this.getMainLooper());

        initSpinner();
        saveButtonListener();

        Snackbar.make(bSaveResp,"Please complete all fields.",Snackbar.LENGTH_LONG).show();

    }

    private void saveButtonListener(){

        bSaveResp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = eName.getText().toString();
                String age = eAge.getText().toString();
                String gender = selectedGender;
                String user = eUser.getText().toString();
                String pass = ePass.getText().toString();

                //Validate first

                //At least a syllable
                if(name.length() >= 2){
                    if(age.length() > 0){
                        if(gender != null){
                            if(user.length() > 0){
                                if(pass.length() > 0){
                                    saveRespondent(age,name,gender,pass,user);
                                }
                                else{
                                    Snackbar.make(eName,"Please enter password.",Snackbar.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Snackbar.make(eName,"Please enter username.",Snackbar.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Snackbar.make(eName,"Please select gender.",Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Snackbar.make(eName,"Please enter age.",Snackbar.LENGTH_SHORT).show();
                    }
                }
                else{
                    Snackbar.make(eName,"Please enter name.",Snackbar.LENGTH_SHORT).show();
                }


                
            }
        });
    }

    private void saveRespondent(String age, String name, String gender, String pass, String user){

        RespondentManager rm
                = new RespondentManager(TelemetrySignup.this,RespondentManager.WRITE);

        Respondent rp = new Respondent();

        rp.AGE = age;
        rp.NAME = name;
        rp.GENDER = gender;

        rp.ID = Long.valueOf(rm.insertResponent(rp)).toString();

        LoginManager lm = new LoginManager(TelemetrySignup.this,LoginManager.WRITE);

        LoginObject loginObject = new LoginObject();
        loginObject.RESPONDENT_ID = rp.ID;
        loginObject.PASSWORD = pass;
        loginObject.USERNAME = user;

        long acctStat = lm.insertUser(loginObject);

        if(acctStat > 0){
            Snackbar.make(bSaveResp,"Successfully added user!",Snackbar.LENGTH_LONG).show();

            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },900);
        }
        else{
            Snackbar.make(bSaveResp,"Adding user failed!",Snackbar.LENGTH_LONG).show();

            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },900);
        }
    }

    private void initSpinner(){

        final String[] gender = {"MALE","FEMALE"};

        ArrayAdapter<String> arrDap = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line,gender);

        sGender.setAdapter(arrDap);

        sGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = gender[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedGender = gender[0];
            }
        });
    }
}
