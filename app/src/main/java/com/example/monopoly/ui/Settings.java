package com.example.monopoly.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.R;
import com.example.monopoly.databinding.SettingsBinding;

public class Settings extends Fragment {

    private SettingsBinding binding;
    private MediaPlayer song;
    private Intent musicService;
    boolean switchCheck;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = SettingsBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        Switch soundMusic = rootView.findViewById(R.id.switch1);
        //View game = inflater.inflate(R.layout.game_board,container,false);
        //Switch soundMusic2 = game.findViewById(R.id.switchsound);
        soundMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                playSong();
            } else {
                pauseSong();
            }

        });

        return rootView;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.backButton.setOnClickListener(view1 -> NavHostFragment.findNavController(Settings.this)
                .navigate(R.id.action_Settings_to_FirstFragment));
        binding.saveButton.setOnClickListener(view12 -> {
            SharedPreferences sharedSets = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedSets.edit();
            edit.putBoolean("sound",binding.switch1.isChecked());
            edit.putBoolean("placeholder",binding.switch2.isChecked());
            edit.apply();

            NavHostFragment.findNavController(Settings.this)
                    .navigate(R.id.action_Settings_to_FirstFragment);
        });
        song = MediaPlayer.create(getActivity(),R.raw.monopoly_song);

    }

    public void pauseSong(){
        if(musicService !=null){
            getActivity().stopService(musicService);
            musicService=null;
        }
    }

    public void playSong(){
        if(musicService==null){
            musicService = new Intent(getActivity(), MusicPlayer.class);
            getActivity().startService(musicService);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        musicService=null;
    }

}
