package com.example.monopoly.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.R;
import com.example.monopoly.network.Client;
import com.example.monopoly.ui.viewmodels.ClientViewModel;
import com.example.monopoly.ui.viewmodels.GameBoardUIViewModel;

public class UIHandler extends Handler {
    private Fragment frag;
    private int counter = 1;

    private String hostname = "";
    private int counterMove = 0;
    private Client client;
    private ClientViewModel clientViewModel;
    private GameBoardUIViewModel gameBoardUIViewModel;



    //private boolean uncoverEnabled;
    public UIHandler(Fragment app) {
        this.frag = app;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        clientViewModel = new ViewModelProvider(frag.requireActivity()).get(ClientViewModel.class);
        gameBoardUIViewModel = new ViewModelProvider(frag.requireActivity()).get(GameBoardUIViewModel.class);   // GameBoardUI state


        if(gameBoardUIViewModel.getCurrentMoney().getValue()!=null){
            ((TextView) this.frag.getActivity().findViewById(R.id.currentMoney)).setText(gameBoardUIViewModel.getCurrentMoney().getValue());
        }

        if(gameBoardUIViewModel.getCurrentTime().getValue()!=null){
            ((TextView) this.frag.getActivity().findViewById(R.id.time)).setText(gameBoardUIViewModel.getCurrentTime().getValue());
        }



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
                    case 1:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser1)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser1Name)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser1)).setText(data);
                        break;
                    case 2:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser2)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser2Name)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser2)).setText(data);
                        break;
                    case 3:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser3)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser3Name)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser3)).setText(data);
                        break;
                    case 4:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser4)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser4Name)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser4)).setText(data);
                        break;
                    case 5:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser5)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser5Name)).setVisibility(View.VISIBLE);
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser5)).setText(data);
                        break;
                }
                counter++;
                break;
            case "hostJoined":
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
                break;
            case "displayKey":
                Log.d("tuaaaaaaa", "displayKey");
                if (HostGame.key != 0) {
                    ((TextView) this.frag.getActivity().findViewById(R.id.textViewKey)).setText("Game-Key: " + HostGame.key);
                }
                break;
            case "move":
                movePlayer(data);
                Log.d("move", data); //Data for move distance and player name
                break;

            case "movePlayer":
                movePlayer(data);
                break;



            case "playersTurn":
                Log.d("gameTurnCheck","; "+NavHostFragment.findNavController(this.frag).getCurrentDestination().getLabel());

                //NavHostFragment.findNavController(this.frag).getCurrentDestination().

                if(!"GameBoardUI".equals(NavHostFragment.findNavController(this.frag).getCurrentDestination().getLabel())) {
                    //NavHostFragment.findNavController(this.frag).navigate(R.id.move_to_GameBoardUI);
                    //Navigation.findNavController(this.frag.getActivity().findViewById(NavHostFragment.findNavController(this.frag).getCurrentDestination().getId())).navigate(R.id.move_to_GameBoardUI);
                    //NavHostFragment.findNavController(this.frag.getActivity().getFragmentManager().findFragmentById(NavHostFragment.findNavController(this.frag).getCurrentDestination().getId())).navigate(R.id.move_to_GameBoardUI);
                    //Objects.requireNonNull(this.frag.getActivity()).getSupportFragmentManager().popBackStack();
                }

                ((TextView) this.frag.getActivity().findViewById(R.id.turn)).setText(data + "'s turn");
                gameBoardUIViewModel.setCurrentPlayer(data + "'s turn");
                Log.d("ButtonGreyCheck", "Here is Button" + this.client.getUser().getUsername());
                if (data.equals(this.client.getUser().getUsername())) {
                    Log.d("ButtonGreyCheck2", "VERY NICE INDEED");
                    this.frag.getActivity().findViewById(R.id.throwdice).setAlpha(1.0f);
                    this.frag.getActivity().findViewById(R.id.throwdice).setEnabled(true);
                    this.frag.getActivity().findViewById(R.id.uncover).setAlpha(0.5f);
                    this.frag.getActivity().findViewById(R.id.uncover).setEnabled(false);
                    gameBoardUIViewModel.setUncoverEnabled(false);
                } else {
                    this.frag.getActivity().findViewById(R.id.throwdice).setAlpha(0.5f);
                    this.frag.getActivity().findViewById(R.id.throwdice).setEnabled(false);
                    this.frag.getActivity().findViewById(R.id.uncover).setAlpha(1.0f);
                    this.frag.getActivity().findViewById(R.id.uncover).setEnabled(true);
                    gameBoardUIViewModel.setUncoverEnabled(true);
                }
                break;

            case "exitDiceFragment":

                NavHostFragment.findNavController(this.frag).navigate(R.id.move_to_GameBoardUI);
                    //Thread.sleep(1000);

                break;

            case "setStartTime":
                startCountUpTimer(Long.parseLong(data), this.frag.getActivity());
                break;

            case"setWinners6":
                ((TextView)this.frag.getActivity().findViewById(R.id.w6)).setText(data);
                this.frag.getActivity().findViewById(R.id.w6).setEnabled(true);
                this.frag.getActivity().findViewById(R.id.bar6).setEnabled(true);
                this.frag.getActivity().findViewById(R.id.p6).setEnabled(true);
                break;

            case"setWinners5":
                ((TextView)this.frag.getActivity().findViewById(R.id.w5)).setText(data);
                this.frag.getActivity().findViewById(R.id.w5).setEnabled(true);
                this.frag.getActivity().findViewById(R.id.bar5).setEnabled(true);
                this.frag.getActivity().findViewById(R.id.p5).setEnabled(true);
                break;

            case"setWinners4":
                ((TextView)this.frag.getActivity().findViewById(R.id.w4)).setText(data);
                this.frag.getActivity().findViewById(R.id.w4).setEnabled(true);
                this.frag.getActivity().findViewById(R.id.bar4).setEnabled(true);
                this.frag.getActivity().findViewById(R.id.p4).setEnabled(true);
                break;

            case"setWinners3":
                ((TextView)this.frag.getActivity().findViewById(R.id.w3)).setText(data);
                this.frag.getActivity().findViewById(R.id.w3).setEnabled(true);
                this.frag.getActivity().findViewById(R.id.bar3).setEnabled(true);
                this.frag.getActivity().findViewById(R.id.p3).setEnabled(true);
                break;

            case"setWinners2":
                ((TextView)this.frag.getActivity().findViewById(R.id.w2)).setText(data);
                this.frag.getActivity().findViewById(R.id.w2).setEnabled(true);
                this.frag.getActivity().findViewById(R.id.bar2).setEnabled(true);
                this.frag.getActivity().findViewById(R.id.p2).setEnabled(true);
                break;

            case"setWinners1":
                ((TextView)this.frag.getActivity().findViewById(R.id.w1)).setText(data);
                this.frag.getActivity().findViewById(R.id.w1).setEnabled(true);
                this.frag.getActivity().findViewById(R.id.bar1).setEnabled(true);
                this.frag.getActivity().findViewById(R.id.p1).setEnabled(true);
                break;

            case"endFrag":
                NavHostFragment.findNavController(this.frag).navigate(R.id.move_to_EndGameFragment);
                break;
        }

        //Toast.makeText(this.frag.getActivity(), msg1, Toast.LENGTH_LONG).show();
    }

    private void movePlayer(String data){
        String[] dataResponseSplit = data.split(":");
        if (dataResponseSplit[2].equals("f")) {
            Log.d("move", "Im in: "+counterMove++);
            this.frag.getActivity().findViewById(R.id.throwdice).setAlpha(0.5f);        // disable dice throwing after not throwing doubles
            this.frag.getActivity().findViewById(R.id.throwdice).setEnabled(false);
        }
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
                        if(timerTextView!=null)
                        {
                            timerTextView.setText(gameBoardUIViewModel.getCurrentTime().getValue());
                        }

                        if (currentTime[0] < endTime / 1000) {
                            currentTime[0]++;
                            handler.postDelayed(this, 1000);
                        } else {
                            handler.removeCallbacks(this);
                        }

                        if(HostGame.getMonopolyServer()!=null){
                        if(HostGame.getMonopolyServer().getClient().isHost()==true&&minutes==endMinutes&&seconds==endSeconds){
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
