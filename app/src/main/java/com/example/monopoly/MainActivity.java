package com.example.monopoly;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button serverButton = findViewById(R.id.serverButton);
        Button clientButton = findViewById(R.id.clientButton);

        serverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serverIntent = new Intent(MainActivity.this, ServerActivity.class);
                startActivity(serverIntent);
            }
        });

        clientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clientIntent = new Intent(MainActivity.this, ClientActivity.class);
                startActivity(clientIntent);
            }
        });
    }
}
