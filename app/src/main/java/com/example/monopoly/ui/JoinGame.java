package com.example.monopoly.ui;


import android.content.Context;
import android.graphics.Color;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.R;
import com.example.monopoly.databinding.JoinGameBinding;
import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.network.Client;

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

//System.out.println("DOUBLE ERROR");
            // TODO: join into Lobby (Client Side)
            String user = binding.userInput.getText().toString();
            String key = binding.keyInput.getText().toString();

            if(user.isEmpty() && key.isEmpty()){
                binding.userInput.setError("No Input");
                binding.keyInput.setError("No Input");
            }else if(user.isEmpty()){
                binding.userInput.setError("No Input");
            }else if(key.isEmpty()){
                binding.keyInput.setError("No Input");
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
                nsd.getClient().setKey(Integer.parseInt(key));

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}