package com.example.monopoly.ui;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.monopoly.R;

public class UIHandler extends Handler {
    private Fragment frag;

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
            case "playerName":
                ((TextView) this.frag.getActivity().findViewById(R.id.textViewUser1)).setText(data);        // TODO
                break;
        }


        //Toast.makeText(this.frag.getActivity(), msg1, Toast.LENGTH_LONG).show();
    }
}
