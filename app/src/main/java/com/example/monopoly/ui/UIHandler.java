package com.example.monopoly.ui;


import android.os.Bundle;

import android.graphics.drawable.LayerDrawable;

import android.os.Handler;
import android.os.Message;
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
import com.example.monopoly.ui.viewmodels.UIHandlerViewModel;

import java.util.HashMap;
import java.util.Objects;


public class UIHandler extends Handler {
    private Fragment frag;
    private int counter=1;


    private String hostname = "";

    private Client client;
    private ClientViewModel clientViewModel;

    private UIHandlerViewModel uiHandlerViewModel;


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

    double[] playersX = new double[7];
    double[] playersY = new double[7];

    double goOneSmallField;

    HashMap<Integer, String> playerObjects;
    int[] currentPosition;


    public UIHandler(Fragment app) {
        this.frag = app;
        playerObjects = new HashMap<>();
        currentPosition = new int[7];
        uiHandlerViewModel = new ViewModelProvider(frag.requireActivity()).get(UIHandlerViewModel.class);
        if(uiHandlerViewModel.getPlayerObjects().getValue()!=null){
            playerObjects = uiHandlerViewModel.getPlayerObjects().getValue();
        }
        if(uiHandlerViewModel.getCurrentPosition().getValue()!=null){
            currentPosition = uiHandlerViewModel.getCurrentPosition().getValue();
        }
        if(uiHandlerViewModel.getPlayerPositionX().getValue()!=null){
            playersX = uiHandlerViewModel.getPlayerPositionX().getValue();
            playersY = uiHandlerViewModel.getPlayerPositionY().getValue();
        }
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
        } catch (Exception e) {
        }

        switch (type) {
            case "changeText":
                ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser1)).setText(data);
                break;
            case "userJoined":
                switch (counter) {
                    case 2:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser1)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser1Name)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser1)).setText(data);
                        playerObjects.put(player2, data);
                        break;
                    case 3:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser2)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser2Name)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser2)).setText(data);
                        playerObjects.put(player3, data);
                        break;
                    case 4:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser3)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser3Name)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser3)).setText(data);
                        playerObjects.put(player4, data);
                        break;
                    case 5:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser4)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser4Name)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser4)).setText(data);
                        playerObjects.put(player5, data);
                        break;
                    case 6:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser5)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser5Name)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser5)).setText(data);
                        playerObjects.put(player6, data);
                        break;
                }
                counter++;
                break;
            case "hostJoined":
                if(playerObjects.get(player1)==null){
                    playerObjects.put(player1, data);
                    counter++;
                }
                if (!HostGame.lobbyname.equals(" ")) {
                    ((TextView) this.frag.getActivity().findViewById(R.id.textViewLobby)).setText("Lobby: " + HostGame.lobbyname);
                }
                ((TextView) this.frag.getActivity().findViewById(R.id.textViewHost)).setText(data);
                break;
            case "keyFromLobby":
                if (data.equals("1")) {
                    NavHostFragment.findNavController(frag).navigate(R.id.action_JoinGame_to_Lobby);
                } else {
                    Log.d("", "gib toast");
                    Toast.makeText(this.frag.getActivity(), "Key rejected", Toast.LENGTH_LONG).show();
                }
                break;
            case "gameStart":
                Log.d("------------", "gameStart");

                /////as                                                                                                             asdadasdadasdasdasdasd
                Bundle bundle = new Bundle();           //Sollte ich als viewmodel übergeben
                bundle.putString("client", client);
                //bundle.putSerializable("clientObject",);
                Log.i("Dices", "gameStart!!!!!");
                NavHostFragment.findNavController(frag)
                        .navigate(R.id.action_JoinGame_to_GameBoard, bundle);

                uiHandlerViewModel.setPlayerObjects(playerObjects);
                for (int i = 1; i < 6; i++) {
                    currentPosition[i]=0;
                }
                uiHandlerViewModel.setCurrentPosition(currentPosition);

                break;
            case "initializePlayerBottomRight":
                Log.d("------------", "initializePlayerBottomRight");

                Log.d("client", "" + client);
                if(uiHandlerViewModel.getCheckFirst().getValue()){
                    imageView = this.frag.getActivity().findViewById(R.id.iv_zoom);
                    layerDrawable = (LayerDrawable) imageView.getDrawable();

                    // The Ratio is relative to a 1440/3120 phone with density of 3.5
                    // Always multiply by this Ratio
                    heightRatio = layerDrawable.getMinimumHeight() / (double) 21000;
                    widthRatio = layerDrawable.getMinimumWidth() / (double) 21000;

                    playersX[1] = (double) 1100 * widthRatio;
                    playersX[2] = (double) 1100 * widthRatio;
                    playersX[3] = (double) 1100 * widthRatio;
                    playersX[4] = (double) 2000 * widthRatio;
                    playersX[5] = (double) 2000 * widthRatio;
                    playersX[6] = (double) 2000 * widthRatio;

                    playersY[1] = 0;
                    playersY[2] = (double) 1000 * widthRatio;
                    playersY[3] = (double) 1800 * widthRatio;
                    playersY[4] = 0;
                    playersY[5] = (double) 1000 * widthRatio;
                    playersY[6] = (double) 1800 * widthRatio;


                    layerDrawable.setLayerGravity(player1, Gravity.BOTTOM | Gravity.RIGHT);
                    layerDrawable.setLayerGravity(player2, Gravity.BOTTOM | Gravity.RIGHT);
                    layerDrawable.setLayerGravity(player3, Gravity.BOTTOM | Gravity.RIGHT);
                    layerDrawable.setLayerGravity(player4, Gravity.BOTTOM | Gravity.RIGHT);
                    layerDrawable.setLayerGravity(player5, Gravity.BOTTOM | Gravity.RIGHT);
                    layerDrawable.setLayerGravity(player6, Gravity.BOTTOM | Gravity.RIGHT);

                    layerDrawable.setLayerInset(player1, 0, 0, (int) playersX[1], (int) playersY[1]);
                    layerDrawable.setLayerInset(player2, 0, 0, (int) playersX[2], (int) playersY[2]);
                    layerDrawable.setLayerInset(player3, 0, 0, (int) playersX[3], (int) playersY[3]);
                    layerDrawable.setLayerInset(player4, 0, 0, (int) playersX[4], (int) playersY[4]);
                    layerDrawable.setLayerInset(player5, 0, 0, (int) playersX[5], (int) playersY[5]);
                    layerDrawable.setLayerInset(player6, 0, 0, (int) playersX[6], (int) playersY[6]);

                    Log.d("------------", "initializePlayerBottomRightFinished");

                    setPlayerPositions();

                    imageView.setImageDrawable(this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard));

                    uiHandlerViewModel.setCheckFirst(false);
                }else{
                    imageView = this.frag.getActivity().findViewById(R.id.iv_zoom);
                    layerDrawable = (LayerDrawable) imageView.getDrawable();

                    layerDrawable.setLayerGravity(player1, Gravity.BOTTOM | Gravity.RIGHT);
                    layerDrawable.setLayerGravity(player2, Gravity.BOTTOM | Gravity.RIGHT);
                    layerDrawable.setLayerGravity(player3, Gravity.BOTTOM | Gravity.RIGHT);
                    layerDrawable.setLayerGravity(player4, Gravity.BOTTOM | Gravity.RIGHT);
                    layerDrawable.setLayerGravity(player5, Gravity.BOTTOM | Gravity.RIGHT);
                    layerDrawable.setLayerGravity(player6, Gravity.BOTTOM | Gravity.RIGHT);

                    layerDrawable.setLayerInset(player1, 0, 0, (int) playersX[1], (int) playersY[1]);
                    layerDrawable.setLayerInset(player2, 0, 0, (int) playersX[2], (int) playersY[2]);
                    layerDrawable.setLayerInset(player3, 0, 0, (int) playersX[3], (int) playersY[3]);
                    layerDrawable.setLayerInset(player4, 0, 0, (int) playersX[4], (int) playersY[4]);
                    layerDrawable.setLayerInset(player5, 0, 0, (int) playersX[5], (int) playersY[5]);
                    layerDrawable.setLayerInset(player6, 0, 0, (int) playersX[6], (int) playersY[6]);

                    imageView.setImageDrawable(this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard));
                }

                break;
            case "displayKey":
                if (HostGame.key != 0) {
                    ((TextView) this.frag.getActivity().findViewById(R.id.textViewKey)).setText("Game-Key: " + HostGame.key);
                }
                break;
            case "move":
                Log.d("move", data); //Data for move distance and player name
                String[] dataResponse = data.split(":");
                int fieldsToMove = Integer.parseInt(dataResponse[0]);



                if (client.equals(playerObjects.get(player1))) {
                    Log.d("--------XYZ------", "" + fieldsToMove);
                }
                imageView = this.frag.getActivity().findViewById(R.id.iv_zoom);
                layerDrawable = (LayerDrawable) imageView.getDrawable();

                for (int playerNumber = 1; playerNumber < 6; playerNumber++) {
                    if(Objects.equals(playerObjects.get(playerNumber), client)){
                        Log.d("CURRENT POSITION X ",""+uiHandlerViewModel.getPlayerPositionX().getValue()[playerNumber]);
                        Log.d("CURRENT POSITION Y ",""+uiHandlerViewModel.getPlayerPositionY().getValue()[playerNumber]);
                        int positionBefore = currentPosition[playerNumber];
                        currentPosition[playerNumber] = currentPosition[playerNumber] + fieldsToMove;
                        Log.d("--",""+currentPosition[playerNumber]);

                        uiHandlerViewModel.setCurrentPosition(currentPosition);

                        if(positionBefore<10 && currentPosition[playerNumber]<11){
                            goFieldBottom(imageView,playerNumber, fieldsToMove);
                        } else if (positionBefore<=10 && currentPosition[playerNumber]>10) {
                            int move = 10-positionBefore;
                            goFieldBottom(imageView,playerNumber, move);
                            initializePlayerBottomLeft(imageView,playerNumber);
                            if(currentPosition[playerNumber]>11 && currentPosition[playerNumber]<20){
                                int moveTo = currentPosition[playerNumber]-10;
                                goFieldLeft(imageView,playerNumber,moveTo);
                            }
                            if(currentPosition[playerNumber]>=20){
                                goFieldLeft(imageView,playerNumber,9);
                                initializePlayerTopLeft(imageView,playerNumber);
                                if(currentPosition[playerNumber]>20){
                                    int moveTo = 20-currentPosition[playerNumber];
                                    goFieldTop(imageView,playerNumber,moveTo);
                                }
                            }
                        } else if (positionBefore > 10 && currentPosition[playerNumber]< 20) {
                            goFieldLeft(imageView,playerNumber, fieldsToMove);
                        } else if (positionBefore >10 && currentPosition[playerNumber]>= 20) {
                            goFieldLeft(imageView,playerNumber, 9);
                            initializePlayerTopLeft(imageView,playerNumber);
                            if(currentPosition[playerNumber]>20 && currentPosition[playerNumber]<30){
                                int move = 20-currentPosition[playerNumber];
                                goFieldTop(imageView,playerNumber,move);
                            }
                            if(currentPosition[playerNumber]>30){
                                goFieldTop(imageView,playerNumber,10);
                                initializePlayerTopRight(imageView,playerNumber);
                            }
                        }

                    }
                }
                Log.d("----COUNTER----",""+playerObjects.get(player1));
                Log.d("----COUNTER----",""+playerObjects.get(player2));
                imageView = this.frag.getActivity().findViewById(R.id.iv_zoom);


                /*
                initializePlayerBottomLeft(imageView,1);
                goFieldLeft(imageView,1);
                initializePlayerTopLeft(imageView,1);
                goFieldTop(imageView, 1);
                initializePlayerTopRight(imageView,1);
                goFieldRight(imageView, 1);*/
                /*
                initializePlayerBottomRight(imageView, 1);
                initializePlayerBottomRight(imageView, 2);
                initializePlayerBottomRight(imageView, 3);
                initializePlayerBottomRight(imageView, 4);
                initializePlayerBottomRight(imageView, 5);
                initializePlayerBottomRight(imageView, 6);*/


                Log.d("fieldsToMove", "" + fieldsToMove);
                Log.d("client", "" + client);
                Log.d("player1", "" + playerObjects.get(player1));
                Log.d("player2", "" + playerObjects.get(player2));
                break;
            case "playersTurn":
                ((TextView) this.frag.getActivity().findViewById(R.id.turn)).setText(data);
                Log.d("ButtonGreyCheck", "Here is Button" + this.client.getUser().getUsername());
                if (data.equals(this.client.getUser().getUsername())) {
                    Log.d("ButtonGreyCheck2", "VERY NICE INDEED");
                    this.frag.getActivity().findViewById(R.id.throwdice).setAlpha(1.0f);
                    this.frag.getActivity().findViewById(R.id.throwdice).setEnabled(true);

                } else {
                    this.frag.getActivity().findViewById(R.id.throwdice).setAlpha(0.5f);
                    this.frag.getActivity().findViewById(R.id.throwdice).setEnabled(false);
                }

                break;
        }
    }

    public void setPlayerPositions(){
        uiHandlerViewModel.setPlayerPositionX(playersX);
        uiHandlerViewModel.setPlayerPositionY(playersY);
        Log.d("CURRENT POSITION X in setPlayer ",""+uiHandlerViewModel.getPlayerPositionX().getValue()[1]);
        Log.d("CURRENT POSITION Y in setPlayer ",""+uiHandlerViewModel.getPlayerPositionY().getValue()[1]);
    }

    public void initializePlayerBottomRight(ImageView imageView, int player){
        layerDrawable = (LayerDrawable) this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard);

        heightRatio = layerDrawable.getMinimumHeight() / (double) 21000;
        widthRatio = layerDrawable.getMinimumWidth() / (double) 21000;

        if(player<=3){
            playersX[player] = (double) 1100 * widthRatio;
        }else{
            playersX[player] = (double) 2000 * widthRatio;
        }

        if(player==1 || player==4){
            playersY[player] = 0;
        }else if(player==2 || player==5){
            playersY[player] = (double) 1000 * widthRatio;
        } else{
            playersY[player] = (double) 1800 * widthRatio;
        }

        layerDrawable.setLayerGravity(player, Gravity.BOTTOM | Gravity.RIGHT);
        layerDrawable.setLayerInset(player, 0, 0, (int) playersX[player], (int) playersY[player]);

        imageView.setImageDrawable(layerDrawable);
    }

    public void goFieldBottom(ImageView imageView, int player, int move){
        layerDrawable = (LayerDrawable) this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard);

        heightRatio = layerDrawable.getMinimumHeight()/(double)21000;
        widthRatio = layerDrawable.getMinimumWidth()/(double)21000;

        goOneSmallField = (double)1700*widthRatio;

        layerDrawable.setLayerGravity(player, Gravity.BOTTOM | Gravity.RIGHT);
        Log.d("CURRENT playersX before",""+playersX[player]);
        playersX[player] = playersX[player] + (goOneSmallField*move);
        Log.d("CURRENT playersX after",""+playersX[player]);
        layerDrawable.setLayerInset(player, 0, 0, (int) playersX[player], (int) playersY[player]);

        Log.d("------------",""+player);
        Log.d("---playersXAfter---",""+playersX[player]);
        Log.d("---playersYAfter---",""+playersY[player]);
        Log.d("------------","goLeftBottomMethod");

        setPlayerPositions();

        //refresh Image
        imageView.setImageDrawable(layerDrawable);
    }

    public void initializePlayerBottomLeft(ImageView imageView, int player){
        layerDrawable = (LayerDrawable) this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard);

        heightRatio = layerDrawable.getMinimumHeight() / (double) 21000;
        widthRatio = layerDrawable.getMinimumWidth() / (double) 21000;

        double initializeLeftY = (double) 1800 * widthRatio;
        double initializeLeftX = (double) 1200 * widthRatio;

        if(player==1){
            double initializeLeft1Y = (double) 2800 * widthRatio;
            layerDrawable.setLayerGravity(player, Gravity.BOTTOM | Gravity.RIGHT);
            playersY[player] =  playersY[player] + initializeLeft1Y;
            layerDrawable.setLayerInset(player, 0, 0, (int) playersX[player], (int) playersY[player]);
        }else if(player==4){
            double initializeLeft4X = (double) 900 * widthRatio;
            double initializeLeft4Y = (double) 3700 * widthRatio;
            layerDrawable.setLayerGravity(player, Gravity.BOTTOM | Gravity.RIGHT);
            playersX[player] = playersX[player] - initializeLeft4X;
            playersY[player] = playersY[player] + initializeLeft4Y;
            layerDrawable.setLayerInset(player, 0, 0, (int) playersX[player], (int) playersY[player]);
        }else{
            layerDrawable.setLayerGravity(player, Gravity.BOTTOM | Gravity.RIGHT);
            playersX[player] = playersX[player] + initializeLeftX;
            playersY[player] = playersY[player] + initializeLeftY;
            layerDrawable.setLayerInset(player, 0, 0, (int) player3X, (int) player3Y);
            Log.d("---x---",""+player);
            Log.d("---playersXAfter---",""+playersX[player]);
            Log.d("---playersYAfter---",""+playersY[player]);
        }

        setPlayerPositions();
        imageView.setImageDrawable(layerDrawable);
    }

    public void goFieldLeft(ImageView imageView, int player, int move){
        layerDrawable = (LayerDrawable) this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard);

        heightRatio = layerDrawable.getMinimumHeight() / (double) 21000;
        widthRatio = layerDrawable.getMinimumWidth() / (double) 21000;

        goOneSmallField = (double) 1500 * widthRatio;

        layerDrawable.setLayerGravity(player, Gravity.BOTTOM | Gravity.RIGHT);
        playersY[player] = playersY[player] + (goOneSmallField * move);
        layerDrawable.setLayerInset(player, 0, 0, (int) playersX[player], (int) playersY[player]);
        /*
        player3Y = player3Y + (goOneSmallField * 9);
        layerDrawable.setLayerInset(player3, 0, 0, (int) player3X, (int) player3Y);
        layerDrawable.setLayerGravity(player2, Gravity.BOTTOM | Gravity.RIGHT);
        player2Y = player2Y + (goOneSmallField * 9);
        layerDrawable.setLayerInset(player2, 0, 0, (int) player2X, (int) player2Y);

        layerDrawable.setLayerGravity(player5, Gravity.BOTTOM | Gravity.RIGHT);
        player5Y = player5Y + (goOneSmallField * 9);
        layerDrawable.setLayerInset(player5, 0, 0, (int) player5X, (int) player5Y);
        layerDrawable.setLayerGravity(player6, Gravity.BOTTOM | Gravity.RIGHT);
        player6Y = player6Y + (goOneSmallField * 9);
        layerDrawable.setLayerInset(player6, 0, 0, (int) player6X, (int) player6Y);

        layerDrawable.setLayerGravity(player1, Gravity.BOTTOM | Gravity.RIGHT);
        player1Y = player1Y + (goOneSmallField * 9);
        layerDrawable.setLayerInset(player1, 0, 0, (int) player1X, (int) player1Y);
        layerDrawable.setLayerGravity(player4, Gravity.BOTTOM | Gravity.RIGHT);
        player4Y = player4Y + (goOneSmallField * 9);
        layerDrawable.setLayerInset(player4, 0, 0, (int) player4X, (int) player4Y);
        */
        setPlayerPositions();

        imageView.setImageDrawable(layerDrawable);
    }

    public void initializePlayerTopLeft(ImageView imageView, int player){
        layerDrawable = (LayerDrawable) this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard);

        heightRatio = layerDrawable.getMinimumHeight() / (double) 21000;
        widthRatio = layerDrawable.getMinimumWidth() / (double) 21000;

        if(player<=3){
            playersX[player] = (double) 1100 * widthRatio;
        }else{
            playersX[player] = (double) 2000 * widthRatio;
        }

        if(player==1 || player==4){
            playersY[player] = 0;
        }else if(player==2 || player==5){
            playersY[player] = (double) 1000 * widthRatio;
        } else{
            playersY[player] = (double) 1800 * widthRatio;
        }

        layerDrawable.setLayerGravity(player, Gravity.TOP | Gravity.LEFT);
        layerDrawable.setLayerInset(player, (int) playersX[player], (int) playersX[player], 0, 0);

        /*
        player1X = (double) 1100 * widthRatio;
        player2X = (double) 1100 * widthRatio;
        player3X = (double) 1100 * widthRatio;
        player4X = (double) 2000 * widthRatio;
        player5X = (double) 2000 * widthRatio;
        player6X = (double) 2000 * widthRatio;

        player1Y = 0;
        player2Y = (double) 1000 * heightRatio;
        player3Y = (double) 1800 * heightRatio;
        player4Y = 0;
        player5Y = (double) 1000 * heightRatio;
        player6Y = (double) 1800 * heightRatio;

        layerDrawable.setLayerGravity(player1, Gravity.TOP | Gravity.LEFT);
        layerDrawable.setLayerGravity(player2, Gravity.TOP | Gravity.LEFT);
        layerDrawable.setLayerGravity(player3, Gravity.TOP | Gravity.LEFT);
        layerDrawable.setLayerGravity(player4, Gravity.TOP | Gravity.LEFT);
        layerDrawable.setLayerGravity(player5, Gravity.TOP | Gravity.LEFT);
        layerDrawable.setLayerGravity(player6, Gravity.TOP | Gravity.LEFT);

        layerDrawable.setLayerInset(player1, (int) player1X, (int) player1Y, 0, 0);
        layerDrawable.setLayerInset(player2, (int) player2X, (int) player2Y, 0, 0);
        layerDrawable.setLayerInset(player3, (int) player3X, (int) player3Y, 0, 0);
        layerDrawable.setLayerInset(player4, (int) player4X, (int) player4Y, 0, 0);
        layerDrawable.setLayerInset(player5, (int) player5X, (int) player5Y, 0, 0);
        layerDrawable.setLayerInset(player6, (int) player6X, (int) player6Y, 0, 0);
*/
        setPlayerPositions();

        imageView.setImageDrawable(layerDrawable);
    }

    public void goFieldTop(ImageView imageView, int player, int move){
        layerDrawable = (LayerDrawable) this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard);

        heightRatio = layerDrawable.getMinimumHeight() / (double) 21000;
        widthRatio = layerDrawable.getMinimumWidth() / (double) 21000;

        goOneSmallField = (double) 1700 * widthRatio;

        layerDrawable.setLayerGravity(player, Gravity.TOP | Gravity.LEFT);
        playersX[player] = playersX[player] + (goOneSmallField * move);
        layerDrawable.setLayerInset(player, (int) playersX[player], (int) playersY[player], 0, 0);
        /*
        layerDrawable.setLayerGravity(player3, Gravity.TOP | Gravity.LEFT);
        player3X = player3X + (goOneSmallField * 10);
        layerDrawable.setLayerInset(player3, (int) player3X, (int) player3Y, 0, 0);
        layerDrawable.setLayerGravity(player2, Gravity.TOP | Gravity.LEFT);
        player2X = player2X + (goOneSmallField * 10);
        layerDrawable.setLayerInset(player2, (int) player2X, (int) player2Y, 0, 0);

        layerDrawable.setLayerGravity(player5, Gravity.TOP | Gravity.LEFT);
        player5X = player5X + (goOneSmallField * 10);
        layerDrawable.setLayerInset(player5, (int) player5X, (int) player5Y, 0, 0);
        layerDrawable.setLayerGravity(player6, Gravity.TOP | Gravity.LEFT);
        player6X = player6X + (goOneSmallField * 10);
        layerDrawable.setLayerInset(player6, (int) player6X, (int) player6Y, 0, 0);

        layerDrawable.setLayerGravity(player1, Gravity.TOP | Gravity.LEFT);
        player1X = player1X + (goOneSmallField * 10);
        layerDrawable.setLayerInset(player1, (int) player1X, (int) player1Y, 0, 0);
        layerDrawable.setLayerGravity(player4, Gravity.TOP | Gravity.LEFT);
        player4X = player4X + (goOneSmallField * 10);
        layerDrawable.setLayerInset(player4, (int) player4X, (int) player4Y, 0, 0);
*/
        setPlayerPositions();

        imageView.setImageDrawable(layerDrawable);
    }


    public void initializePlayerTopRight(ImageView imageView, int player){
        layerDrawable = (LayerDrawable) this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard);

        heightRatio = layerDrawable.getMinimumHeight() / (double) 21000;
        widthRatio = layerDrawable.getMinimumWidth() / (double) 21000;

        double initializeRightY = (double) 1800 * widthRatio;
        double initializeRightX = (double) 1200 * widthRatio;

        // if(player2 || player3)
        // player2Y = player2Y + 1800;
        // player2X = player2X + 1200;
        layerDrawable.setLayerGravity(player3, Gravity.TOP | Gravity.LEFT);
        player3X = player3X + initializeRightX;
        player3Y = player3Y + initializeRightY;
        layerDrawable.setLayerInset(player3, (int) player3X, (int) player3Y, 0, 0);

        layerDrawable.setLayerGravity(player2, Gravity.TOP | Gravity.LEFT);
        player2X = player2X + initializeRightX;
        player2Y = player2Y + initializeRightY;
        layerDrawable.setLayerInset(player2, (int) player2X, (int) player2Y, 0, 0);

        // if(player5 || player6)
        // player2Y = player2Y + 1800;
        // player2X = player2X + 1200;
        layerDrawable.setLayerGravity(player5, Gravity.TOP | Gravity.LEFT);
        player5X = player5X + initializeRightX;
        player5Y = player5Y + initializeRightY;
        layerDrawable.setLayerInset(player5, (int) player5X, (int) player5Y, 0, 0);

        layerDrawable.setLayerGravity(player6, Gravity.TOP | Gravity.LEFT);
        player6X = player6X + initializeRightX;
        player6Y = player6Y + initializeRightY;
        layerDrawable.setLayerInset(player6, (int) player6X, (int) player6Y, 0, 0);

        // if(player1)
        // player1Y = player1Y + 2800;
        double initializeRight14Y = (double) 2800 * widthRatio;
        layerDrawable.setLayerGravity(player1, Gravity.TOP | Gravity.LEFT);
        player1Y = player1Y + initializeRight14Y;
        layerDrawable.setLayerInset(player1, (int) player1X, (int) player1Y, 0, 0);

        // if(player4)
        // player4Y = player4Y + 3700;
        // player4X = player4X + 900;
        double initializeRight4X = (double) 900 * widthRatio;
        double initializeRight4Y = (double) 3700 * widthRatio;
        layerDrawable.setLayerGravity(player4, Gravity.TOP | Gravity.LEFT);
        player4X = player4X - initializeRight4X;
        player4Y = player4Y + initializeRight4Y;
        layerDrawable.setLayerInset(player4, (int) player4X, (int) player4Y, 0, 0);

        setPlayerPositions();

        imageView.setImageDrawable(layerDrawable);
    }

    public void goFieldRight(ImageView imageView, int player){
        layerDrawable = (LayerDrawable) this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard);

        heightRatio = layerDrawable.getMinimumHeight() / (double) 21000;
        widthRatio = layerDrawable.getMinimumWidth() / (double) 21000;

        goOneSmallField = (double) 1500 * widthRatio;

        layerDrawable.setLayerGravity(player3, Gravity.TOP | Gravity.LEFT);
        player3Y = player3Y + (goOneSmallField * 9);
        layerDrawable.setLayerInset(player3, (int) player3X, (int) player3Y, 0, 0);
        layerDrawable.setLayerGravity(player2, Gravity.TOP | Gravity.LEFT);
        player2Y = player2Y + (goOneSmallField * 9);
        layerDrawable.setLayerInset(player2, (int) player2X, (int) player2Y, 0, 0);

        layerDrawable.setLayerGravity(player5, Gravity.TOP | Gravity.LEFT);
        player5Y = player5Y + (goOneSmallField * 9);
        layerDrawable.setLayerInset(player5, (int) player5X, (int) player5Y, 0, 0);
        layerDrawable.setLayerGravity(player6, Gravity.TOP | Gravity.LEFT);
        player6Y = player6Y + (goOneSmallField * 9);
        layerDrawable.setLayerInset(player6, (int) player6X, (int) player6Y, 0, 0);

        layerDrawable.setLayerGravity(player1, Gravity.TOP | Gravity.LEFT);
        player1Y = player1Y + (goOneSmallField * 9);
        layerDrawable.setLayerInset(player1, (int) player1X, (int) player1Y, 0, 0);
        layerDrawable.setLayerGravity(player4, Gravity.TOP | Gravity.LEFT);
        player4Y = player4Y + (goOneSmallField * 9);
        layerDrawable.setLayerInset(player4, (int) player4X, (int) player4Y, 0, 0);

        setPlayerPositions();

        imageView.setImageDrawable(layerDrawable);
    }

}
