package com.example.monopoly.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.monopoly.R;
import com.example.monopoly.network.Client;
import com.example.monopoly.network.MonopolyServer;

public class EndGameFragment extends Fragment {

    private MonopolyServer monopolyServer = HostGame.getMonopolyServer();
    private NSD_Client nsdClient;
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

        Button closeButton = view.findViewById(R.id.closeButton);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                monopolyServer.closeConnectionsAndShutdown();
                Navigation.findNavController(v).navigate(R.id.FirstFragment);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        monopolyServer.closeClientConnection();
        if(HostGame.getMonopolyServer()!=null){
            monopolyServer.closeConnectionsAndShutdown();
        }
        nsdClient.stopDiscovery();
    }

}
