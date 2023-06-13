package com.example.monopoly.ui;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.R;
import com.example.monopoly.databinding.FragmentFirstBinding;
import com.example.monopoly.databinding.GameBoardBinding;
import com.example.monopoly.databinding.SettingsBinding;
import com.example.monopoly.network.Client;
import com.example.monopoly.network.ClientHandler;
import com.example.monopoly.network.MonopolyServer;
import com.example.monopoly.ui.viewmodels.ClientViewModel;
import com.example.monopoly.ui.viewmodels.DiceViewModel;
import com.example.monopoly.ui.viewmodels.GameBoardUIViewModel;

import java.io.IOException;
import java.net.BindException;
import java.net.Socket;

public class GameBoardUI extends Fragment {

    private GameBoardBinding binding;
    private DiceViewModel diceViewModel;
    private ClientViewModel clientViewModel;
    private Client client;
    private GameBoardUIViewModel gameBoardUIViewModel;

    private NSD_Client nsdClient;
    private MonopolyServer monopoly;
    private ClientHandler clientHandler;
    private Socket socket;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        diceViewModel = new ViewModelProvider(requireActivity()).get(DiceViewModel.class);
        clientViewModel = new ViewModelProvider(requireActivity()).get(ClientViewModel.class);
        diceViewModel.getDicesData().observe(this, dices -> {
            if(diceViewModel.getContinuePressedData().getValue()) {     // if fragment is exited upon clicking the continue button (not via command)
                Log.i("Dices", dices.toString());
                String cheated = dices.isLastRollFlawed() == true ? "t" : "f";
                String doublets = (dices.getDice1() == dices.getDice2()) == true ? "t" : "f";       // 3 doubles in a row mean jail!!!
                //String passedStartField = dices.isLastRollFlawed()==true?"t":"f";

                try {
                    this.client.writeToServer("GameBoardUI|move|" + dices.getSum() + ":" + cheated + ":" + doublets + "|" + this.client.getUser().getUsername());
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
        Client.subscribe(this,"GameBoardUI");

        binding = GameBoardBinding.inflate(inflater, container, false);

           /*
        Bundle bundle = getArguments();
        if (bundle != null) {
            monopoly = (MonopolyServer) bundle.getSerializable("monopolyServer");
        }
        socket=new Socket();
        clientHandler = new ClientHandler(socket);
        try {

            monopoly = new MonopolyServer(HostGame.getMonopolyServer.getNumberOfClients());
            //clientHandler.setServer(monopoly);
            //monopoly.start();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        this.client = clientViewModel.getClientData().getValue();       // set client

        /**
         * Reconstruction of GameBoardUI
         */
        try {
            gameBoardUIViewModel = new ViewModelProvider(this.requireActivity()).get(GameBoardUIViewModel.class);   // restore GameBoardUI state
            ((TextView)this.getActivity().findViewById(R.id.turn)).setText(gameBoardUIViewModel.getCurrentPlayer().getValue());     // set name for player turn
            if (gameBoardUIViewModel.getUncoverEnabled().getValue()) {
                this.getActivity().findViewById(R.id.uncover).setAlpha(1.0f);
                this.getActivity().findViewById(R.id.uncover).setEnabled(true);
            } else {
                this.getActivity().findViewById(R.id.uncover).setAlpha(0.5f);
                this.getActivity().findViewById(R.id.uncover).setEnabled(false);
            }
            if(gameBoardUIViewModel.getThrowDiceEnabled().getValue()){
                this.getActivity().findViewById(R.id.throwdice).setAlpha(1.0f);
                this.getActivity().findViewById(R.id.throwdice).setEnabled(true);
            } else {
                this.getActivity().findViewById(R.id.throwdice).setAlpha(0.5f);
                this.getActivity().findViewById(R.id.throwdice).setEnabled(false);
            }
            if(gameBoardUIViewModel.getEndTurnEnabled().getValue()){
                this.getActivity().findViewById(R.id.endTurn).setAlpha(1.0f);
                this.getActivity().findViewById(R.id.endTurn).setEnabled(true);
            }else{
                this.getActivity().findViewById(R.id.endTurn).setAlpha(0.5f);
                this.getActivity().findViewById(R.id.endTurn).setEnabled(false);
            }
            // TODO restore player position
        }catch (Exception e){}

        super.onViewCreated(view, savedInstanceState);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // TODO: Set textView with key from Lobby
                //binding.textViewKey.setText("Game-Key: "+key);
            }
        });

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
        leaveGame();

    }

    private void showDiceFragment(){
        NavHostFragment.findNavController(this).navigate(R.id.action_GameBoardUI_to_DiceFragment);
    }
    private void leaveGame(){
        Button leave = getView().findViewById(R.id.backButton);
        leave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                socket=new Socket();
                clientHandler=new ClientHandler(socket);
                try {
                    monopoly = new MonopolyServer(5);
                }catch (BindException b){
                    b.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //monopoly.playerLeftGame(clientHandler);
                closeClientConnection();
                client.setButtonCheck(false);
                Navigation.findNavController(v).navigate(R.id.FirstFragment);
                Log.i("ServerAct","CLOSED!!");

            }
        });
    }

    private void closeClientConnection() {

            try{
                Socket socket = new Socket();
                clientHandler = new ClientHandler(socket);
                clientHandler.getClient().close();
                //clientHandler.endConn();
                Log.i("ClientActivity", "Client closed 1");
            }catch (IOException e){
                e.printStackTrace();
            }

        nsdClient = new NSD_Client();
        nsdClient.stopDiscovery();


        Log.i("ClientActivity","Client closed 3");
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

        //closeServerConnection();
        closeClientConnection();
        if(monopoly!=null){
            monopoly.closeConnectionsAndShutdown();
        }


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
