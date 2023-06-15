package com.example.monopoly.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.monopoly.R;
import com.example.monopoly.network.Client;

public class EndGameFragment extends Fragment {

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
        // Inflate the layout for this fragment
        Client.subscribe(this, "EndGameFragment");
        return inflater.inflate(R.layout.fragment_end_game, container, false);
    }

}
