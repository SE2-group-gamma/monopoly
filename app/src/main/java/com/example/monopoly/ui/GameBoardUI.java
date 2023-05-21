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

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int width = displayMetrics.heightPixels;
        int height = displayMetrics.widthPixels;

        Log.d("-----------",""+width);
        Log.d("-----------",""+height);

        // Set start position (standard for it is 1440p)
        double player1X = 0;
        double player2X = 0;
        double player3X = 0;
        double player4X = (double)900/3120*height;
        double player5X = (double)900/3120*height;
        double player6X = (double)900/3120*height;
        Log.d("6X davor",""+player6X);

        double player1Y = 0;
        double player2Y = (double)1000/1440*width;
        double player3Y = (double)1800/1440*width;
        double player4Y = 0;
        double player5Y = (double)1000/1440*width;
        double player6Y = (double)1800/1440*width;
        Log.d("6Y davor",""+player6X);


        layerDrawable.setLayerGravity(player1, Gravity.BOTTOM | Gravity.RIGHT);
        layerDrawable.setLayerGravity(player2, Gravity.BOTTOM | Gravity.RIGHT);
        layerDrawable.setLayerGravity(player3, Gravity.BOTTOM | Gravity.RIGHT);
        layerDrawable.setLayerGravity(player4, Gravity.BOTTOM | Gravity.RIGHT);
        layerDrawable.setLayerGravity(player5, Gravity.BOTTOM | Gravity.RIGHT);
        layerDrawable.setLayerGravity(player6, Gravity.BOTTOM | Gravity.RIGHT);


        layerDrawable.setLayerInset(player1, 0,0,(int)player1X,(int)player1Y);
        layerDrawable.setLayerInset(player2, 0,0,(int)player2X,(int)player2Y);
        layerDrawable.setLayerInset(player3, 0,0,(int)player3X,(int)player3Y);
        layerDrawable.setLayerInset(player4, 0,0,(int)player4X,(int)player4Y);
        layerDrawable.setLayerInset(player5, 0,0,(int)player5X,(int)player5Y);
        layerDrawable.setLayerInset(player6, 0,0,(int)player6X,(int)player6Y);

        // To go from a big field in the corners to the next field: +/- 2800 (horizontally)
        double goOneBigFieldHorizontalDouble = (double)2800/3120;
        int goOneBigFieldHorizontal = (int)(goOneBigFieldHorizontalDouble*height);
        Log.d("1X davor",""+player1X);
        Log.d("4X davor",""+player4X);
        player1X = player1X + goOneBigFieldHorizontal;
        player4X = player4X + goOneBigFieldHorizontal;
        Log.d("1X ",""+player1X);
        Log.d("4X ",""+player4X);

        layerDrawable.setLayerInset(player1, 0,0,(int)player1X,(int)player1Y);
        layerDrawable.setLayerInset(player4, 0,0,(int)player4X,(int)player4Y);
/*
        // To go from a smaller field to a smaller field: +/- 1700 (horizontally)
        double goOneSmallFieldHorizontalDouble = (double)1700/3120;
        int goOneSmallFieldHorizontal = (int)(goOneSmallFieldHorizontalDouble*height);
        player2X = player2X + goOneBigFieldHorizontal + (goOneSmallFieldHorizontal*5);
        player5X = player5X + goOneBigFieldHorizontal + (goOneSmallFieldHorizontal*5);

        layerDrawable.setLayerInset(player2, 0,0,(int)player2X,(int)player2Y);
        layerDrawable.setLayerInset(player5, 0,0,(int)player5X,(int)player5Y);

        // if the player is on top of the gameboard(free parking)
        layerDrawable.setLayerInset(player6, 0,0,(int)(player6X+20000),(int)(player6Y+20000));

*/
        // TODO vertical view





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
