package com.example.monopoly.ui;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
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

    int player1 = 1;
    int player2 = 2;
    int player3 = 3;
    int player4 = 4;
    int player5 = 5;
    int player6 = 6;

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

        // Set start position
        int player1X = 0;
        int player2X = 0;
        int player3X = 0;
        int player4X = 900;
        int player5X = 900;
        int player6X = 900;

        int player1Y = 0;
        int player2Y = 1000;
        int player3Y = 1800;
        int player4Y = 0;
        int player5Y = 1000;
        int player6Y = 1800;
        layerDrawable.setLayerGravity(player1, Gravity.BOTTOM | Gravity.RIGHT);
        layerDrawable.setLayerGravity(player2, Gravity.BOTTOM | Gravity.RIGHT);
        layerDrawable.setLayerGravity(player3, Gravity.BOTTOM | Gravity.RIGHT);
        layerDrawable.setLayerGravity(player4, Gravity.BOTTOM | Gravity.RIGHT);
        layerDrawable.setLayerGravity(player5, Gravity.BOTTOM | Gravity.RIGHT);
        layerDrawable.setLayerGravity(player6, Gravity.BOTTOM | Gravity.RIGHT);

        layerDrawable.setLayerInset(player1, 0,0,player1X,player1Y);
        layerDrawable.setLayerInset(player2, 0,0,player2X,player2Y);
        layerDrawable.setLayerInset(player3, 0,0,player3X,player3Y);
        layerDrawable.setLayerInset(player4, 0,0,player4X,player4Y);
        layerDrawable.setLayerInset(player5, 0,0,player5X,player5Y);
        layerDrawable.setLayerInset(player6, 0,0,player6X,player6Y);

        // To go from a big field in the corners to the next field: +/- 2800 (horizontally)

        layerDrawable.setLayerInset(player1, 0,0,player1X+2800,player1Y);
        layerDrawable.setLayerInset(player4, 0,0,player4X+2800,player4Y);

        // To go from a smaller field to a smaller field: +/- 1700 (horizontally)

        layerDrawable.setLayerInset(player2, 0,0,player2X+2800+(1700*9),player2Y);
        layerDrawable.setLayerInset(player5, 0,0,player5X+4600,player5Y);

        // if the player is on top of the gameboard(free parking): rearrange LayerGravity and the rest can stay the same
        layerDrawable.setLayerGravity(player6, Gravity.TOP | Gravity.LEFT);
        layerDrawable.setLayerInset(player6, player6X,player6Y,0,0);


        // TODO vertical view
        // after the 11th field the players will need to be arranged vertically, because of the gameboard
        layerDrawable.setLayerGravity(player1, Gravity.BOTTOM | Gravity.LEFT);
        layerDrawable.setLayerInset(player1, player1X,0,0,player1Y+2800);




        // refresh ImageView to display changes
        imageView.invalidate();



        super.onViewCreated(view, savedInstanceState);
        //Log.d("Test",binding.button.getText().toString());
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // TODO: Set textView with key from Lobby
                //binding.textViewKey.setText("Game-Key: "+key);
            }
        });

        binding.buy.setOnContextClickListener(view1 -> {
            return true;
        });

        binding.backButton.setOnClickListener(view1 -> NavHostFragment.findNavController(GameBoardUI.this)
                .navigate(R.id.action_GameBoard_to_FirstFragment));

        binding.throwdice.setOnClickListener(view1 -> {
            Log.d("dd","----------------------------------here--------------------------------------");
            layerDrawable.setLayerGravity(player1, Gravity.BOTTOM | Gravity.RIGHT);
            layerDrawable.setLayerInset(player1, 0,0,6000,100);
            imageView.invalidate();
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
