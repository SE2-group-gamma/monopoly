package com.example.monopoly.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.R;
import com.example.monopoly.databinding.LobbyBinding;
import com.example.monopoly.network.Client;
import com.example.monopoly.network.ClientHandler;
import com.example.monopoly.network.MonopolyServer;

public class Lobby extends Fragment {

    private LobbyBinding binding;

    private int key;
    private String lobbyname;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Client.subscribe(this,"Lobby");
        binding = LobbyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                MonopolyServer x = HostGame.getMonopolyServer();
                //Log.d("",""+x.getNumberOfClients());
                //Log.d("",""+x.getNumberOfClients());
                // TODO: Set textView with key from Lobby

            }

        });

        binding.backButton.setOnClickListener(view1 -> {

            NavHostFragment.findNavController(Lobby.this)
                    .navigate(R.id.action_Lobby_to_FirstFragment);
        });

        binding.startButton.setOnClickListener(view12 -> {
            // TODO: join into game
            for (ClientHandler handler: HostGame.getMonopolyServer().getClients()) {
                handler.writeToClient("Lobby|gameStart| ");
            }
            //HostGame.getMonopolyServer().getClients().get(0).getClientClient().handleMessage("Lobby|gameStart| ".split("\\|"));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
