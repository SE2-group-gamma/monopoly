package com.example.monopoly.ui;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monopoly.R;
import com.example.monopoly.databinding.FragmentDiceBinding;
import com.example.monopoly.gamelogic.Dices;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiceFragment extends Fragment implements SensorEventListener {

    private long lastSensorUpdate;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float lastX, lastY, lastZ;
    private static final float SHAKE_THRESHOLD = 50;
    private FragmentDiceBinding binding;
    private Dices dices;

    public DiceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiceFragment newInstance() {
        DiceFragment fragment = new DiceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.lastSensorUpdate = 0;
        this.lastX = -1f;
        this.lastY = -1f;
        this.lastZ = -1f;
        this.sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        this.accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.dices = new Dices();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.binding = FragmentDiceBinding.inflate(getLayoutInflater());
        return this.binding.getRoot();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() != Sensor.TYPE_ACCELEROMETER) return;

        long currentTime = System.currentTimeMillis();
        if((currentTime-lastSensorUpdate) > 1000) {

            long timeDifference = currentTime-lastSensorUpdate;
            lastSensorUpdate = currentTime;

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            float speed = (Math.abs(x+y+z -lastX-lastY-lastZ)/timeDifference) * 10000;

            if(speed > SHAKE_THRESHOLD){
                Log.i("SHAKE_DETECTION", "Shake detected!");
                dices.rollDices();
                // set dice images
            }

            lastX = x;
            lastY = y;
            lastZ = z;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}