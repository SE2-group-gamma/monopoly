package com.example.monopoly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.SeekBar;
import android.widget.TextView;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.example.monopoly.databinding.HostGameBinding;

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
                setSeekBar(binding.seekBar2,5,false, binding.textViewSeekBar2);
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
            // TODO: create Lobby here
            String user = binding.userInput.getText().toString();
            String lobby = binding.lobbyInput.getText().toString();
            int playerCount = binding.seekBar.getProgress();
            int maxTimeMin = binding.seekBar2.getProgress();

            /*NavHostFragment.findNavController(HostGame.this)
                    .navigate(R.id.action_HostGame_to_FirstFragment);*/
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setSeekBar(SeekBar seekBar, int progress, boolean fromUser, TextView textView){
        int width = seekBar.getWidth();
        if(!fromUser){
            width += seekBar.getThumbOffset();
            //Log.d("bool","false");
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
        textView.setText("" + progress);
        textView.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
    }


}