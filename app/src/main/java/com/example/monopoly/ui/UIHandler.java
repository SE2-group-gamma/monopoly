package com.example.monopoly.ui;

import android.app.Activity;
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
                NavHostFragment.findNavController(frag)
                        .navigate(R.id.action_JoinGame_to_GameBoard);
                break;
            case "goField":
                Log.d("------------","goField");
                ImageView imageView = this.frag.getActivity().findViewById(R.id.iv_zoom);
                LayerDrawable layerDrawable = (LayerDrawable) imageView.getDrawable();

                DisplayMetrics displayMetrics = new DisplayMetrics();
                ((Activity) this.frag.getContext()).getWindowManager()
                        .getDefaultDisplay()
                        .getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;

                Log.d("-----------",""+height);
                Log.d("-----------",""+width);

                double heightRatio = (double) height / 1440;
                double widthRatio = (double) width / 3120;

                double goOneBigFieldHorizontal = (double)2800*widthRatio;
                double goOneSmallFieldHorizontal = (double)1700*widthRatio;

                double player2X = 0;
                double player2Y = (double)1000*widthRatio;

                layerDrawable.setLayerGravity(GameBoardUI.player2, Gravity.BOTTOM | Gravity.RIGHT);
                layerDrawable.setLayerInset(GameBoardUI.player2, 0,0,(int)player2X,(int)player2Y);
                player2X = player2X + goOneBigFieldHorizontal + (goOneSmallFieldHorizontal*5);
                layerDrawable.setLayerInset(GameBoardUI.player2, 0,0,(int)player2X,(int)player2Y);

                imageView.postInvalidate(); // oder postInvalidate

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
