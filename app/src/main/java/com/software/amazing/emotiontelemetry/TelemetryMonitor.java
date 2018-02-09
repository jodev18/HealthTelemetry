package com.software.amazing.emotiontelemetry;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.software.amazing.emotiontelemetry.bluetooth.BlunoLibrary;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.itangqi.waveloadingview.WaveLoadingView;

public class TelemetryMonitor extends BlunoLibrary {

    private Handler h;
    private StringBuilder sb;
    private ProgressDialog prg;

    private AlertDialog notify;

    public static final String DEVICE_MAC = "20:16:07:26:17:50";

    @BindView(R.id.graph1) GraphView gv1;
    @BindView(R.id.graph2) GraphView gv2;
    @BindView(R.id.graph3) GraphView gv3;

    @BindView(R.id.tvAveEnvTemp) TextView tvAETemp;
    @BindView(R.id.tvAveSkinTemp) TextView tvASTemp;
    @BindView(R.id.tvAverageBPM) TextView tvABPM;

    @BindView(R.id.tvCurrentBPM) TextView tvCurrBPM;
    @BindView(R.id.tvCurrEnvTemp) TextView tvCurrEnvTemp;
    @BindView(R.id.tvCurrSkinTemp) TextView tvCurrSkinTemp;

    @BindView(R.id.waveEmotion) WaveLoadingView emotion;

    int secondsWait = 20;

    private Integer x1 = 3, x2 = 3, x3 = 3;

    private LineGraphSeries<DataPoint> lSeries1;
    private LineGraphSeries<DataPoint> lSeries2;
    private LineGraphSeries<DataPoint> lSeries3;

    private List<Double> BPM_LIST = new ArrayList<>();
    private List<Double> ENV_TEMP = new ArrayList<>();
    private List<Double> SKIN_TEMP = new ArrayList<>();

    //FUTURE
    private List<Double> CONDUCTIVITY_VARS = new ArrayList<>();

    int buffCount = 0;
    StringBuilder bfff = new StringBuilder();

    private String selectedGender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telemetry_monitor);
        setTitle("Emotion Telemetry");

        onCreateProcess();														//onCreate Process by BlunoLibrary


        serialBegin(115200);

        ButterKnife.bind(this);

        initialCheck();
    }

    private void initialCheck(){
        //showSelectGenderDialog();
        begin();
        buttonScanOnClickProcess();
    }

    private void initializeGraphViews(){

        gv1.setTitle("Pulse Rate");
        gv1.setTitleTextSize(40.0f);
        gv1.addSeries(lSeries1);

        gv2.setTitle("Env. Temperature");
        gv2.setTitleTextSize(40.0f);
        gv2.addSeries(lSeries2);

        gv3.setTitle("Skin. Temperature");
        gv3.setTitleTextSize(40.0f);
        gv3.addSeries(lSeries3);
    }

    private void begin(){

        h = new Handler(getMainLooper());
        sb = new StringBuilder();
        prg = new ProgressDialog(TelemetryMonitor.this);
        lSeries1 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 0),
                new DataPoint(1, 0),
                new DataPoint(2, 0)
        });

        lSeries2 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 0),
                new DataPoint(1, 0),
                new DataPoint(2, 0)
        });
        lSeries3 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 0),
                new DataPoint(1, 0),
                new DataPoint(2, 0)
        });

        enableBluetooth();
//        initializeBluetooth();
        initializeGraphViews();

    }

    private void showSelectGenderDialog(){

        final AlertDialog.Builder selGender = new AlertDialog.Builder(TelemetryMonitor.this);
        selGender.setTitle("Select Gender");

        final String[] gender = {"Male","Female"};

        selGender.setSingleChoiceItems(gender, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selectedGender = gender[i];

//                Toasty.info(TelemetryMonitor.this,"Selected gender: " + selectedGender,Toast.LENGTH_LONG).show();
                dialogInterface.dismiss();
            }
        });

        selGender.setCancelable(false);
        selGender.create().show();

    }

    private void enableBluetooth(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();

        if(!isEnabled){
            bluetoothAdapter.enable();
        }
    }

    private void tempHandleData(String data){

        if(!data.contains("N")){
            if(notify != null){
                if(notify.isShowing()){
                    notify.dismiss();
                }
            }
            if(buffCount < 5){
                bfff.append(data);
                buffCount++;
            }
            else{
                buffCount = 0;
                processInput(bfff.toString());
                bfff = new StringBuilder();
            }
        }
        else{
            if(notify == null){
                AlertDialog.Builder notif = new AlertDialog.Builder(TelemetryMonitor.this);
                notif.setTitle("Sensor removed");
                notif.setMessage("Please keep the sensor intact to continue sensing.");
                notif.setCancelable(false);
                notify = notif.create();


            }
            notify.show();
        }

    }


    private void processInput(String data){

        String[] currentData = data.split(",");

        int csize = currentData.length;

        boolean shouldAddToDataset = true;

        Double pulse_stat = 0d;
        Double env_stat = 0d;
        Double body_stat = 0d;

        for(int i=0;i<csize;i++){

            String[] labvals = currentData[i].split(":");

            if(labvals.length == 2){
                Log.d("DataProcess2", labvals[0] + ":" + labvals[1]);

                if(labvals[0].equals("B")){

                    if(labvals[1].trim().length() > 1){

                        Double BPMRate = Double.parseDouble(labvals[1].trim());

                        if(BPMRate < 165d){
                            BPM_LIST.add(BPMRate);
                            gv1.setTitle("Pulse Rate");
                            tvABPM.setText("Average BPM: " + round(getAverage(BPM_LIST),4).toString());
                            tvCurrBPM.setText("Current BPM: " + BPMRate.toString());

                            pulse_stat = BPMRate - 40;

                            lSeries1.appendData(new DataPoint(x1,Double.parseDouble(labvals[1].trim())),false,50);
                            x1++;

                        }
                        else{
                            pulse_stat = 0d;

                            gv1.setTitle("No finger detected--Pulse");
                            tvABPM.setText("Please place finger on the pulse sensor.");
                            tvCurrBPM.setText("Please place finger on the pulse sensor.");
                        }
                    }
                    else{
                        pulse_stat = 0d;

                        BPM_LIST.add(0d);
                        //lSeries1.appendData(new DataPoint(x1,0),false,50);
                        //x1++;
                    }


                }
                else if(labvals[0].equals("E")){

                    if(labvals[1].trim().length() > 1){

                        Double EnvTemp = Double.parseDouble(labvals[1].trim());

                        ENV_TEMP.add(EnvTemp);
                        tvCurrEnvTemp.setText("Current Environmental Temp: " + EnvTemp.toString());
                        tvAETemp.setText("Average Environmental Temp: " + round(getAverage(ENV_TEMP),4).toString()  + " °C");
                        lSeries2.appendData(new DataPoint(x2,EnvTemp),false,50);
                        x2++;

                        env_stat = EnvTemp;

                    }
                    else{
                        env_stat = 0d;
                        ENV_TEMP.add(0d);
                        //lSeries2.appendData(new DataPoint(x2,0),false,50);
                        //x2++;
                    }
                }
                else if(labvals[0].equals("T")){


                    if(labvals[1].trim().length() > 1){

                        Double currSkinTemp = Double.parseDouble(labvals[1].trim());

                        SKIN_TEMP.add(currSkinTemp);
                        tvCurrSkinTemp.setText("Current Skin Temp: " +currSkinTemp.toString());
                        tvASTemp.setText("Average Skin Temp: " + round(getAverage(SKIN_TEMP),4).toString() + " °C");
                        lSeries3.appendData(new DataPoint(x3,Double.parseDouble(labvals[1].trim())),false,50);
                        x3++;

                        body_stat = (currSkinTemp - 28) * 6;


                    }
                    else{
                        body_stat = 0d;
                        SKIN_TEMP.add(0d);
                        //lSeries3.appendData(new DataPoint(x3,0),false,50);
                        //x3++;
                    }
                }

                Double progVal = (body_stat + pulse_stat) - (env_stat/ 2) ;

                Log.d("STAT",progVal.toString());

                emotion.setProgressValue(progVal.intValue());

                if(progVal.intValue() >= 50 && progVal.intValue() <= 60){
                    emotion.setTopTitle("Emotion Detected");
                    emotion.setCenterTitle("Neutral");
                }
                else if(progVal.intValue() >= 60 && progVal.intValue() <= 100){
                    emotion.setTopTitle("Emotion Detected");
                    emotion.setCenterTitle("Excited.");
                }
                else if(progVal.intValue() <= 45 && progVal.intValue() >= 35){
                    //.setTopTitle(".");
                    emotion.setTopTitle("Emotion Detected");
                    emotion.setCenterTitle("Depressed.");
                }
                else{
                    if(progVal > 100){
                        emotion.setTopTitle("--");
                        emotion.setCenterTitle("--");
                    }
                    else{
                        emotion.setTopTitle("Insufficient Data.");
                        emotion.setCenterTitle("--");
                    }
                }


            }

        }
    }

    private Double getAverage(List<Double> values){

        Double totVals = 0d;

        for(int i=0;i<values.size();i++){
            totVals += values.get(i);
        }

        return (totVals / values.size());
    }

    private Double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    protected void onResume(){
        super.onResume();
        System.out.println("BlUNOActivity onResume");
        onResumeProcess();														//onResume Process by BlunoLibrary
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResultProcess(requestCode, resultCode, data);					//onActivityResult Process by BlunoLibrary
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        onPauseProcess();														//onPause Process by BlunoLibrary
    }

    protected void onStop() {
        super.onStop();
        onStopProcess();														//onStop Process by BlunoLibrary
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyProcess();														//onDestroy Process by BlunoLibrary
    }

    @Override
    public void onConectionStateChange(connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
        switch (theConnectionState) {											//Four connection state
            case isConnected:
//                buttonScan.setText("Connected");
                break;
            case isConnecting:
//                buttonScan.setText("Connecting");
                break;
            case isToScan:
//                buttonScan.setText("Scan");
                break;
            case isScanning:
//                buttonScan.setText("Scanning");
                break;
            case isDisconnecting:
//                buttonScan.setText("isDisconnecting");
                break;
            default:
                break;
        }
    }

    @Override
    public void onSerialReceived(String theString) {							//Once connection data received, this function will be called
        // TODO Auto-generated method stub
        tempHandleData(theString);						//append the text into the EditText
        //The Serial data from the BLUNO may be sub-packaged, so using a buffer to hold the String is a good choice.
    }

//    @Override
//    public void onDestroy(){
//        super.onDestroy();
////        simpleBluetooth.endSimpleBluetooth();
//    }
}
