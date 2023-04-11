package com.example.monopoly;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.databinding.JoinGameBinding;
import com.example.monopoly.databinding.LobbyBinding;

public class Lobby extends Fragment {

    private LobbyBinding binding;

    private int key;
    private String lobbyname;
/*
    public Lobby(int key, String lobbyname) {
        this.key = key;
        this.lobbyname = lobbyname;
    }
*/
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = LobbyBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //binding.textViewKey.setText("Game-Key: "+key);
            }
        });

        binding.backButton.setOnClickListener(view1 -> NavHostFragment.findNavController(Lobby.this)
                .navigate(R.id.action_Lobby_to_FirstFragment));

        binding.startButton.setOnClickListener(view12 -> {
            // TODO: join into game

            /*NavHostFragment.findNavController(Lobby.this)
                    .navigate(R.id.action_JoinGame_to_FirstFragment);*/
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
