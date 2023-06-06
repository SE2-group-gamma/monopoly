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
import com.example.monopoly.network.Client;
import com.example.monopoly.ui.viewmodels.ClientViewModel;
import com.example.monopoly.ui.viewmodels.DiceViewModel;
import com.example.monopoly.ui.viewmodels.GameBoardUIViewModel;

import java.io.IOException;

public class GameBoardUI extends Fragment {

    private GameBoardBinding binding;
    private DiceViewModel diceViewModel;
    private ClientViewModel clientViewModel;
    private String clientName;
    private Client client;
    private boolean didCheat;
    private GameBoardUIViewModel gameBoardUIViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // After changing to a different fragment the fragment status needs to be saved
        /*if (savedInstanceState != null) {
            Log.d("gameTurnCheck",savedInstanceState.getBoolean("IS_UNCOVER_ENABLED")+" .");
            if(savedInstanceState.getBoolean("IS_UNCOVER_ENABLED")){
                this.getActivity().findViewById(R.id.uncover).setAlpha(1.0f);
                this.getActivity().findViewById(R.id.uncover).setEnabled(true);
            }else{
                this.getActivity().findViewById(R.id.uncover).setAlpha(0.5f);
                this.getActivity().findViewById(R.id.uncover).setEnabled(false);
            }
        }*/

        //Bundle implementation
        /*if (getArguments() != null) {
            Log.i("Dices", "Client over bundle: "+getArguments().getString("client"));
            clientName = getArguments().getString("client");            //getting the client name using argument bundle
        }*/

        diceViewModel = new ViewModelProvider(requireActivity()).get(DiceViewModel.class);
        clientViewModel = new ViewModelProvider(requireActivity()).get(ClientViewModel.class);
        diceViewModel.getDicesData().observe(this, dices -> {
            Log.i("Dices", dices.toString());
            String cheated = dices.isLastRollFlawed()==true?"t":"f";
            String doublets = (dices.getDice1()==dices.getDice2())==true?"t":"f";       // 3 doubles in a row mean jail!!!
            //String passedStartField = dices.isLastRollFlawed()==true?"t":"f";

            try {
                this.client.writeToServer("GameBoardUI|move|"+dices.getSum()+":"+cheated+":"+doublets+"|"+this.client.getUser().getUsername());
            } catch (IOException e) {
                throw new RuntimeException(e);
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
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        this.client = clientViewModel.getClientData().getValue();       // set client

        try {
            gameBoardUIViewModel = new ViewModelProvider(this.requireActivity()).get(GameBoardUIViewModel.class);   // restore GameBoardUI state
            if (gameBoardUIViewModel.getUncoverEnabled().getValue()) {
                Log.d("gameTurnCheck","was enabled!");
                this.getActivity().findViewById(R.id.uncover).setAlpha(1.0f);
                this.getActivity().findViewById(R.id.uncover).setEnabled(true);
            } else {
                Log.d("gameTurnCheck","was disabled!");
                this.getActivity().findViewById(R.id.uncover).setAlpha(0.5f);
                this.getActivity().findViewById(R.id.uncover).setEnabled(false);
            }
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
