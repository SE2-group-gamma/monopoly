package com.example.monopoly.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.R;
import com.example.monopoly.databinding.LobbyBinding;
import com.example.monopoly.gamelogic.Game;
import com.example.monopoly.network.Client;
import com.example.monopoly.network.ClientHandler;
import com.example.monopoly.network.MonopolyServer;
import com.example.monopoly.ui.viewmodels.ClientViewModel;

public class Lobby extends Fragment {

    private LobbyBinding binding;

    private int key;
    private String lobbyname;
    private Game game;
    private Client client;
    private ClientViewModel clientViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        clientViewModel = new ViewModelProvider(requireActivity()).get(ClientViewModel.class);
        Client.subscribe(this,"GameBoardUI");

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Client.subscribe(this,"Lobby");
        binding = LobbyBinding.inflate(inflater, container, false);
        this.client = clientViewModel.getClientData().getValue();       // set client
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                MonopolyServer x = HostGame.getMonopolyServer();
                // TODO: Set textView with key from Lobby
            }

        });

        binding.backButton.setOnClickListener(view1 -> {

            NavHostFragment.findNavController(Lobby.this)
                    .navigate(R.id.action_Lobby_to_FirstFragment);
        });

        binding.startButton.setOnClickListener(view12 -> {
            HostGame.getMonopolyServer().broadCast("Lobby|gameStart| ");
        });

        if(!client.isHost()){
            binding.startButton.setEnabled(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
