package com.example.monopoly.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.monopoly.R;
import com.example.monopoly.network.Client;
import com.example.monopoly.network.MonopolyServer;

public class EndGameFragment extends Fragment {

    private MonopolyServer monopolyServer = HostGame.getMonopolyServer();
    public EndGameFragment() {
        // Required empty public constructor
        Client.subscribe(this,"EndGameFragment");
    }

    public static EndGameFragment newInstance() {
        EndGameFragment fragment = new EndGameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (monopolyServer != null) {
            monopolyServer.closeConnectionsAndShutdown();
            monopolyServer.stop();
        }
        return inflater.inflate(R.layout.fragment_end_game, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(HostGame.getMonopolyServer()!=null){
            monopolyServer.closeConnectionsAndShutdown();
        }

    }
}