package com.example.monopoly.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.R;
import com.example.monopoly.gamelogic.Game;
import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.network.Client;

import java.io.Serializable;

public class UIHandler extends Handler {
    private Fragment frag;
    private int counter=1;
    private int currentPlayerindex=1;
    private Game game = Game.getInstance();
    private Player currentPlayer;

    public UIHandler(Fragment app) {
        this.frag = app;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
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
                currentPlayerindex++;
                if (counter != currentPlayerindex) {
                    disableTextFieldsForPlayer(counter);
                } else{
                    currentPlayer = game.getPlayers().get(currentPlayerindex);
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
            case "displayKey":
                Log.d("tuaaaaaaa","displayKey");
                if(HostGame.key!=0) {
                    ((TextView) this.frag.getActivity().findViewById(R.id.textViewKey)).setText("Game-Key: " + HostGame.key);
                }
                break;
            case "move":
                Log.d("move",data); //Data for move distance and player name
                data="turnEnded";
                msg.getData().putString("Data",data);
                currentPlayer = game.getCurrentPlayer();
                if ( data=="turnEnded" && currentPlayer!=null) {
                    Log.i("move","inside if");
                    int nextPlayerIndex = currentPlayerindex + 1;
                    if (nextPlayerIndex >= game.getPlayers().size()) {
                        nextPlayerIndex = 0; // Wrap around to the first player if reached the end
                    }

                    currentPlayer = game.getPlayers().get(nextPlayerIndex);
                    Log.i("move",currentPlayer.toString());
                    currentPlayerindex = nextPlayerIndex;
                    // Enable text fields for the next player
                    disableTextFieldsForPlayer(currentPlayerindex);
                }
                break;
        }


        //Toast.makeText(this.frag.getActivity(), msg1, Toast.LENGTH_LONG).show();
    }

    private void disableTextFieldsForPlayer(int playerNumber) {
        AppCompatActivity activity = (AppCompatActivity) frag.getActivity();
        TextView textViewName = null;
        TextView textViewUser = null;

        switch (playerNumber) {
            case 1:
                textViewName = activity.findViewById(R.id.textViewUser1Name);
                textViewUser = activity.findViewById(R.id.textViewUser1);
                break;
            case 2:
                textViewName = activity.findViewById(R.id.textViewUser2Name);
                textViewUser = activity.findViewById(R.id.textViewUser2);
                break;
            case 3:
                textViewName = activity.findViewById(R.id.textViewUser3Name);
                textViewUser = activity.findViewById(R.id.textViewUser3);
                break;
            case 4:
                textViewName = activity.findViewById(R.id.textViewUser4Name);
                textViewUser = activity.findViewById(R.id.textViewUser4);
                break;
            case 5:
                textViewName = activity.findViewById(R.id.textViewUser5Name);
                textViewUser = activity.findViewById(R.id.textViewUser5);
                break;
        }

        if (textViewName != null && textViewUser != null) {
            if (playerNumber == currentPlayerindex) {
                textViewName.setEnabled(true);
                textViewUser.setEnabled(true);
            } else {
                textViewName.setEnabled(false);
                textViewUser.setEnabled(false);
            }
        }
    }
}
