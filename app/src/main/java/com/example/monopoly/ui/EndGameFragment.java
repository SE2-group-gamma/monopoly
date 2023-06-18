package com.example.monopoly.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.monopoly.R;
import com.example.monopoly.network.Client;
import com.example.monopoly.network.ClientHandler;
import com.example.monopoly.network.MonopolyServer;

import java.net.Socket;

public class EndGameFragment extends Fragment {

    private MonopolyServer monopolyServer = HostGame.getMonopolyServer();
    private NSD_Client nsdClient = new NSD_Client();
    private Socket socket;
    private ClientHandler clientHandler;

    public EndGameFragment() {
        // Required empty public constructor

    }

    public static EndGameFragment newInstance() {
        EndGameFragment fragment = new EndGameFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Client.subscribe(this, "EndGameFragment");
        View view = inflater.inflate(R.layout.fragment_end_game, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstance){
        super.onViewCreated(view,savedInstance);
        Button closeButton = view.findViewById(R.id.closeButton);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socket=new Socket();
                clientHandler=new ClientHandler(socket);
                Navigation.findNavController(v).navigate(R.id.FirstFragment);
                monopolyServer.closeClientConnection();
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(HostGame.getMonopolyServer() != null){
            monopolyServer.closeConnectionsAndShutdown();
            monopolyServer.closeClientConnection();
            nsdClient.stopDiscovery();
        }

    }

}
