package com.example.monopoly.gamelogic;

import com.example.monopoly.network.Client;

public class ClientLoop extends Thread {

    private boolean isRunning;
    private Client client;

    public ClientLoop(Client client) {
        this.isRunning = false;
        this.client = client;
    }

    @Override
    public void run() {
        this.isRunning = true;

        while (isRunning) {
            // Game logic for the client
        }
    }

    public void stopLoop() {
        this.isRunning = false;
    }

    public void handleMessage(String message) {
        // Handle incoming messages from the server
    }
}
