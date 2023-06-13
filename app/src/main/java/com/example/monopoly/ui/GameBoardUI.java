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
import com.example.monopoly.databinding.GameBoardBinding;
import com.example.monopoly.gamelogic.Board;
import com.example.monopoly.gamelogic.properties.ClientPropertyStorage;
import com.example.monopoly.gamelogic.properties.Field;
import com.example.monopoly.gamelogic.properties.IllegalFieldException;
import com.example.monopoly.network.Client;
import com.example.monopoly.network.ClientHandler;
import com.example.monopoly.network.MonopolyServer;
import com.example.monopoly.ui.viewmodels.ClientViewModel;
import com.example.monopoly.ui.viewmodels.DiceViewModel;
import com.example.monopoly.ui.viewmodels.GameBoardUIViewModel;
import com.example.monopoly.ui.viewmodels.UIHandlerViewModel;

import java.io.IOException;
import java.net.Socket;

public class GameBoardUI extends Fragment {

    // reconstructions of GameBoardUI + UIHandler: turn swap; opening a different fragment and going back; end turn button

    private GameBoardBinding binding;
    private DiceViewModel diceViewModel;
    private ClientViewModel clientViewModel;
    private Client client;
    private GameBoardUIViewModel gameBoardUIViewModel;
    private ClientPropertyStorage clientPropertyStorage;

    private NSD_Client nsdClient;
    private MonopolyServer monopoly;
    private ClientHandler clientHandler;
    private Socket socket;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Client.subscribe(this,"GameBoardUI");
        diceViewModel = new ViewModelProvider(requireActivity()).get(DiceViewModel.class);
        clientViewModel = new ViewModelProvider(requireActivity()).get(ClientViewModel.class);
        diceViewModel.getDicesData().observe(this, dices -> {
            if(diceViewModel.getContinuePressedData().getValue()) {     // if fragment is exited upon clicking the continue button (not via command)
                Log.i("Dices", dices.toString());
                String cheated = dices.isLastRollFlawed() == true ? "t" : "f";
                String doublets = (dices.getDice1() == dices.getDice2()) == true ? "t" : "f";       // 3 doubles in a row mean jail!!!
                //String passedStartField = dices.isLastRollFlawed()==true?"t":"f";
                try {
                    client.writeToServer("GameBoardUI|move|" + dices.getSum() + ":" + cheated + ":" + doublets + "|" + this.client.getUser().getUsername());
                    Log.d("gameboardBuy", "After dice: " + Board.getFieldName(this.client.getUser().getPosition()));
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


        Log.d("MSG", "OnCreateView");

        this.client = clientViewModel.getClientData().getValue();       // set client


        //if(this.client.isHost()) {
        try {
            this.client.writeToServer("GameBoardUI|initializePlayerBottomRight| : |" + this.client.getUser().getUsername());      // needs to be sent only once
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        binding = GameBoardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        Log.d("MSG", "OnViewCreated");
        this.clientPropertyStorage = ClientPropertyStorage.getInstance();

        //}

        // DisplayMetrics might still be useful, so keep them for now
/*
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getRealMetrics(displayMetrics);
        double height = displayMetrics.heightPixels;
        double width = displayMetrics.widthPixels;

        // Tried relative calculation with dp
        //double heightRatio = (double) height / 411.4285583496094;
        //double widthRatio = (double) width / 891.4285888671875;

        //double heightRatio = (double) height / 1440;
        //double widthRatio = (double) width / 3120;
        //heightRatio = heightRatio * (3.5/displayMetrics.density);
        //widthRatio = widthRatio * (3.5/displayMetrics.density);

        double heightRatio = layerDrawable.getMinimumHeight()/(double)21000;
        double widthRatio = layerDrawable.getMinimumWidth()/(double)21000;
*/


        super.onViewCreated(view, savedInstanceState);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

            }
        });
        UIHandlerViewModel uiHandlerViewModel = (new ViewModelProvider(requireActivity())).get(UIHandlerViewModel.class);

        //binding.currentMoney.setText("Current Money \n"+uiHandlerViewModel.getCurrentMoney().getValue()+"$"); // dont redraw

        binding.backButton.setOnClickListener(view1 -> NavHostFragment.findNavController(GameBoardUI.this)
                .navigate(R.id.action_GameBoard_to_FirstFragment));

        binding.uncover.setOnClickListener(view1 -> {
            try {
                this.client.writeToServer("GameBoardUI|uncover||"+this.client.getUser().getUsername());     // not data to transfer
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        binding.throwdice.setOnClickListener(view1 -> {
            showDiceFragment();
        });

        binding.endTurn.setOnClickListener(view1 -> {
            /*this.client = clientViewModel.getClientData().getValue();
            this.client.endTurnPressed();*/

            Log.d("endTurn",clientViewModel.getClientData().getValue().getUser().getUsername());
            //clientViewModel.getClientData().getValue().endTurnPressed();
            //clientViewModel.getClientData().getValue().endTurnPressedBroadCast();
            try {
                this.client.writeToServer("GameBoardUI|turnEnd|:|");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        binding.showPropertiesButton.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_GameBoardUI_to_ProperyCardFragment);
        });

        try{
            Log.d("gameboardBuy", Board.getFieldName(clientViewModel.getClientData().getValue().getUser().getPosition()));
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

        binding.pay.setOnClickListener(view1 -> {
                NavHostFragment.findNavController(this).navigate(R.id.action_GameBoardUI_to_DrawCardFragment);
        });

        if (Board.getFieldName(clientViewModel.getClientData().getValue().getUser().getPosition()).equals("chance") ||
                Board.getFieldName(clientViewModel.getClientData().getValue().getUser().getPosition()).equals("community")) {

            NavHostFragment.findNavController(GameBoardUI.this).navigate(R.id.action_GameBoardUI_to_DrawCardFragment);

        }
    }

    private void showDiceFragment(){
        NavHostFragment.findNavController(this).navigate(R.id.action_GameBoardUI_to_DiceFragment);
    }






    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;




        //nsdClient.stopDiscovery();
        /*try {
            monopoly.shutdownServer();
            clientHandler.getClient().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        nsdServ.stopNSD();

        Log.i("GameBoardUI", "Conections done");
        monopoly=null;*/
    }
}
