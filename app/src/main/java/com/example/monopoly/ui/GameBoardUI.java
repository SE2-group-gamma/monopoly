package com.example.monopoly.ui;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.R;
import com.example.monopoly.databinding.GameBoardBinding;
import com.example.monopoly.gamelogic.Dices;
import com.example.monopoly.ui.viewmodels.DiceViewModel;
import com.example.monopoly.network.Client;
import com.example.monopoly.network.ClientHandler;

import java.io.IOException;

public class GameBoardUI extends Fragment {

    private GameBoardBinding binding;
    private DiceViewModel diceViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diceViewModel = new ViewModelProvider(requireActivity()).get(DiceViewModel.class);
        diceViewModel.getDicesData().observe(this, dices -> {
            Log.i("Dices", dices.toString());
            String cheated = dices.isLastRollFlawed()==true?"t":"f";


        });
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = GameBoardBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        Client.subscribe(this,"GameBoardUI");


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
                for (ClientHandler handler: HostGame.getMonopolyServer().getClients()) {
                    //New Protocol: Fragment|action|player:additionalData|sender
                    handler.writeToClient("GameBoardUI|initializePlayerBottomRight|1,2,3,4,5,6:nothing|sender");
                    dosleep();
                    handler.writeToClient("GameBoardUI|goFieldBottom| ");
                    dosleep();
                    handler.writeToClient("GameBoardUI|initializePlayerBottomLeft| ");
                    dosleep();
                    handler.writeToClient("GameBoardUI|goFieldLeft| ");
                    dosleep();
                    handler.writeToClient("GameBoardUI|initializePlayerTopLeft| ");
                    dosleep();
                    handler.writeToClient("GameBoardUI|goFieldTop| ");
                    dosleep();
                    handler.writeToClient("GameBoardUI|initializePlayerTopRight| ");
                    dosleep();
                    handler.writeToClient("GameBoardUI|goFieldRight| ");
                }
            }
        });

        binding.buy.setOnContextClickListener(view1 -> {
            return true;
        });

        binding.backButton.setOnClickListener(view1 -> NavHostFragment.findNavController(GameBoardUI.this)
                .navigate(R.id.action_GameBoard_to_FirstFragment));

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

    public void dosleep(){
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
