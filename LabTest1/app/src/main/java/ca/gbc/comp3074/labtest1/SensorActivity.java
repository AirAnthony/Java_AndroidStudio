package ca.gbc.comp3074.labtest1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private TextView temperatureView;
    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private Boolean isTempSensorAvailable;
    private TextView lightView;
    private Sensor lightSensor;
    private Boolean isLightSensorAvailable;

    private SensorEventListener sensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        temperatureView = findViewById(R.id.temperatureView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) !=null){
            temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTempSensorAvailable = true;
        }else{
            temperatureView.setText("Temperature Sensor Cannot be Detected");
            isTempSensorAvailable = false;
        }

        lightView = findViewById(R.id.lightView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) !=null){
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            isLightSensorAvailable = true;
        }else{
            lightView.setText("Light Sensor Cannot be Detected");
            isLightSensorAvailable = false;
        }

    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            temperatureView.setText("The current temperature is: "+sensorEvent.values[0]+" °C");
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT)
            lightView.setText("The current light is: "+sensorEvent.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isTempSensorAvailable){
            sensorManager.registerListener(this, temperatureSensor, sensorManager.SENSOR_DELAY_NORMAL);
        }
        if(isLightSensorAvailable){
            sensorManager.registerListener(this, lightSensor, sensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isTempSensorAvailable){
            sensorManager.unregisterListener(this);
        }
        if(isLightSensorAvailable){
            sensorManager.unregisterListener(this);
        }

    }

}