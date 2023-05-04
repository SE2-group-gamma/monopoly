package com.example.monopoly.ui;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.R;

public class UIHandler extends Handler {
    private Fragment frag;
    private int counter=1;

    public UIHandler(Fragment app) {
        this.frag = app;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        String data = msg.getData().get("Data").toString();
        String type = msg.getData().get("ActionType").toString();

        switch (type) {
            case "changeText":
                ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser1)).setText(data);
                break;
            case "userJoined":
                switch (counter){
                    case 1:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser1)).setText(data);
                        break;
                    case 2:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser2)).setText(data);
                        break;
                    case 3:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser3)).setText(data);
                        break;
                    case 4:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser4)).setText(data);
                        break;
                    case 5:
                        ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser5)).setText(data);
                        break;
                }
                counter++;
                break;
            case "hostJoined":
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
                NavHostFragment.findNavController(frag)
                        .navigate(R.id.action_JoinGame_to_GameBoard);
                break;
            case "displayKey":
                Log.d("tuaaaaaaa","displayKey");
                if(HostGame.key!=0) {
                    ((TextView) this.frag.getActivity().findViewById(R.id.textViewKey)).setText("Game-Key: " + HostGame.key);
                }
        }


        //Toast.makeText(this.frag.getActivity(), msg1, Toast.LENGTH_LONG).show();
    }
}
