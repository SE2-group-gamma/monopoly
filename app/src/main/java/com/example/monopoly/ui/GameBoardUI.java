package com.example.monopoly.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.example.monopoly.databinding.GameBoardBinding;
import com.example.monopoly.gamelogic.Board;
import com.example.monopoly.gamelogic.properties.ClientPropertyStorage;
import com.example.monopoly.gamelogic.properties.Field;
import com.example.monopoly.gamelogic.properties.IllegalFieldException;
import com.example.monopoly.network.Client;
import com.example.monopoly.network.MonopolyServer;
import com.example.monopoly.ui.viewmodels.ClientViewModel;
import com.example.monopoly.ui.viewmodels.DiceViewModel;
import com.example.monopoly.ui.viewmodels.GameBoardUIViewModel;
import com.example.monopoly.ui.viewmodels.UIHandlerViewModel;

import java.io.IOException;

public class GameBoardUI extends Fragment {

    // reconstructions of GameBoardUI + UIHandler: turn swap; opening a different fragment and going back; end turn button

    private GameBoardBinding binding;
    private DiceViewModel diceViewModel;
    private ClientViewModel clientViewModel;
    private Client client;
    private GameBoardUIViewModel gameBoardUIViewModel;
    private ClientPropertyStorage clientPropertyStorage;

    private MonopolyServer monopoly = HostGame.getMonopolyServer();
    private NSD_Client nsdClient = new NSD_Client();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Client.subscribe(this,"GameBoardUI");
        diceViewModel = new ViewModelProvider(requireActivity()).get(DiceViewModel.class);
        clientViewModel = new ViewModelProvider(requireActivity()).get(ClientViewModel.class);
        diceViewModel.getDicesData().observe(this, dices -> {
            if(diceViewModel.getContinuePressedData().getValue()) {     // if fragment is exited upon clicking the continue button (not via command)
                String cheated = dices.isLastRollFlawed() == true ? "t" : "f";
                String doublets = (dices.getDice1() == dices.getDice2()) == true ? "t" : "f";       // 3 doubles in a row mean jail!!!
                try {
                    client.writeToServer("GameBoardUI|move|" + dices.getSum() + ":" + cheated + ":" + doublets + "|" + this.client.getUser().getUsername());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                diceViewModel.setContinuePressed(false);
            }
        });
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        this.client = clientViewModel.getClientData().getValue();       // set client

        try {
            this.client.writeToServer("GameBoardUI|initializePlayerBottomRight| : |" + this.client.getUser().getUsername());      // needs to be sent only once
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        binding = GameBoardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        this.clientPropertyStorage = ClientPropertyStorage.getInstance();
        super.onViewCreated(view, savedInstanceState);

        binding.uncover.setOnClickListener(view1 -> {
            try {
                this.client.writeToServer("GameBoardUI|uncover||"+this.client.getUser().getUsername());     // not data to transfer
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        binding.throwdice.setOnClickListener(view1 -> {
            showDiceFragment();
            clientViewModel.getClientData().getValue().getUser().setDrewCard(false);
        });

        binding.endTurn.setOnClickListener(view1 -> {
            try {
                this.client.writeToServer("GameBoardUI|turnEnd|:|");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            client.writeToServer("GameBoardUI|setPlayers|" + HostGame.getPlayerCount() + "|" + this.client.getUser().getUsername());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        binding.showPropertiesButton.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_GameBoardUI_to_ProperyCardFragment);
        });

        try{
            Field field = clientPropertyStorage.getProperty(Board.getFieldName(clientViewModel.getClientData().getValue().getUser().getPosition()));
            if(field.getOwner() != null || this.client.getUser().getCapital() < field.getPrice()) throw new IllegalFieldException();
            binding.buy.setAlpha(1f);
            binding.buy.setEnabled(true);
            binding.buy.setOnClickListener((viewX) -> {
                clientPropertyStorage.updateOwner(field.getName(), this.client.getUser());
                try {
                    client.writeToServer("GameBoardUI|buyField|" + field.getName() + "|" + this.client.getUser().getUsername());
                    client.writeToServer("GameBoardUI|giveMoney|" + (-field.getPrice()) + "|" + this.client.getUser().getUsername());
                    binding.buy.setAlpha(0.5f);
                    binding.buy.setEnabled(false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IllegalFieldException ie) {
            binding.buy.setAlpha(0.5f);
            binding.buy.setEnabled(false);
        }


        if(!clientViewModel.getClientData().getValue().getUser().getDrewCard()) {
            if (Board.getFieldName(clientViewModel.getClientData().getValue().getUser().getPosition()).equals("chance") ||
                    Board.getFieldName(clientViewModel.getClientData().getValue().getUser().getPosition()).equals("community")) {
                Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        NavHostFragment.findNavController(GameBoardUI.this).navigate(R.id.action_GameBoardUI_to_DrawCardFragment);
                    }
                };
                handler.postDelayed(runnable, 500);
                clientViewModel.getClientData().getValue().getUser().setDrewCard(true);
            }
        }
    }


    private void showDiceFragment(){
        NavHostFragment.findNavController(this).navigate(R.id.action_GameBoardUI_to_DiceFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;


    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        monopoly.closeClientConnection();
        if(HostGame.getMonopolyServer() != null){
            monopoly.closeConnectionsAndShutdown();
        }
        nsdClient.stopDiscovery();

    }
}
