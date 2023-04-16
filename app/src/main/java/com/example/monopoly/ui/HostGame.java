package com.example.monopoly.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.example.monopoly.R;
import com.example.monopoly.databinding.HostGameBinding;
import com.example.monopoly.utils.LobbyKey;

import java.text.DecimalFormatSymbols;

public class HostGame extends Fragment {

    private HostGameBinding binding;



    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = HostGameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                binding.seekBar.setPadding(30,0,30,0);
                binding.seekBar2.setPadding(30,0,30,0);
                setSeekBar(binding.seekBar,2,false, binding.textViewSeekBar);
                setSeekBar(binding.seekBar2,2,false, binding.textViewSeekBar2);
            }
        });

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               setSeekBar(seekBar,progress,true,binding.textViewSeekBar);
           }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        binding.seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setSeekBar(seekBar,progress,true,binding.textViewSeekBar2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        binding.backButton.setOnClickListener(view1 -> NavHostFragment.findNavController(HostGame.this)
                .navigate(R.id.action_HostGame_to_FirstFragment));

        binding.createButton.setOnClickListener(view12 -> {
            String user = binding.userInput.getText().toString();
            String lobby = binding.lobbyInput.getText().toString();
            int playerCount = binding.seekBar.getProgress();
            int maxTimeMin = binding.seekBar2.getProgress();
            if(user.isEmpty() && lobby.isEmpty()){
                binding.userInput.setError("No Input");
                binding.lobbyInput.setError("No Input");
            }
            else if(user.isEmpty()){
                binding.userInput.setError("No Input");
            } else if (lobby.isEmpty()) {
                binding.lobbyInput.setError("No Input");
            } else {
                LobbyKey lobbyKey = new LobbyKey();
                int key = lobbyKey.generateKey();

                // TODO: create Lobby with key here (Server Side)

                NavHostFragment.findNavController(HostGame.this)
                        .navigate(R.id.action_HostGame_to_Lobby);
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setSeekBar(SeekBar seekBar, int progress, boolean fromUser, TextView textView){
        int width = seekBar.getWidth();
        if(!fromUser && seekBar.equals(binding.seekBar)){
            width += seekBar.getThumbOffset()*3;
        }else{
            width += seekBar.getThumbOffset();
        }

        if(!fromUser && seekBar.equals(binding.seekBar2)){
            width += seekBar.getThumbOffset()*6;
        }
        else {
            width -= seekBar.getThumbOffset();
        }
        int val = (progress * (width - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
        /*
        Log.d("seekbarWidth",""+width);
        Log.d("seekbarThumbOffset",""+seekBar.getThumbOffset());
        Log.d("seekbarMax",""+seekBar.getMax());
        Log.d("val",""+val);
        Log.d("getX",""+seekBar.getX());
        Log.d("setX",""+ (seekBar.getX() + val + seekBar.getThumbOffset() / 2));
        */
        if(seekBar.equals(binding.seekBar)){
            textView.setText("" + (progress+2));
        }else if(progress==10){
            textView.setText("" + DecimalFormatSymbols.getInstance().getInfinity());
        }else{
            textView.setText("" + ((progress*5)+10));
        }
        textView.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2.0f);
    }

}