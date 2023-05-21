package com.example.monopoly.ui;


import android.content.Context;
import android.graphics.Color;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.net.nsd.NsdManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.R;
import com.example.monopoly.databinding.JoinGameBinding;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.os.Bundle;

import com.example.monopoly.R;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.monopoly.databinding.ActivityMainBinding;
import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.network.Client;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import java.io.IOException;

public class JoinGame extends Fragment {

    private JoinGameBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Client.subscribe(this,"JoinGame");
        binding = JoinGameBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.backButton.setOnClickListener(view1 -> NavHostFragment.findNavController(JoinGame.this)
                .navigate(R.id.action_JoinGame_to_FirstFragment));

        binding.joinButton.setOnClickListener(view12 -> {
            // TODO: join into Lobby (Client Side)
            String user = binding.userInput.getText().toString();
            String keyString = binding.keyInput.getText().toString();

            try {
                int key = Integer.parseInt(keyString);

                if(user.isEmpty() && key==0){
                    binding.userInput.setError("No Input");
                    binding.keyInput.setError("No Input");
                }else if(user.isEmpty()){
                    binding.userInput.setError("No Input");
                }else if(key==0){
                    binding.keyInput.setError("No Input");
                }else if(key<1000 || key>9999) {
                    binding.keyInput.setError("Not a valid Key");
                }else{
                    NsdManager manager = (NsdManager) getActivity().getSystemService(Context.NSD_SERVICE);
                    NSD_Client nsd = new NSD_Client();
                    nsd.setIsHost(false);
                    nsd.start(manager);
                    Player player = new Player(user, new Color(),500.00,true);

                    while(!nsd.isReady()){
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            throw new RuntimeException(e);
                        }
                    }

                    nsd.getClient().setUser(player);
                    nsd.getClient().setKey(key);
                }
            } catch (NumberFormatException e) {
                binding.keyInput.setError("Not a valid Key");
            }


        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}