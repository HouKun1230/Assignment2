package com.hou.kun.heartratedetection;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kun.hou.connectDB;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends WearableActivity implements SensorEventListener{

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.CANADA);

    private String url = "";
    private String user = "";
    private String pass="";

    private static final String TAG = "MainActivity";
    private TextView mTextViewHeart;
    SensorManager mSensorManager;
    Sensor mHeartRateSensor;
    SensorEventListener sensorEventListener;
    private ImageButton btnStart;
    private ImageButton btnPause;

    private BoxInsetLayout mContainerView;
    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mTextView = (TextView) findViewById(R.id.text);
        btnStart = (ImageButton) findViewById(R.id.start);
        btnPause = (ImageButton) findViewById(R.id.stop);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btnStart.setVisibility(ImageButton.GONE);
//                btnPause.setVisibility(ImageButton.VISIBLE);
                mTextView.setText("Please wait...");
                startMeasure();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btnPause.setVisibility(ImageButton.GONE);
//                btnStart.setVisibility(ImageButton.VISIBLE);
                mTextView.setText("--");
                stopMeasure();
            }
        });

        mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));

        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);



    }



    private void startMeasure() {
        mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void stopMeasure() {
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float mHeartRateFloat = event.values[0];

        int mHeartRate = Math.round(mHeartRateFloat);
        Toast.makeText(getApplicationContext(),
                String.valueOf(mHeartRate), Toast.LENGTH_LONG).show();
        connectDB cdb = new connectDB(MainActivity.this,user,pass);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        Toast.makeText(getApplicationContext(),
//                accuracy, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
