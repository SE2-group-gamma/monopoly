package com.example.monopoly.ui;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.monopoly.R;
import com.example.monopoly.databinding.FragmentDiceBinding;
import com.example.monopoly.gamelogic.Dices;
import com.example.monopoly.network.Client;
import com.example.monopoly.ui.viewmodels.ClientViewModel;
import com.example.monopoly.ui.viewmodels.DiceViewModel;

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
    private static final float SHAKE_THRESHOLD = 10;
    private FragmentDiceBinding binding;
    private Dices dices;
    private boolean hasBeenRolled;
    private boolean userSetValue;
    private int flawedValue;
    private DiceViewModel diceViewModel;
    private ClientViewModel clientViewModel;


    public DiceFragment() {
        // Required empty public constructor
        //Client.subscribe(this,"DiceFragment");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DiceFragment.
     */

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
        this.hasBeenRolled = false;
        this.userSetValue = false;
        this.diceViewModel = new ViewModelProvider(requireActivity()).get(DiceViewModel.class);
        this.clientViewModel = new ViewModelProvider(requireActivity()).get(ClientViewModel.class);
        Client.subscribe(this,"DiceFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.binding = FragmentDiceBinding.inflate(getLayoutInflater());
        this.binding.sliderTooltip.setText(""+this.binding.numberSlider.getProgress()+2);
        this.binding.continueButtonDiceFragment.setOnClickListener(view -> {
            if(hasBeenRolled) {
                this.diceViewModel.setContinuePressed(true);
                this.diceViewModel.setDices(dices);
                this.clientViewModel.getClientData().getValue().getUser().setPosition((this.clientViewModel.getClientData().getValue().getUser().getPosition()+dices.getSum())%40);
                NavHostFragment.findNavController(this).navigate(R.id.action_DiceFragment_to_GameBoardUI);
            }
        });
        this.binding.numberSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                userSetValue = true;
                flawedValue = i+2;
                binding.sliderTooltip.setText(""+flawedValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return this.binding.getRoot();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() != Sensor.TYPE_ACCELEROMETER) return;

        if(this.hasBeenRolled) return;

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
                if(userSetValue){
                    dices.rollDicesFlawed(flawedValue);
                } else {
                    dices.rollDices();
                }
                setDiceImage(binding.imageDice1, dices.getDice1());
                setDiceImage(binding.imageDice2,dices.getDice2());

                this.hasBeenRolled = true;
            }

            lastX = x;
            lastY = y;
            lastZ = z;
        }
    }

    private void setDiceImage(ImageView diceImage, int number){
        switch (number) {
            case 1:
                diceImage.setImageResource(R.drawable.dice_1);
                break;
            case 2:
                diceImage.setImageResource(R.drawable.dice_2);
                break;
            case 3:
                diceImage.setImageResource(R.drawable.dice_3);
                break;
            case 4:
                diceImage.setImageResource(R.drawable.dice_4);
                break;
            case 5:
                diceImage.setImageResource(R.drawable.dice_5);
                break;
            case 6:
                diceImage.setImageResource(R.drawable.dice_6);
                break;
            default:
                diceImage.setImageResource(R.drawable.dice_0);
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