package com.example.monopoly.ui;


import android.os.Bundle;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.R;
import com.example.monopoly.network.Client;

import com.example.monopoly.ui.viewmodels.ClientViewModel;

import java.util.HashMap;


public class UIHandler extends Handler {
    private Fragment frag;
    private int counter=1;


    private String hostname = "";

    private Client client;
    private ClientViewModel clientViewModel;


    public int player1 = 1;
    public int player2 = 2;
    public int player3 = 3;
    public int player4 = 4;
    public int player5 = 5;
    public int player6 = 6;

    ImageView imageView;
    LayerDrawable layerDrawable;
    double heightRatio;
    double widthRatio;

    double player1X;
    double player2X;
    double player3X;
    double player4X;
    double player5X;
    double player6X;

    double player1Y;
    double player2Y;
    double player3Y;
    double player4Y;
    double player5Y;
    double player6Y;

    double goOneSmallField;

    HashMap<Integer, String> playerObjects;


    public UIHandler(Fragment app) {
        this.frag = app;
        playerObjects = new HashMap<>();
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        clientViewModel = new ViewModelProvider(frag.requireActivity()).get(ClientViewModel.class);
        this.client = clientViewModel.getClientData().getValue();
        super.handleMessage(msg);
        String data = msg.getData().get("Data").toString();
        String type = msg.getData().get("ActionType").toString();
        String client = "";
        Client clientObject = null;
        try {
            client = msg.getData().get("Client").toString();
        }catch (Exception e){}

        switch (type) {
            case "changeText":
                ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser1)).setText(data);
                break;
            case "userJoined":
                switch (counter){
                    case 1:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser1)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser1Name)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser1)).setText(data);
                        playerObjects.put(player1,client);
                        break;
                    case 2:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser2)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser2Name)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser2)).setText(data);
                        playerObjects.put(player2,client);
                        break;
                    case 3:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser3)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser3Name)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser3)).setText(data);
                        playerObjects.put(player3,client);
                        break;
                    case 4:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser4)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser4Name)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser4)).setText(data);
                        playerObjects.put(player4,client);
                        break;
                    case 5:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser5)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser5Name)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser5)).setText(data);
                        playerObjects.put(player5,client);
                        break;
                }
                counter++;
                break;
            case "hostJoined":
                if(!HostGame.lobbyname.equals(" ")){
                    ((TextView) this.frag.getActivity().findViewById(R.id.textViewLobby)).setText("Lobby: "+HostGame.lobbyname);
                }
                ((TextView) this.frag.getActivity().findViewById(R.id.textViewHost)).setText(data);
                break;
            case "keyFromLobby":
                if(data.equals("1")){
                    NavHostFragment.findNavController(frag).navigate(R.id.action_JoinGame_to_Lobby);
                }else{
                    Log.d("","gib toast");
                    Toast.makeText(this.frag.getActivity(), "Key rejected", Toast.LENGTH_LONG).show();
                }
                break;
            case "gameStart":
                Log.d("------------","gameStart");
                /////as                                                                                                             asdadasdadasdasdasdasd
                Bundle bundle = new Bundle();           //Sollte ich als viewmodel übergeben
                bundle.putString("client",client);
                //bundle.putSerializable("clientObject",);
                Log.i("Dices","gameStart!!!!!");
                NavHostFragment.findNavController(frag)
                        .navigate(R.id.action_JoinGame_to_GameBoard,bundle);
                break;
            case "initializePlayerBottomRight":
                Log.d("------------","initializePlayerBottomRight");

                String[] dataRes = data.split(":");
                int fieldsMove = Integer.parseInt(dataRes[0]);

                Log.d("fieldsToMove",""+fieldsMove);
                Log.d("client",""+client);

                imageView = this.frag.getActivity().findViewById(R.id.iv_zoom);
                layerDrawable = (LayerDrawable) imageView.getDrawable();

                // The Ratio is relative to a 1440/3120 phone with density of 3.5
                // Always multiply by this Ratio
                heightRatio = layerDrawable.getMinimumHeight()/(double)21000;
                widthRatio = layerDrawable.getMinimumWidth()/(double)21000;

                player1X = (double)1100*widthRatio;
                player2X = (double)1100*widthRatio;
                player3X = (double)1100*widthRatio;
                player4X = (double)2000*widthRatio;
                player5X = (double)2000*widthRatio;
                player6X = (double)2000*widthRatio;

                player1Y = 0;
                player2Y = (double)1000*heightRatio;
                player3Y = (double)1800*heightRatio;
                player4Y = 0;
                player5Y = (double)1000*heightRatio;
                player6Y = (double)1800*heightRatio;

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

                Log.d("------------","initializePlayerBottomRightFinished");

                //imageView.setImageDrawable(this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard));

                break;
            case "goFieldBottom":
                // MAX FIELDS = 10 -> then initializePlayerBottomLeft
                Log.d("------------","goFieldBottom");

                imageView = this.frag.getActivity().findViewById(R.id.iv_zoom);
                layerDrawable = (LayerDrawable) imageView.getDrawable();

                heightRatio = layerDrawable.getMinimumHeight()/(double)21000;
                widthRatio = layerDrawable.getMinimumWidth()/(double)21000;

                goOneSmallField = (double)1700*widthRatio;

                layerDrawable.setLayerGravity(player3, Gravity.BOTTOM | Gravity.RIGHT);
                player3X = player3X + (goOneSmallField*10);
                layerDrawable.setLayerInset(player3, 0,0,(int)player3X,(int)player3Y);
                layerDrawable.setLayerGravity(player2, Gravity.BOTTOM | Gravity.RIGHT);
                player2X = player2X + (goOneSmallField*10);
                layerDrawable.setLayerInset(player2, 0,0,(int)player2X,(int)player2Y);

                layerDrawable.setLayerGravity(player5, Gravity.BOTTOM | Gravity.RIGHT);
                player5X = player5X + (goOneSmallField*10);
                layerDrawable.setLayerInset(player5, 0,0,(int)player5X,(int)player5Y);
                layerDrawable.setLayerGravity(player6, Gravity.BOTTOM | Gravity.RIGHT);
                player6X = player6X + (goOneSmallField*10);
                layerDrawable.setLayerInset(player6, 0,0,(int)player6X,(int)player6Y);

                layerDrawable.setLayerGravity(player1, Gravity.BOTTOM | Gravity.RIGHT);
                player1X = player1X + (goOneSmallField*10);
                layerDrawable.setLayerInset(player1, 0,0,(int)player1X,(int)player1Y);
                layerDrawable.setLayerGravity(player4, Gravity.BOTTOM | Gravity.RIGHT);
                player4X = player4X + (goOneSmallField*10);
                layerDrawable.setLayerInset(player4, 0,0,(int)player4X,(int)player4Y);

                Log.d("------------","goFieldBottomFinished");

                //refresh Image
                //imageView.setImageDrawable(this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard));

                break;
            case "initializePlayerBottomLeft":
                Log.d("------------","initializePlayerBottomLeft");

                imageView = this.frag.getActivity().findViewById(R.id.iv_zoom);
                layerDrawable = (LayerDrawable) imageView.getDrawable();

                // The Ratio is relative to a 1440/3120 phone with density of 3.5
                // Always multiply by this Ratio
                heightRatio = layerDrawable.getMinimumHeight()/(double)21000;
                widthRatio = layerDrawable.getMinimumWidth()/(double)21000;

                double initializeLeftY = (double)1800*widthRatio;
                double initializeLeftX = (double)1200*widthRatio;

                // if(player2 || player3)
                // player2Y = player2Y + 1800;
                // player2X = player2X + 1200;
                layerDrawable.setLayerGravity(player3, Gravity.BOTTOM | Gravity.RIGHT);
                player3X = player3X + initializeLeftX;
                player3Y = player3Y + initializeLeftY;
                layerDrawable.setLayerInset(player3, 0,0,(int)player3X,(int)player3Y);

                layerDrawable.setLayerGravity(player2, Gravity.BOTTOM | Gravity.RIGHT);
                player2X = player2X + initializeLeftX;
                player2Y = player2Y + initializeLeftY;
                layerDrawable.setLayerInset(player2, 0,0,(int)player2X,(int)player2Y);

                // if(player5 || player6)
                // player2Y = player2Y + 1800;
                // player2X = player2X + 1200;
                layerDrawable.setLayerGravity(player5, Gravity.BOTTOM | Gravity.RIGHT);
                player5X = player5X + initializeLeftX;
                player5Y = player5Y + initializeLeftY;
                layerDrawable.setLayerInset(player5, 0,0,(int)player5X,(int)player5Y);

                layerDrawable.setLayerGravity(player6, Gravity.BOTTOM | Gravity.RIGHT);
                player6X = player6X + initializeLeftX;
                player6Y = player6Y + initializeLeftY;
                layerDrawable.setLayerInset(player6, 0,0,(int)player6X,(int)player6Y);

                // if(player1)
                // player1Y = player1Y + 2800;
                double initializeLeft14Y = (double)2800*widthRatio;
                layerDrawable.setLayerGravity(player1, Gravity.BOTTOM | Gravity.RIGHT);
                player1Y = player1Y + initializeLeft14Y;
                layerDrawable.setLayerInset(player1, 0,0,(int)player1X,(int)player1Y);

                // if(player4)
                // player4Y = player4Y + 3700;
                // player4X = player4X + 900;
                double initializeLeft4X = (double)900*widthRatio;
                double initializeLeft4Y = (double)3700*widthRatio;
                layerDrawable.setLayerGravity(player4, Gravity.BOTTOM | Gravity.RIGHT);
                player4X = player4X - initializeLeft4X;
                player4Y = player4Y + initializeLeft4Y;
                layerDrawable.setLayerInset(player4, 0,0,(int)player4X,(int)player4Y);

                Log.d("------------","initializePlayerBottomLeftFinished");

                //refresh Image
                //imageView.setImageDrawable(this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard));

                break;
            case "goFieldLeft":
                // MAX FIELDS = 9 -> then initializePlayerTopLeft
                Log.d("------------","goFieldLeft");

                imageView = this.frag.getActivity().findViewById(R.id.iv_zoom);
                layerDrawable = (LayerDrawable) imageView.getDrawable();

                heightRatio = layerDrawable.getMinimumHeight()/(double)21000;
                widthRatio = layerDrawable.getMinimumWidth()/(double)21000;

                goOneSmallField = (double)1500*widthRatio;

                layerDrawable.setLayerGravity(player3, Gravity.BOTTOM | Gravity.RIGHT);
                player3Y = player3Y + (goOneSmallField*9);
                layerDrawable.setLayerInset(player3, 0,0,(int)player3X,(int)player3Y);
                layerDrawable.setLayerGravity(player2, Gravity.BOTTOM | Gravity.RIGHT);
                player2Y = player2Y + (goOneSmallField*9);
                layerDrawable.setLayerInset(player2, 0,0,(int)player2X,(int)player2Y);

                layerDrawable.setLayerGravity(player5, Gravity.BOTTOM | Gravity.RIGHT);
                player5Y = player5Y + (goOneSmallField*9);
                layerDrawable.setLayerInset(player5, 0,0,(int)player5X,(int)player5Y);
                layerDrawable.setLayerGravity(player6, Gravity.BOTTOM | Gravity.RIGHT);
                player6Y = player6Y + (goOneSmallField*9);
                layerDrawable.setLayerInset(player6, 0,0,(int)player6X,(int)player6Y);

                layerDrawable.setLayerGravity(player1, Gravity.BOTTOM | Gravity.RIGHT);
                player1Y = player1Y + (goOneSmallField*9);
                layerDrawable.setLayerInset(player1, 0,0,(int)player1X,(int)player1Y);
                layerDrawable.setLayerGravity(player4, Gravity.BOTTOM | Gravity.RIGHT);
                player4Y = player4Y + (goOneSmallField*9);
                layerDrawable.setLayerInset(player4, 0,0,(int)player4X,(int)player4Y);

                Log.d("------------","goFieldLeftFinished");

                //refresh Image
                //imageView.setImageDrawable(this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard));

                break;
            case "initializePlayerTopLeft":
                Log.d("------------","initializePlayerTopLeft");

                imageView = this.frag.getActivity().findViewById(R.id.iv_zoom);
                layerDrawable = (LayerDrawable) imageView.getDrawable();

                // The Ratio is relative to a 1440/3120 phone with density of 3.5
                // Always multiply by this Ratio
                heightRatio = layerDrawable.getMinimumHeight()/(double)21000;
                widthRatio = layerDrawable.getMinimumWidth()/(double)21000;

                player1X = (double)1100*widthRatio;
                player2X = (double)1100*widthRatio;
                player3X = (double)1100*widthRatio;
                player4X = (double)2000*widthRatio;
                player5X = (double)2000*widthRatio;
                player6X = (double)2000*widthRatio;

                player1Y = 0;
                player2Y = (double)1000*heightRatio;
                player3Y = (double)1800*heightRatio;
                player4Y = 0;
                player5Y = (double)1000*heightRatio;
                player6Y = (double)1800*heightRatio;

                layerDrawable.setLayerGravity(player1, Gravity.TOP | Gravity.LEFT);
                layerDrawable.setLayerGravity(player2, Gravity.TOP | Gravity.LEFT);
                layerDrawable.setLayerGravity(player3, Gravity.TOP | Gravity.LEFT);
                layerDrawable.setLayerGravity(player4, Gravity.TOP | Gravity.LEFT);
                layerDrawable.setLayerGravity(player5, Gravity.TOP | Gravity.LEFT);
                layerDrawable.setLayerGravity(player6, Gravity.TOP | Gravity.LEFT);

                layerDrawable.setLayerInset(player1, (int)player1X,(int)player1Y,0,0);
                layerDrawable.setLayerInset(player2, (int)player2X,(int)player2Y,0,0);
                layerDrawable.setLayerInset(player3, (int)player3X,(int)player3Y,0,0);
                layerDrawable.setLayerInset(player4, (int)player4X,(int)player4Y,0,0);
                layerDrawable.setLayerInset(player5, (int)player5X,(int)player5Y,0,0);
                layerDrawable.setLayerInset(player6, (int)player6X,(int)player6Y,0,0);

                Log.d("------------","initializePlayerTopLeftFinished");

                //imageView.setImageDrawable(this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard));

                break;
            case "goFieldTop":
                // MAX FIELDS = 10 -> then initializePlayerTopRight
                Log.d("------------","goFieldTop");

                imageView = this.frag.getActivity().findViewById(R.id.iv_zoom);
                layerDrawable = (LayerDrawable) imageView.getDrawable();

                heightRatio = layerDrawable.getMinimumHeight()/(double)21000;
                widthRatio = layerDrawable.getMinimumWidth()/(double)21000;

                goOneSmallField = (double)1700*widthRatio;

                layerDrawable.setLayerGravity(player3, Gravity.TOP | Gravity.LEFT);
                player3X = player3X + (goOneSmallField*10);
                layerDrawable.setLayerInset(player3, (int)player3X,(int)player3Y,0,0);
                layerDrawable.setLayerGravity(player2, Gravity.TOP | Gravity.LEFT);
                player2X = player2X + (goOneSmallField*10);
                layerDrawable.setLayerInset(player2, (int)player2X,(int)player2Y,0,0);

                layerDrawable.setLayerGravity(player5, Gravity.TOP | Gravity.LEFT);
                player5X = player5X + (goOneSmallField*10);
                layerDrawable.setLayerInset(player5, (int)player5X,(int)player5Y,0,0);
                layerDrawable.setLayerGravity(player6, Gravity.TOP | Gravity.LEFT);
                player6X = player6X + (goOneSmallField*10);
                layerDrawable.setLayerInset(player6, (int)player6X,(int)player6Y,0,0);

                layerDrawable.setLayerGravity(player1, Gravity.TOP | Gravity.LEFT);
                player1X = player1X + (goOneSmallField*10);
                layerDrawable.setLayerInset(player1, (int)player1X,(int)player1Y,0,0);
                layerDrawable.setLayerGravity(player4, Gravity.TOP | Gravity.LEFT);
                player4X = player4X + (goOneSmallField*10);
                layerDrawable.setLayerInset(player4, (int)player4X,(int)player4Y,0,0);

                Log.d("------------","goFieldTopFinished");

                //refresh Image
                //imageView.setImageDrawable(this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard));

                break;
            case "initializePlayerTopRight":
                Log.d("------------","initializePlayerTopRight");

                imageView = this.frag.getActivity().findViewById(R.id.iv_zoom);
                layerDrawable = (LayerDrawable) imageView.getDrawable();

                // The Ratio is relative to a 1440/3120 phone with density of 3.5
                // Always multiply by this Ratio
                heightRatio = layerDrawable.getMinimumHeight()/(double)21000;
                widthRatio = layerDrawable.getMinimumWidth()/(double)21000;

                double initializeRightY = (double)1800*widthRatio;
                double initializeRightX = (double)1200*widthRatio;

                // if(player2 || player3)
                // player2Y = player2Y + 1800;
                // player2X = player2X + 1200;
                layerDrawable.setLayerGravity(player3, Gravity.TOP | Gravity.LEFT);
                player3X = player3X + initializeRightX;
                player3Y = player3Y + initializeRightY;
                layerDrawable.setLayerInset(player3, (int)player3X,(int)player3Y,0,0);

                layerDrawable.setLayerGravity(player2, Gravity.TOP | Gravity.LEFT);
                player2X = player2X + initializeRightX;
                player2Y = player2Y + initializeRightY;
                layerDrawable.setLayerInset(player2, (int)player2X,(int)player2Y,0,0);

                // if(player5 || player6)
                // player2Y = player2Y + 1800;
                // player2X = player2X + 1200;
                layerDrawable.setLayerGravity(player5, Gravity.TOP | Gravity.LEFT);
                player5X = player5X + initializeRightX;
                player5Y = player5Y + initializeRightY;
                layerDrawable.setLayerInset(player5, (int)player5X,(int)player5Y,0,0);

                layerDrawable.setLayerGravity(player6, Gravity.TOP | Gravity.LEFT);
                player6X = player6X + initializeRightX;
                player6Y = player6Y + initializeRightY;
                layerDrawable.setLayerInset(player6, (int)player6X,(int)player6Y,0,0);

                // if(player1)
                // player1Y = player1Y + 2800;
                double initializeRight14Y = (double)2800*widthRatio;
                layerDrawable.setLayerGravity(player1, Gravity.TOP | Gravity.LEFT);
                player1Y = player1Y + initializeRight14Y;
                layerDrawable.setLayerInset(player1, (int)player1X,(int)player1Y,0,0);

                // if(player4)
                // player4Y = player4Y + 3700;
                // player4X = player4X + 900;
                double initializeRight4X = (double)900*widthRatio;
                double initializeRight4Y = (double)3700*widthRatio;
                layerDrawable.setLayerGravity(player4, Gravity.TOP | Gravity.LEFT);
                player4X = player4X - initializeRight4X;
                player4Y = player4Y + initializeRight4Y;
                layerDrawable.setLayerInset(player4, (int)player4X,(int)player4Y,0,0);

                Log.d("------------","initializePlayerTopRightFinished");

                //refresh Image
                //imageView.setImageDrawable(this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard));

                break;
            case "goFieldRight":
                // MAX FIELDS = 9 -> then initializePlayerBottomRight
                Log.d("------------","goFieldRight");

                imageView = this.frag.getActivity().findViewById(R.id.iv_zoom);
                layerDrawable = (LayerDrawable) imageView.getDrawable();

                heightRatio = layerDrawable.getMinimumHeight()/(double)21000;
                widthRatio = layerDrawable.getMinimumWidth()/(double)21000;

                goOneSmallField = (double)1500*widthRatio;

                layerDrawable.setLayerGravity(player3, Gravity.TOP | Gravity.LEFT);
                player3Y = player3Y + (goOneSmallField*9);
                layerDrawable.setLayerInset(player3, (int)player3X,(int)player3Y,0,0);
                layerDrawable.setLayerGravity(player2, Gravity.TOP | Gravity.LEFT);
                player2Y = player2Y + (goOneSmallField*9);
                layerDrawable.setLayerInset(player2, (int)player2X,(int)player2Y,0,0);

                layerDrawable.setLayerGravity(player5, Gravity.TOP | Gravity.LEFT);
                player5Y = player5Y + (goOneSmallField*9);
                layerDrawable.setLayerInset(player5, (int)player5X,(int)player5Y,0,0);
                layerDrawable.setLayerGravity(player6, Gravity.TOP | Gravity.LEFT);
                player6Y = player6Y + (goOneSmallField*9);
                layerDrawable.setLayerInset(player6, (int)player6X,(int)player6Y,0,0);

                layerDrawable.setLayerGravity(player1, Gravity.TOP | Gravity.LEFT);
                player1Y = player1Y + (goOneSmallField*9);
                layerDrawable.setLayerInset(player1, (int)player1X,(int)player1Y,0,0);
                layerDrawable.setLayerGravity(player4, Gravity.TOP | Gravity.LEFT);
                player4Y = player4Y + (goOneSmallField*9);
                layerDrawable.setLayerInset(player4, (int)player4X,(int)player4Y,0,0);

                Log.d("------------","goFieldRightFinished");

                //refresh Image
                imageView.setImageDrawable(this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard));

                break;
            case "displayKey":
                Log.d("tuaaaaaaa","displayKey");
                if(HostGame.key!=0) {
                    ((TextView) this.frag.getActivity().findViewById(R.id.textViewKey)).setText("Game-Key: " + HostGame.key);
                }
                break;
            case "move":
                Log.d("move",data); //Data for move distance and player name
                String[] dataResponse = data.split(":");
                int fieldsToMove = Integer.parseInt(dataResponse[0]);

                Log.d("fieldsToMove",""+fieldsToMove);
                Log.d("client",""+client);
                break;
            case "playersTurn":
                ((TextView) this.frag.getActivity().findViewById(R.id.turn)).setText(data);
                Log.d("ButtonGreyCheck", "Here is Button"+this.client.getUser().getUsername());
                if(data.equals(this.client.getUser().getUsername())){
                    Log.d("ButtonGreyCheck2", "VERY NICE INDEED");
                    this.frag.getActivity().findViewById(R.id.throwdice).setAlpha(1.0f);
                    this.frag.getActivity().findViewById(R.id.throwdice).setEnabled(true);

                }else{
                    this.frag.getActivity().findViewById(R.id.throwdice).setAlpha(0.5f);
                    this.frag.getActivity().findViewById(R.id.throwdice).setEnabled(false);
                }

                break;
        }
        // Needed information: new position as an int, player as an int (1,2,3,4,5,6)
    }
}
