package com.example.monopoly.ui;

import android.content.Context;
import android.graphics.Color;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.example.monopoly.R;
import com.example.monopoly.databinding.HostGameBinding;
import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.network.Client;
import com.example.monopoly.network.ClientHandler;
import com.example.monopoly.network.MonopolyServer;
import com.example.monopoly.utils.LobbyKey;

import java.io.IOException;
import java.text.DecimalFormatSymbols;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HostGame extends Fragment {

    private HostGameBinding binding;
    private static MonopolyServer ms;

    public static int key = 0;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Client.subscribe(this,"HostGame");
        binding = HostGameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public static MonopolyServer getMonopolyServer() {
        return ms;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {



        super.onViewCreated(view, savedInstanceState);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                binding.seekBar.setPadding(30,0,30,0);
                binding.seekBar2.setPadding(30,0,30,0);
                setSeekBar(binding.seekBar,2,false, binding.textViewSeekBar);
                setSeekBar(binding.seekBar2,2,false, binding.textViewSeekBar2);
            }
        });

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               setSeekBar(seekBar,progress,true,binding.textViewSeekBar);
           }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        binding.seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setSeekBar(seekBar,progress,true,binding.textViewSeekBar2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        binding.backButton.setOnClickListener(view1 -> NavHostFragment.findNavController(HostGame.this)
                .navigate(R.id.action_HostGame_to_FirstFragment));

        binding.createButton.setOnClickListener(view12 -> {
            String user = binding.userInput.getText().toString();
            String lobby = binding.lobbyInput.getText().toString();
            int playerCount = binding.seekBar.getProgress();
            int maxTimeMin = binding.seekBar2.getProgress();
            if(user.isEmpty() && lobby.isEmpty()){
                binding.userInput.setError("No Input");
                binding.lobbyInput.setError("No Input");
            }
            else if(user.isEmpty()){
                binding.userInput.setError("No Input");
            } else if (lobby.isEmpty()) {
                binding.lobbyInput.setError("No Input");
            } else {
                LobbyKey lobbyKey = new LobbyKey();
                key = lobbyKey.generateKey();

                ms = null;
                NSDServer nsdServer = new NSDServer((NsdManager) getActivity().getSystemService(Context.NSD_SERVICE));
                try {
                    ms = new MonopolyServer(playerCount);
                    ms.setHostname(user);
                    nsdServer.registerNSDService(ms.getLocalPort());
                } catch (IOException e) {
                    Log.e("MonopolyServer", "Server creation failed");
                }

                // TODO: create Lobby with key here (Server Side)
                ms.start();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


                NsdManager manager = (NsdManager) getActivity().getSystemService(Context.NSD_SERVICE);
                NSD_Client nsd = new NSD_Client();
                nsd.setIsHost(true);
                nsd.start(manager);

                Log.d("SocketConn","nsd");


                Player player = new Player(user, new Color(),500.00,true);
                //Client c = new Client(null,0,player);
                //Client c = new Client(ms.getClients().get(0).getClient().getInetAddress(),ms.getClients().get(0).getClient().getPort(),player);

                while(!nsd.isReady()){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                nsd.getClient().setHost(true);
                ms.setClient(nsd.getClient());

                try {
                    nsd.getClient().setUser(player);
                    nsd.getClient().setKey(key);
                    nsd.getClient().setMonopolyServer(ms);
                    nsd.getClient().writeToServer("Lobby|hostJoined|"+player.getUsername());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //Bundle bundle = new Bundle();
                //bundle.putParcelable("server", (Parcelable) ms);

                NavHostFragment.findNavController(HostGame.this)
                        .navigate(R.id.action_HostGame_to_Lobby);
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setSeekBar(SeekBar seekBar, int progress, boolean fromUser, TextView textView){
        int width = seekBar.getWidth();
        if(!fromUser && seekBar.equals(binding.seekBar)){
            width += seekBar.getThumbOffset()*3;
        }else{
            width += seekBar.getThumbOffset();
        }

        if(!fromUser && seekBar.equals(binding.seekBar2)){
            width += seekBar.getThumbOffset()*6;
        }
        else {
            width -= seekBar.getThumbOffset();
        }
        int val = (progress * (width - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
        /*
        Log.d("seekbarWidth",""+width);
        Log.d("seekbarThumbOffset",""+seekBar.getThumbOffset());
        Log.d("seekbarMax",""+seekBar.getMax());
        Log.d("val",""+val);
        Log.d("getX",""+seekBar.getX());
        Log.d("setX",""+ (seekBar.getX() + val + seekBar.getThumbOffset() / 2));
        */
        if(seekBar.equals(binding.seekBar)){
            textView.setText("" + (progress+2));
        }else if(progress==10){
            textView.setText("" + DecimalFormatSymbols.getInstance().getInfinity());
        }else{
            textView.setText("" + ((progress*5)+10));
        }
        textView.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2.0f);
    }
}