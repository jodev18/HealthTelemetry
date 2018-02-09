package com.software.amazing.emotiontelemetry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddRespondent extends AppCompatActivity {

    @BindView(R.id.etName) EditText eName;
    @BindView(R.id.etAge) EditText eAge;
    @BindView(R.id.spGender) Spinner sGender;
    @BindView(R.id.etUser) EditText eUser;
    @BindView(R.id.etPass) EditText ePass;
    @BindView(R.id.etConfPass) EditText eCPass;

    @BindView(R.id.btnSaveRespondent) Button bSaveResp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_respondent);

        ButterKnife.bind(this);

        initSpinner();
        saveButtonListener();

    }

    private void saveButtonListener(){
        bSaveResp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initSpinner(){

        String[] gender = {"MALE","FEMALE"};

        ArrayAdapter<String> arrDap = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line,gender);

        sGender.setAdapter(arrDap);

        sGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
