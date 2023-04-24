package com.example.monopoly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ServerActivity extends AppCompatActivity {
    private Server server;
    private TextView serverLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        serverLog = findViewById(R.id.serverLog);
        Button startServerButton = findViewById(R.id.startServerButton);

        startServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                server = new Server(serverLog);
                server.start();
            }
        });
    }
}
