package com.example.monopoly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClientActivity extends AppCompatActivity {
    private Client client;
    private TextView clientLog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        clientLog = findViewById(R.id.clientLog);
        Button startClientButton = findViewById(R.id.startClientButton);

        startClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client = new Client(clientLog);
                client.start();
            }
        });

        Button rolldicebutton = findViewById(R.id.roll_dice_button);

        rolldicebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.setRoll(true);
                System.out.println(client.isRoll());
            }
        });
    }


}
