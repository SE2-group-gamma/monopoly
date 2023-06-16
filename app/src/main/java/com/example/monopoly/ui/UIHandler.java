package com.example.monopoly.ui;


import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.R;
import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.gamelogic.PlayerMapPosition;
import com.example.monopoly.gamelogic.properties.ClientPropertyStorage;
import com.example.monopoly.network.Client;
import com.example.monopoly.ui.viewmodels.ClientViewModel;
import com.example.monopoly.ui.viewmodels.GameBoardUIViewModel;
import com.example.monopoly.ui.viewmodels.UIHandlerViewModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class UIHandler extends Handler {
    private Fragment frag;
    private int counter = 1;

    private String hostname = "";
    private int counterMove = 0;
    private Client clientObj;
    private ClientViewModel clientViewModel;
    private GameBoardUIViewModel gameBoardUIViewModel;

    private UIHandlerViewModel uiHandlerViewModel;


    public int player1 = 1;
    public int player2 = 2;
    public int player3 = 3;
    public int player4 = 4;
    public int player5 = 5;
    public int player6 = 6;

    PlayerMapPosition playerMapPosition;

    ImageView imageView;
    LayerDrawable layerDrawable;
    double heightRatio;
    double widthRatio;

    double[] playersX = new double[7];
    double[] playersY = new double[7];
    int[] playerGravity = new int[7];

    double goOneSmallField;

    HashMap<Integer, String> playerObjects;
    int[] currentPosition;
    int currentMoney;

    //private boolean uncoverEnabled;
    public UIHandler(Fragment app) {
        this.frag = app;
        playerObjects = new HashMap<>();
        currentPosition = new int[7];
        uiHandlerViewModel = new ViewModelProvider(frag.requireActivity()).get(UIHandlerViewModel.class);
        if (uiHandlerViewModel.getCurrentMoney().getValue() == null) {
            currentMoney = 1500;
        }

        if (uiHandlerViewModel.getPlayerObjects().getValue() != null) {
            playerObjects = uiHandlerViewModel.getPlayerObjects().getValue();
        }
        if (uiHandlerViewModel.getCurrentPosition().getValue() != null) {
            currentPosition = uiHandlerViewModel.getCurrentPosition().getValue();
        }
        if (uiHandlerViewModel.getPlayerPositionX().getValue() != null) {
            playersX = uiHandlerViewModel.getPlayerPositionX().getValue();
            playersY = uiHandlerViewModel.getPlayerPositionY().getValue();
            Log.d("hostPosition", "X Pos: " + playersX[1] + "; Y Pos: " + playersY[1]);
        }
        if (uiHandlerViewModel.getCurrentMoney().getValue() != null) {
            currentMoney = uiHandlerViewModel.getCurrentMoney().getValue();
        }
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        clientViewModel = new ViewModelProvider(frag.requireActivity()).get(ClientViewModel.class);
        gameBoardUIViewModel = new ViewModelProvider(frag.requireActivity()).get(GameBoardUIViewModel.class);   // GameBoardUI state


        this.clientObj = clientViewModel.getClientData().getValue();

        if (gameBoardUIViewModel.getCurrentMoney().getValue() != null) {
            ((TextView) this.frag.getActivity().findViewById(R.id.currentMoney)).setText(gameBoardUIViewModel.getCurrentMoney().getValue());
        }

        if (gameBoardUIViewModel.getCurrentTime().getValue() != null && ((TextView) this.frag.getActivity().findViewById(R.id.time)) != null) {
            ((TextView) this.frag.getActivity().findViewById(R.id.time)).setText(gameBoardUIViewModel.getCurrentTime().getValue());
        }


        this.clientObj = clientViewModel.getClientData().getValue();
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
                if (playerObjects.get(player1) == null) {
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
                for (int i = 1; i <= 6; i++) {
                    currentPosition[i] = 0;
                }
                uiHandlerViewModel.setCurrentPosition(currentPosition);
                uiHandlerViewModel.setCurrentMoney(currentMoney);

                break;
            case "initializePlayerBottomRight1":
                Log.d("------------", "initializePlayerBottomRight");

                Log.d("hostPosition", "Initialize Host");
                if (uiHandlerViewModel.getCheckFirst().getValue()) {

                    gameBoardUIViewModel.setUncoverEnabled(this.frag.getActivity().findViewById(R.id.uncover).isEnabled());     // save uncover status on first turn

                    imageView = this.frag.getActivity().findViewById(R.id.iv_zoom);
                    //layerDrawable = (LayerDrawable) imageView.getDrawable();

                    layerDrawable = (LayerDrawable) this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard);

                    // The Ratio is relative to a 1440/3120 phone with density of 3.5
                    // Always multiply by this Ratio
                    heightRatio = layerDrawable.getMinimumHeight() / (double) 21000;
                    widthRatio = layerDrawable.getMinimumWidth() / (double) 21000;

                    Log.d("gameTurnCheck", layerDrawable.getMinimumHeight() + "");

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

                    for (int i = 0; i <= 6; i++) {
                        playerGravity[i] = Gravity.BOTTOM | Gravity.RIGHT;
                    }

                    uiHandlerViewModel.setPlayerGravityLiveData(playerGravity);

                    Log.d("hostPosition", "Host redraw at start");


                    layerDrawable.setLayerGravity(player1, playerGravity[1]);
                    layerDrawable.setLayerGravity(player2, playerGravity[2]);
                    layerDrawable.setLayerGravity(player3, playerGravity[3]);
                    layerDrawable.setLayerGravity(player4, playerGravity[4]);
                    layerDrawable.setLayerGravity(player5, playerGravity[5]);
                    layerDrawable.setLayerGravity(player6, playerGravity[6]);

                    layerDrawable.setLayerInset(player1, 0, 0, (int) playersX[1], (int) playersY[1]);
                    layerDrawable.setLayerInset(player2, 0, 0, (int) playersX[2], (int) playersY[2]);
                    layerDrawable.setLayerInset(player3, 0, 0, (int) playersX[3], (int) playersY[3]);
                    layerDrawable.setLayerInset(player4, 0, 0, (int) playersX[4], (int) playersY[4]);
                    layerDrawable.setLayerInset(player5, 0, 0, (int) playersX[5], (int) playersY[5]);
                    layerDrawable.setLayerInset(player6, 0, 0, (int) playersX[6], (int) playersY[6]);

                    Log.d("------------", "initializePlayerBottomRightFinished");

                    setPlayerPositions();

                    imageView.setImageDrawable(layerDrawable);

                    uiHandlerViewModel.setCheckFirst(false);
                } else {
                    restore();
                    Log.d("gameTurnCheck", "I restored: " + clientObj.getUser().getUsername());
                }

                break;
            case "displayKey":
                if (HostGame.key != 0) {
                    ((TextView) this.frag.getActivity().findViewById(R.id.textViewKey)).setText("Game-Key: " + HostGame.key);
                }
                break;
            case "changeCapital":
                if (clientObj.getUser().getUsername().equals(client)) {
                    int money = currentMoney + Integer.parseInt(data.split(":")[0]);
                    uiHandlerViewModel.setCurrentMoney(money);
                    if (((TextView) this.frag.getActivity().findViewById(R.id.currentMoney)) != null)
                        ((TextView) this.frag.getActivity().findViewById(R.id.currentMoney)).setText("Current Money \n" + money + "$");
                    Log.d("MoneyPlayer", "" + money);
                    Log.d("MoneyPlayer", "" + client);
                }
                break;
            case "uncoverUsed":
                gameBoardUIViewModel.setUncoverEnabled(false);


                String[] dataResponseSplit = data.split(":");

                if (this.frag.getActivity().findViewById(R.id.uncover) != null) {
                    this.frag.getActivity().findViewById(R.id.uncover).setAlpha(0.5f);
                    this.frag.getActivity().findViewById(R.id.uncover).setEnabled(false);
                    if(dataResponseSplit[0].equals("t")){
                        Toast.makeText(this.frag.getActivity(),dataResponseSplit[1]+" successfully punished "+client+"!",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this.frag.getActivity(),dataResponseSplit[1]+" failed to punish "+client+"!",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case "movePlayer":
                if (clientObj.getUser().getUsername().equals(client)) {
                    movePlayer(data);   // disable ui and viewModel entries
                    //restore();
                } else {
                    //restore();
                }

                //restore();
                Log.d("move", data); //Data for move distance and player name
                String[] dataResponse = data.split(":");
                int fieldsToMove = Integer.parseInt(dataResponse[0]);

                /*if (client.equals(playerObjects.get(player1))) {
                    Log.d("--------XYZ------", "" + fieldsToMove);
                }*/
                //imageView = this.frag.getActivity().findViewById(R.id.iv_zoom);
                //layerDrawable = (LayerDrawable) imageView.getDrawable();


                for (int playerNumber = 1; playerNumber <= 6; playerNumber++) {
                    if (Objects.equals(playerObjects.get(playerNumber), client)) {
                        Log.d("CURRENT POSITION X ", "" + uiHandlerViewModel.getPlayerPositionX().getValue()[playerNumber]);
                        Log.d("CURRENT POSITION Y ", "" + uiHandlerViewModel.getPlayerPositionY().getValue()[playerNumber]);
                        int positionBefore = currentPosition[playerNumber];
                        currentPosition[playerNumber] = currentPosition[playerNumber] + fieldsToMove;
                        Log.d("--", "" + currentPosition[playerNumber]);
                        if (currentPosition[playerNumber] >= 40) {
                            Log.d("playerNumber: ", "" + playerNumber);

                            currentPosition[playerNumber] = currentPosition[playerNumber] - 40;
                            // TODO get starting money
                            if (playerObjects.get(playerNumber).equals(clientObj.getUser().getUsername())) {
                                Log.d("MoneyPlayer", "client = " + client);
                                try {
                                    clientObj.writeToServer("GameBoardUI|giveMoney|200:|" + clientObj.getUser().getUsername());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        Log.d("--", "" + currentPosition[playerNumber]);

                        uiHandlerViewModel.setCurrentPosition(currentPosition);

                        if (positionBefore < 10 && currentPosition[playerNumber] < 11) {
                            goFieldBottom(imageView, playerNumber, fieldsToMove);
                        } else if (positionBefore <= 10 && currentPosition[playerNumber] > 10) {
                            int move = 10 - positionBefore;
                            goFieldBottom(imageView, playerNumber, move);
                            initializePlayerBottomLeft(imageView, playerNumber);
                            if (currentPosition[playerNumber] > 11 && currentPosition[playerNumber] < 20) {
                                int moveTo = currentPosition[playerNumber] - 11;
                                goFieldLeft(imageView, playerNumber, moveTo);
                            }
                            if (currentPosition[playerNumber] >= 20) {
                                goFieldLeft(imageView, playerNumber, 9);
                                initializePlayerTopLeft(imageView, playerNumber);
                                if (currentPosition[playerNumber] > 20) {
                                    int moveTo = currentPosition[playerNumber] - 20;
                                    goFieldTop(imageView, playerNumber, moveTo);
                                }
                            }
                        } else if (positionBefore > 10 && currentPosition[playerNumber] < 20 && currentPosition[playerNumber] > 12) {
                            goFieldLeft(imageView, playerNumber, fieldsToMove);
                        } else if (positionBefore > 10 && currentPosition[playerNumber] >= 20 && positionBefore < 20) {
                            int moveTo = 19 - positionBefore;
                            goFieldLeft(imageView, playerNumber, moveTo);
                            initializePlayerTopLeft(imageView, playerNumber);
                            if (currentPosition[playerNumber] > 20 && currentPosition[playerNumber] < 30) {
                                int move = currentPosition[playerNumber] - 20;
                                goFieldTop(imageView, playerNumber, move);
                            }
                            if (currentPosition[playerNumber] >= 30) {
                                goFieldTop(imageView, playerNumber, 10);
                                if (currentPosition[playerNumber] > 30) {
                                    initializePlayerTopRight(imageView, playerNumber);
                                }
                                if (currentPosition[playerNumber] > 31) {
                                    int moveToRight = currentPosition[playerNumber] - 30;
                                    goFieldRight(imageView, playerNumber, moveToRight);
                                }
                            }
                        } else if (positionBefore >= 20 && currentPosition[playerNumber] <= 30 && currentPosition[playerNumber] > 20) {
                            goFieldTop(imageView, playerNumber, fieldsToMove);
                        } else if (positionBefore >= 20 && currentPosition[playerNumber] > 30 && positionBefore <= 30) {
                            Log.d("-------------------", "Im here-------------");
                            int moveTo = 30 - positionBefore;
                            Log.d("-------------------", "moveTo" + moveTo);
                            goFieldTop(imageView, playerNumber, moveTo);
                            initializePlayerTopRight(imageView, playerNumber);
                            if (currentPosition[playerNumber] > 31 && currentPosition[playerNumber] < 40) {
                                int move = currentPosition[playerNumber] - 31;
                                Log.d("-------------------", "move" + move);
                                goFieldRight(imageView, playerNumber, move);
                            }
                        } else if (positionBefore <= 30 && currentPosition[playerNumber] < 5) {
                            int moveTo = 30 - positionBefore;
                            goFieldTop(imageView, playerNumber, moveTo);
                            initializePlayerTopRight(imageView, playerNumber);
                            goFieldRight(imageView, playerNumber, 9);
                            initializePlayerBottomRight(imageView, playerNumber);
                            if (currentPosition[playerNumber] > 0) {
                                goFieldBottom(imageView, playerNumber, currentPosition[playerNumber]);
                            }
                        } else if (positionBefore > 30 && currentPosition[playerNumber] < 40 && currentPosition[playerNumber] > 30) {
                            goFieldRight(imageView, playerNumber, fieldsToMove);
                        } else if (positionBefore > 30 && currentPosition[playerNumber] < 12) {
                            int move = 39 - positionBefore;
                            goFieldRight(imageView, playerNumber, move);
                            initializePlayerBottomRight(imageView, playerNumber);
                            if (currentPosition[playerNumber] == 11) {
                                goFieldBottom(imageView, playerNumber, 10);
                                initializePlayerBottomLeft(imageView, playerNumber);
                            }
                            if (currentPosition[playerNumber] <= 10) {
                                goFieldBottom(imageView, playerNumber, currentPosition[playerNumber]);
                            }
                        }
                    }
                }
                Log.d("gameTurnCheck", "Host move to: " +
                        layerDrawable.getLayerInsetRight(1));
                //setPlayerPositions();

                gameBoardUIViewModel.setUncoverEnabled(true);

                if (this.frag.getActivity().findViewById(R.id.uncover) == null)
                    return;
                this.frag.getActivity().findViewById(R.id.uncover).setAlpha(1.0f);
                this.frag.getActivity().findViewById(R.id.uncover).setEnabled(true);


                // Log.d("----COUNTER----",""+playerObjects.get(player1));
                // Log.d("----COUNTER----",""+playerObjects.get(player2));
                imageView = this.frag.getActivity().findViewById(R.id.iv_zoom);

                restore();
/*
                Log.d("fieldsToMove", "" + fieldsToMove);
                Log.d("client", "" + client);
                Log.d("player1", "" + playerObjects.get(player1));
                Log.d("player2", "" + playerObjects.get(player2));*/
                break;

            case "playersTurn":
                //Log.d("gameTurnCheck","; "+NavHostFragment.findNavController(this.frag).getCurrentDestination().getLabel());
/*
                if(!uiHandlerViewModel.getCheckFirst().getValue())
                    //restore();

                //NavHostFragment.findNavController(this.frag).getCurrentDestination().

                if(!"GameBoardUI".equals(NavHostFragment.findNavController(this.frag).getCurrentDestination().getLabel())) {
                    //NavHostFragment.findNavController(this.frag).navigate(R.id.move_to_GameBoardUI);
                    //Navigation.findNavController(this.frag.getActivity().findViewById(NavHostFragment.findNavController(this.frag).getCurrentDestination().getId())).navigate(R.id.move_to_GameBoardUI);
                    //NavHostFragment.findNavController(this.frag.getActivity().getFragmentManager().findFragmentById(NavHostFragment.findNavController(this.frag).getCurrentDestination().getId())).navigate(R.id.move_to_GameBoardUI);
                    //Objects.requireNonNull(this.frag.getActivity()).getSupportFragmentManager().popBackStack();
                }*/

                gameBoardUIViewModel.setCurrentPlayer(data + "'s turn");

                if (data.equals(this.clientObj.getUser().getUsername())) {
                    gameBoardUIViewModel.setEndTurnEnabled(true);
                    gameBoardUIViewModel.setThrowDiceEnabled(true);
                } else {
                    gameBoardUIViewModel.setEndTurnEnabled(false);
                    gameBoardUIViewModel.setThrowDiceEnabled(false);
                }
                if(this.frag.getActivity().findViewById(R.id.uncover)==null)
                    return;

                if (gameBoardUIViewModel.getUncoverEnabled().getValue() != null) {
                    if (gameBoardUIViewModel.getUncoverEnabled().getValue()) {
                        this.frag.getActivity().findViewById(R.id.uncover).setEnabled(true);
                        this.frag.getActivity().findViewById(R.id.uncover).setAlpha(1.0f);
                    } else {
                        this.frag.getActivity().findViewById(R.id.uncover).setEnabled(false);
                        this.frag.getActivity().findViewById(R.id.uncover).setAlpha(0.5f);
                    }
                }
                ((TextView) this.frag.getActivity().findViewById(R.id.turn)).setText(data + "'s turn");

                Log.d("ButtonGreyCheck", "Here is Button" + this.clientObj.getUser().getUsername());
                if (data.equals(this.clientObj.getUser().getUsername())) {     // your turn
                    Log.d("ButtonGreyCheck2", "VERY NICE INDEED");
                    this.frag.getActivity().findViewById(R.id.throwdice).setAlpha(1.0f);
                    this.frag.getActivity().findViewById(R.id.throwdice).setEnabled(true);
                    this.frag.getActivity().findViewById(R.id.endTurn).setAlpha(1.0f);
                    this.frag.getActivity().findViewById(R.id.endTurn).setEnabled(true);
                    //this.frag.getActivity().findViewById(R.id.uncover).setAlpha(0.5f);
                    //this.frag.getActivity().findViewById(R.id.uncover).setEnabled(false);
                    //gameBoardUIViewModel.setUncoverEnabled(false);
                } else {                                                    // not your turn
                    this.frag.getActivity().findViewById(R.id.throwdice).setAlpha(0.5f);
                    this.frag.getActivity().findViewById(R.id.throwdice).setEnabled(false);
                    this.frag.getActivity().findViewById(R.id.endTurn).setAlpha(0.5f);
                    this.frag.getActivity().findViewById(R.id.endTurn).setEnabled(false);
                }
                break;

            case "exitDiceFragment":
                NavHostFragment.findNavController(this.frag).navigate(R.id.move_to_GameBoardUI);
                //Thread.sleep(1000);

                break;

            case "setStartTime":
                startCountUpTimer(Long.parseLong(data), this.frag.getActivity());
                break;

            case "setWinners6":
                ((TextView) this.frag.getActivity().findViewById(R.id.w6)).setText(data);
                this.frag.getActivity().findViewById(R.id.w6).setVisibility(View.VISIBLE);
                this.frag.getActivity().findViewById(R.id.bar6).setVisibility(View.VISIBLE);
                this.frag.getActivity().findViewById(R.id.p6).setVisibility(View.VISIBLE);
                break;

            case "setWinners5":
                ((TextView) this.frag.getActivity().findViewById(R.id.w5)).setText(data);
                this.frag.getActivity().findViewById(R.id.w5).setVisibility(View.VISIBLE);
                this.frag.getActivity().findViewById(R.id.bar5).setVisibility(View.VISIBLE);
                this.frag.getActivity().findViewById(R.id.p5).setVisibility(View.VISIBLE);
                break;

            case "setWinners4":
                ((TextView) this.frag.getActivity().findViewById(R.id.w4)).setText(data);
                this.frag.getActivity().findViewById(R.id.w4).setVisibility(View.VISIBLE);
                this.frag.getActivity().findViewById(R.id.bar4).setVisibility(View.VISIBLE);
                this.frag.getActivity().findViewById(R.id.p4).setVisibility(View.VISIBLE);
                break;

            case "setWinners3":
                ((TextView) this.frag.getActivity().findViewById(R.id.w3)).setText(data);
                this.frag.getActivity().findViewById(R.id.w3).setVisibility(View.VISIBLE);
                this.frag.getActivity().findViewById(R.id.bar3).setVisibility(View.VISIBLE);
                this.frag.getActivity().findViewById(R.id.p3).setVisibility(View.VISIBLE);
                break;

            case "setWinners2":
                Log.d("revCount", "heyUser" + data);
                ((TextView) this.frag.getActivity().findViewById(R.id.w2)).setText(data);
                this.frag.getActivity().findViewById(R.id.w2).setVisibility(View.VISIBLE);
                this.frag.getActivity().findViewById(R.id.bar2).setVisibility(View.VISIBLE);
                this.frag.getActivity().findViewById(R.id.p2).setVisibility(View.VISIBLE);
                break;

            case "setWinners1":
                ((TextView) this.frag.getActivity().findViewById(R.id.w1)).setText(data);
                this.frag.getActivity().findViewById(R.id.w1).setVisibility(View.VISIBLE);
                this.frag.getActivity().findViewById(R.id.bar1).setVisibility(View.VISIBLE);
                this.frag.getActivity().findViewById(R.id.p1).setVisibility(View.VISIBLE);
                break;

            case "endFrag":
                Log.d("revCount", "heyEnd");
                NavHostFragment.findNavController(this.frag).navigate(R.id.move_to_EndGameFragment);
                break;
            case "updateHouse":
                if (!clientObj.getUser().getUsername().equals(client))
                    ClientPropertyStorage.getInstance().addHouse(data);
                break;
            case "updateHotel":
                if (!clientObj.getUser().getUsername().equals(client))
                    ClientPropertyStorage.getInstance().addHotel(data);
                break;
            case "updateOwner":
                if (!clientObj.getUser().getUsername().equals(client))
                    ClientPropertyStorage.getInstance().updateOwner(data, new Player(client, new Color(), 0, true));
                else
                    Toast.makeText(this.frag.getActivity(),"You just bought "+data,Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void setPlayerPositions() {
        uiHandlerViewModel.setPlayerPositionX(playersX);
        uiHandlerViewModel.setPlayerPositionY(playersY);
        Log.d("CURRENT POSITION X in setPlayer ", "" + uiHandlerViewModel.getPlayerPositionX().getValue()[1]);
        Log.d("CURRENT POSITION Y in setPlayer ", "" + uiHandlerViewModel.getPlayerPositionY().getValue()[1]);
    }

    public void initializePlayerBottomRight(ImageView imageView, int player) {
        //layerDrawable = (LayerDrawable) imageView.getDrawable();

        heightRatio = layerDrawable.getMinimumHeight() / (double) 21000;
        widthRatio = layerDrawable.getMinimumWidth() / (double) 21000;

        if (player <= 3) {
            playersX[player] = (double) 1100 * widthRatio;
        } else {
            playersX[player] = (double) 2000 * widthRatio;
        }

        if (player == 1 || player == 4) {
            playersY[player] = 0;
        } else if (player == 2 || player == 5) {
            playersY[player] = (double) 1000 * widthRatio;
        } else {
            playersY[player] = (double) 1800 * widthRatio;
        }

        //layerDrawable.setLayerGravity(player, Gravity.BOTTOM | Gravity.RIGHT);
        //layerDrawable.setLayerInset(player, 0, 0, (int) playersX[player], (int) playersY[player]);
        playerGravity[player] = Gravity.BOTTOM | Gravity.RIGHT;
        uiHandlerViewModel.setPlayerGravityLiveData(playerGravity);

        setPlayerPositions();

        //imageView.setImageDrawable(layerDrawable);
    }

    public void goFieldBottom(ImageView imageView, int player, int move) {
        //layerDrawable = (LayerDrawable) imageView.getDrawable();

        heightRatio = layerDrawable.getMinimumHeight() / (double) 21000;
        widthRatio = layerDrawable.getMinimumWidth() / (double) 21000;

        goOneSmallField = (double) 1700 * widthRatio;

        //layerDrawable.setLayerGravity(player, Gravity.BOTTOM | Gravity.RIGHT);
        Log.d("CURRENT playersX before", "" + playersX[player]);
        playersX[player] = playersX[player] + (goOneSmallField * move);
        Log.d("CURRENT playersX after", "" + playersX[player]);
        //layerDrawable.setLayerInset(player, 0, 0, (int) playersX[player], (int) playersY[player]);
        playerGravity[player] = Gravity.BOTTOM | Gravity.RIGHT;
        uiHandlerViewModel.setPlayerGravityLiveData(playerGravity);

        Log.d("------------", "" + player);
        Log.d("---playersXAfter---", "" + playersX[player]);
        Log.d("---playersYAfter---", "" + playersY[player]);
        Log.d("------------", "goLeftBottomMethod");

        setPlayerPositions();

        //refresh Image
        //imageView.setImageDrawable(layerDrawable);
    }

    public void initializePlayerBottomLeft(ImageView imageView, int player) {
        //layerDrawable = (LayerDrawable) imageView.getDrawable();

        heightRatio = layerDrawable.getMinimumHeight() / (double) 21000;
        widthRatio = layerDrawable.getMinimumWidth() / (double) 21000;

        double initializeLeftY = (double) 1800 * widthRatio;
        double initializeLeftX = (double) 1200 * widthRatio;

        if (player == 1) {
            double initializeLeft1Y = (double) 2800 * widthRatio;
            //layerDrawable.setLayerGravity(player, Gravity.BOTTOM | Gravity.RIGHT);
            playersY[player] = playersY[player] + initializeLeft1Y;
            //layerDrawable.setLayerInset(player, 0, 0, (int) playersX[player], (int) playersY[player]);
            playerGravity[player] = Gravity.BOTTOM | Gravity.RIGHT;
            uiHandlerViewModel.setPlayerGravityLiveData(playerGravity);
        } else if (player == 4) {
            double initializeLeft4X = (double) 900 * widthRatio;
            double initializeLeft4Y = (double) 3700 * widthRatio;
            //layerDrawable.setLayerGravity(player, Gravity.BOTTOM | Gravity.RIGHT);
            playersX[player] = playersX[player] - initializeLeft4X;
            playersY[player] = playersY[player] + initializeLeft4Y;
            //layerDrawable.setLayerInset(player, 0, 0, (int) playersX[player], (int) playersY[player]);
            playerGravity[player] = Gravity.BOTTOM | Gravity.RIGHT;
            uiHandlerViewModel.setPlayerGravityLiveData(playerGravity);
        } else {
            //layerDrawable.setLayerGravity(player, Gravity.BOTTOM | Gravity.RIGHT);
            playersX[player] = playersX[player] + initializeLeftX;
            playersY[player] = playersY[player] + initializeLeftY;
            //layerDrawable.setLayerInset(player, 0, 0, (int) player3X, (int) player3Y);
            playerGravity[player] = Gravity.BOTTOM | Gravity.RIGHT;
            uiHandlerViewModel.setPlayerGravityLiveData(playerGravity);
            Log.d("---x---", "" + player);
            Log.d("---playersXAfter---", "" + playersX[player]);
            Log.d("---playersYAfter---", "" + playersY[player]);
        }

        setPlayerPositions();
        //imageView.setImageDrawable(layerDrawable);
    }

    public void goFieldLeft(ImageView imageView, int player, int move) {
        //layerDrawable = (LayerDrawable) imageView.getDrawable();

        heightRatio = layerDrawable.getMinimumHeight() / (double) 21000;
        widthRatio = layerDrawable.getMinimumWidth() / (double) 21000;

        goOneSmallField = (double) 1700 * widthRatio;

        //layerDrawable.setLayerGravity(player, Gravity.BOTTOM | Gravity.RIGHT);
        playersY[player] = playersY[player] + (goOneSmallField * move);
        Log.d("------MOVING--", "" + move);
        //layerDrawable.setLayerInset(player, 0, 0, (int) playersX[player], (int) playersY[player]);
        playerGravity[player] = Gravity.BOTTOM | Gravity.RIGHT;
        uiHandlerViewModel.setPlayerGravityLiveData(playerGravity);

        setPlayerPositions();

        //imageView.setImageDrawable(layerDrawable);
    }

    public void initializePlayerTopLeft(ImageView imageView, int player) {
        //layerDrawable = (LayerDrawable) imageView.getDrawable();

        heightRatio = layerDrawable.getMinimumHeight() / (double) 21000;
        widthRatio = layerDrawable.getMinimumWidth() / (double) 21000;

        if (player <= 3) {
            playersX[player] = (double) 1100 * widthRatio;
        } else {
            playersX[player] = (double) 2000 * widthRatio;
        }

        if (player == 1 || player == 4) {
            playersY[player] = 0;
        } else if (player == 2 || player == 5) {
            playersY[player] = (double) 1000 * widthRatio;
        } else {
            playersY[player] = (double) 1800 * widthRatio;
        }

        //layerDrawable.setLayerGravity(player, Gravity.TOP | Gravity.LEFT);
        //layerDrawable.setLayerInset(player, (int) playersX[player], (int) playersY[player], 0, 0);
        playerGravity[player] = Gravity.TOP | Gravity.LEFT;
        uiHandlerViewModel.setPlayerGravityLiveData(playerGravity);

        setPlayerPositions();

        //imageView.setImageDrawable(layerDrawable);
    }

    public void goFieldTop(ImageView imageView, int player, int move) {
        //layerDrawable = (LayerDrawable) imageView.getDrawable();

        heightRatio = layerDrawable.getMinimumHeight() / (double) 21000;
        widthRatio = layerDrawable.getMinimumWidth() / (double) 21000;

        goOneSmallField = (double) 1700 * widthRatio;

        //layerDrawable.setLayerGravity(player, Gravity.TOP | Gravity.LEFT);
        playersX[player] = playersX[player] + (goOneSmallField * move);
        Log.d("THE X", "" + playersX[player]);
        //layerDrawable.setLayerInset(player, (int) playersX[player], (int) playersY[player], 0, 0);
        playerGravity[player] = Gravity.TOP | Gravity.LEFT;
        uiHandlerViewModel.setPlayerGravityLiveData(playerGravity);

        setPlayerPositions();

        //imageView.setImageDrawable(layerDrawable);
    }


    public void initializePlayerTopRight(ImageView imageView, int player) {
        //layerDrawable = (LayerDrawable) imageView.getDrawable();

        heightRatio = layerDrawable.getMinimumHeight() / (double) 21000;
        widthRatio = layerDrawable.getMinimumWidth() / (double) 21000;


        double initializeRightY = (double) 1800 * widthRatio;
        double initializeRightX = (double) 1200 * widthRatio;

        if (player == 1) {
            double initializeRight1Y = (double) 2800 * widthRatio;
            //layerDrawable.setLayerGravity(player, Gravity.TOP | Gravity.LEFT);
            playersY[player] = playersY[player] + initializeRight1Y;
            //layerDrawable.setLayerInset(player, (int) playersX[player], (int) playersY[player], 0, 0);
            playerGravity[player] = Gravity.TOP | Gravity.LEFT;
            uiHandlerViewModel.setPlayerGravityLiveData(playerGravity);
        } else if (player == 4) {
            double initializeRight4X = (double) 900 * widthRatio;
            double initializeRight4Y = (double) 3700 * widthRatio;
            //layerDrawable.setLayerGravity(player, Gravity.TOP | Gravity.LEFT);
            playersX[player] = playersX[player] - initializeRight4X;
            playersY[player] = playersY[player] + initializeRight4Y;
            //layerDrawable.setLayerInset(player, (int)  playersX[player], (int) playersY[player], 0, 0);
            playerGravity[player] = Gravity.TOP | Gravity.LEFT;
            uiHandlerViewModel.setPlayerGravityLiveData(playerGravity);
        } else {
            //layerDrawable.setLayerGravity(player, Gravity.TOP | Gravity.LEFT);
            playersX[player] = playersX[player] + initializeRightX;
            playersY[player] = playersY[player] + initializeRightY;
            //layerDrawable.setLayerInset(player, (int) playersX[player], (int) playersY[player], 0, 0);
            playerGravity[player] = Gravity.TOP | Gravity.LEFT;
            uiHandlerViewModel.setPlayerGravityLiveData(playerGravity);
        }

        setPlayerPositions();

        //imageView.setImageDrawable(layerDrawable);
    }

    public void goFieldRight(ImageView imageView, int player, int move) {
        //layerDrawable = (LayerDrawable) imageView.getDrawable();

        heightRatio = layerDrawable.getMinimumHeight() / (double) 21000;
        widthRatio = layerDrawable.getMinimumWidth() / (double) 21000;

        goOneSmallField = (double) 1700 * widthRatio;

        //layerDrawable.setLayerGravity(player, Gravity.TOP | Gravity.LEFT);
        playersY[player] = playersY[player] + (goOneSmallField * move);
        //layerDrawable.setLayerInset(player, (int) playersX[player], (int) playersY[player], 0, 0);
        playerGravity[player] = Gravity.TOP | Gravity.LEFT;
        uiHandlerViewModel.setPlayerGravityLiveData(playerGravity);

        setPlayerPositions();

        //imageView.setImageDrawable(layerDrawable);
    }


    private void movePlayer(String data) {
        String[] dataResponseSplit = data.split(":");
        if (dataResponseSplit[2].equals("f")) {
            this.frag.getActivity().findViewById(R.id.throwdice).setAlpha(0.5f);        // disable dice throwing after not throwing doubles
            this.frag.getActivity().findViewById(R.id.throwdice).setEnabled(false);
            gameBoardUIViewModel.setThrowDiceEnabled(false);
        }
    }

    private void restore() {
        Log.d("MoneyPlayer", "I am: " + clientObj.getUser().getUsername());
        Log.d("MoneyPlayer", "curr money: " + currentMoney);

        if (this.frag.getActivity().findViewById(R.id.currentMoney) == null)            //check if open
            return;

        currentMoney = uiHandlerViewModel.getCurrentMoney().getValue();

        ((TextView) this.frag.getActivity().findViewById(R.id.currentMoney)).setText("Current Money \n" + currentMoney + "$");
        ((TextView) this.frag.getActivity().findViewById(R.id.turn)).setText(gameBoardUIViewModel.getCurrentPlayer().getValue());

        /**
         * Reconstruction of GameBoardUI
         */
        try {
            gameBoardUIViewModel = new ViewModelProvider(this.frag.requireActivity()).get(GameBoardUIViewModel.class);   // restore GameBoardUI state
            ((TextView) this.frag.getActivity().findViewById(R.id.turn)).setText(gameBoardUIViewModel.getCurrentPlayer().getValue());     // set name for player turn
            if (gameBoardUIViewModel.getUncoverEnabled().getValue()) {
                this.frag.getActivity().findViewById(R.id.uncover).setAlpha(1.0f);
                this.frag.getActivity().findViewById(R.id.uncover).setEnabled(true);
            } else {
                this.frag.getActivity().findViewById(R.id.uncover).setAlpha(0.5f);
                this.frag.getActivity().findViewById(R.id.uncover).setEnabled(false);
            }
            if (gameBoardUIViewModel.getThrowDiceEnabled().getValue()) {
                this.frag.getActivity().findViewById(R.id.throwdice).setAlpha(1.0f);
                this.frag.getActivity().findViewById(R.id.throwdice).setEnabled(true);
            } else {
                this.frag.getActivity().findViewById(R.id.throwdice).setAlpha(0.5f);
                this.frag.getActivity().findViewById(R.id.throwdice).setEnabled(false);
            }
            if (gameBoardUIViewModel.getEndTurnEnabled().getValue()) {
                this.frag.getActivity().findViewById(R.id.endTurn).setAlpha(1.0f);
                this.frag.getActivity().findViewById(R.id.endTurn).setEnabled(true);
            } else {
                this.frag.getActivity().findViewById(R.id.endTurn).setAlpha(0.5f);
                this.frag.getActivity().findViewById(R.id.endTurn).setEnabled(false);
            }

        } catch (Exception e) {
        }

        imageView = this.frag.getActivity().findViewById(R.id.iv_zoom);
        //layerDrawable = (LayerDrawable) imageView.getDrawable();
        layerDrawable = (LayerDrawable) this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard);

        /*if(uiHandlerViewModel.getPlayerPositionX().getValue()!=null){
            playersX = uiHandlerViewModel.getPlayerPositionX().getValue();
            playersY = uiHandlerViewModel.getPlayerPositionY().getValue();
            Log.d("gameTurnCheck","I am: "+clientObj.getUser().getUsername()+"; x is: "+playersX[1]);
        }*/

        playerGravity = uiHandlerViewModel.getPlayerGravityLiveData().getValue();

        layerDrawable.setLayerGravity(player1, playerGravity[1]);
        layerDrawable.setLayerGravity(player2, playerGravity[2]);
        layerDrawable.setLayerGravity(player3, playerGravity[3]);
        layerDrawable.setLayerGravity(player4, playerGravity[4]);
        layerDrawable.setLayerGravity(player5, playerGravity[5]);
        layerDrawable.setLayerGravity(player6, playerGravity[6]);

        Log.d("gravity1231231231", "Host gravity: " + playerGravity[1] + "; Bottom Right: " + (Gravity.BOTTOM | Gravity.RIGHT) + "; Top Left: " + (Gravity.TOP | Gravity.LEFT));
        Log.d("gravity1231231231", "Host X: " + playersX[1] + "; Y: " + playersY[1]);

        if (playerGravity[1] == (Gravity.BOTTOM | Gravity.RIGHT)) {
            layerDrawable.setLayerInset(player1, 0, 0, (int) playersX[1], (int) playersY[1]);
        } else {
            layerDrawable.setLayerInset(player1, (int) playersX[1], (int) playersY[1], 0, 0);
        }
        if (playerGravity[2] == (Gravity.BOTTOM | Gravity.RIGHT)) {
            layerDrawable.setLayerInset(player2, 0, 0, (int) playersX[2], (int) playersY[2]);
        } else {
            layerDrawable.setLayerInset(player2, (int) playersX[2], (int) playersY[2], 0, 0);
        }
        if (playerGravity[3] == (Gravity.BOTTOM | Gravity.RIGHT)) {
            layerDrawable.setLayerInset(player3, 0, 0, (int) playersX[3], (int) playersY[3]);
        } else {
            layerDrawable.setLayerInset(player3, (int) playersX[3], (int) playersY[3], 0, 0);
        }
        if (playerGravity[4] == (Gravity.BOTTOM | Gravity.RIGHT)) {
            layerDrawable.setLayerInset(player4, 0, 0, (int) playersX[4], (int) playersY[4]);
        } else {
            layerDrawable.setLayerInset(player4, (int) playersX[4], (int) playersY[4], 0, 0);
        }
        if (playerGravity[5] == (Gravity.BOTTOM | Gravity.RIGHT)) {
            layerDrawable.setLayerInset(player5, 0, 0, (int) playersX[5], (int) playersY[5]);
        } else {
            layerDrawable.setLayerInset(player5, (int) playersX[5], (int) playersY[5], 0, 0);
        }
        if (playerGravity[6] == (Gravity.BOTTOM | Gravity.RIGHT)) {
            layerDrawable.setLayerInset(player6, 0, 0, (int) playersX[6], (int) playersY[6]);
        } else {
            layerDrawable.setLayerInset(player6, (int) playersX[6], (int) playersY[6], 0, 0);
        }

        //imageView.setImageDrawable(this.frag.getResources().getDrawable(R.drawable.layerlist_for_gameboard));
        imageView.setImageDrawable(layerDrawable);

        try {

            for (int i = 1; i <= 6; i++) {
                if (clientObj.getUser().getId() == 1) {
                    clientObj.writeToServer("GameBoardUI|mapPlayers|" + (int) playersX[i] + ":" + (int) playersY[i] + "," + 1 + "|" + clientObj.getUser().getUsername());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //Log.d("hostPosition","Draw Host at X: "+ layerDrawable.getLayerInsetRight(1));
        //Log.d("hostPosition","Draw Host at Y: "+ layerDrawable.getLayerInsetLeft(1));
    }

    public void startCountUpTimer(long endTime, FragmentActivity activity) {
        final Handler handler = new Handler(Looper.getMainLooper());
        final long[] currentTime = {0};

        long totalEndTimeSeconds = endTime / 1000;
        final long endMinutes = totalEndTimeSeconds / 60;
        final long endSeconds = totalEndTimeSeconds % 60;

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final TextView timerTextView = activity.findViewById(R.id.time);

                        long totalSeconds = currentTime[0];
                        long minutes = totalSeconds / 60;
                        long seconds = totalSeconds % 60;

                        String time = String.format("%02d:%02d | %02d:%02d", minutes, seconds, endMinutes, endSeconds);
                        gameBoardUIViewModel.setCurrentTime(time);
                        if (timerTextView != null) {
                            timerTextView.setText(gameBoardUIViewModel.getCurrentTime().getValue());
                        }

                        if (currentTime[0] < endTime / 1000) {
                            currentTime[0]++;
                            handler.postDelayed(this, 1000);
                        } else {
                            handler.removeCallbacks(this);
                        }

                        if (HostGame.getMonopolyServer() != null) {
                            if (HostGame.getMonopolyServer().getClient().isHost() == true && minutes == endMinutes && seconds == endSeconds) {
                                HostGame.getMonopolyServer().getClient().setGameover(true);
                            }
                        }
                    }
                });
            }
        };

        handler.post(runnable);
    }

}