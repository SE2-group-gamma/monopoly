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

public class GameBoardUI extends Fragment {

    private GameBoardBinding binding;
    private DiceViewModel diceViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diceViewModel = new ViewModelProvider(requireActivity()).get(DiceViewModel.class);
        diceViewModel.getDicesData().observe(this, dices -> {
            Log.i("Dices", dices.toString());
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

        //binding.ivZoom.setImageResource(R.drawable.layerlist_for_gameboard);

        // Initialize players

        ImageView imageView = binding.ivZoom;
        LayerDrawable layerDrawable = (LayerDrawable) imageView.getDrawable();

        // DisplayMetrics might still be useful, so keep them for now
/*
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getRealMetrics(displayMetrics);
        //int height = displayMetrics.heightPixels/displayMetrics.density;
        //int width = displayMetrics.widthPixels/displayMetrics.density;
        double height = displayMetrics.heightPixels;
        double width = displayMetrics.widthPixels;

        Log.d("-----x------",""+height);
        Log.d("------x-----",""+width);

        Log.d("-----------Density",""+displayMetrics.density);

        // Tried relative calculation with dp
        //double heightRatio = (double) height / 411.4285583496094;
        //double widthRatio = (double) width / 891.4285888671875;

        //double heightRatio = (double) height / 1440;
        //double widthRatio = (double) width / 3120;
        //heightRatio = heightRatio * (3.5/displayMetrics.density);
        //widthRatio = widthRatio * (3.5/displayMetrics.density);

        double heightRatio = layerDrawable.getMinimumHeight()/(double)21000;
        double widthRatio = layerDrawable.getMinimumWidth()/(double)21000;

        Log.d("-----x------",""+heightRatio);
        Log.d("------x-----",""+widthRatio);

        // To go from a big field in the corners to the next field: +/- 2800 (horizontally)
        double goOneBigFieldHorizontal = (double)2800*widthRatio;
        player1X = player1X + goOneBigFieldHorizontal;
        player4X = player4X + goOneBigFieldHorizontal;
        layerDrawable.setLayerInset(player1, 0,0,(int)player1X,(int)player1Y);
        layerDrawable.setLayerInset(player4, 0,0,(int)player4X,(int)player4Y);

        // To go from a smaller field to a smaller field: +/- 1700 (horizontally)
        double goOneSmallFieldHorizontal = (double)1700*widthRatio;
        player2X = player2X + goOneBigFieldHorizontal + (goOneSmallFieldHorizontal*5);
        player5X = player5X + goOneBigFieldHorizontal + (goOneSmallFieldHorizontal*5);
        layerDrawable.setLayerInset(player2, 0,0,(int)player2X,(int)player2Y);
        layerDrawable.setLayerInset(player5, 0,0,(int)player5X,(int)player5Y);
*/

        // refresh ImageView to display changes
        //imageView.invalidate();

        super.onViewCreated(view, savedInstanceState);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                for (ClientHandler handler: HostGame.getMonopolyServer().getClients()) {
                    /*handler.writeToClient("GameBoardUI|initializePlayers| ");
                    handler.writeToClient("GameBoardUI|goFieldSmallFieldBottom| ");
                    handler.writeToClient("GameBoardUI|initializePlayerBottomLeft| ");*/
                    handler.writeToClient("GameBoardUI|initializePlayerTopLeft| ");
                    handler.writeToClient("GameBoardUI|goFieldSmallFieldTop| ");
                    handler.writeToClient("GameBoardUI|initializePlayerTopRight| ");
                    handler.writeToClient("GameBoardUI|goFieldSmallFieldRight| ");
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


}
