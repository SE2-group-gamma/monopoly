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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.R;
import com.example.monopoly.databinding.GameBoardBinding;
import com.example.monopoly.gamelogic.Dices;
import com.example.monopoly.ui.viewmodels.DiceViewModel;
import com.example.monopoly.network.Client;
import com.example.monopoly.network.ClientHandler;

import java.io.IOException;

public class GameBoardUI extends Fragment {

    private GameBoardBinding binding;
    private DiceViewModel diceViewModel;
    private Client client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diceViewModel = new ViewModelProvider(requireActivity()).get(DiceViewModel.class);
        diceViewModel.getDicesData().observe(this, dices -> {
            Log.i("Dices", dices.toString());
            //Msg an server mit augenzahl + welcher client
            HostGame.getMonopolyServer().getClients().get(0).writeToClient("GameBoardUI|move|"+(dices.getDice1()+dices.getDice2()));

        });
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Client.subscribe(this,"GameBoardUI");

        binding = GameBoardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        //Client.subscribe(this,"GameBoardUI");

        super.onViewCreated(view, savedInstanceState);
        //Log.d("Test",binding.button.getText().toString());
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // TODO: Set textView with key from Lobby
                //binding.textViewKey.setText("Game-Key: "+key);
            }
        });

        binding.backButton.setOnClickListener(view1 -> NavHostFragment.findNavController(GameBoardUI.this)
                .navigate(R.id.action_GameBoard_to_FirstFragment));

        binding.throwdice.setOnClickListener(view1 -> {
            showDiceFragment();
            /*for (ClientHandler handler: HostGame.getMonopolyServer().getClients()) {
                handler.writeToClient("GameBoardUI|gameStart| ");
            }*/
        });
    }

    private void showDiceFragment(){
        NavHostFragment.findNavController(this).navigate(R.id.action_GameBoardUI_to_DiceFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
