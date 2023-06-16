package com.example.monopoly.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        Client.subscribe(this, "EndGameFragment");
        View view = inflater.inflate(R.layout.fragment_end_game, container, false);

        Button closeButton = view.findViewById(R.id.closeButton);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }

}
