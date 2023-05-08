package com.example.monopoly.gamelogic;

public class ServerLoop extends Thread {

    private boolean isRunning;

    public ServerLoop() {
        this.isRunning = false;
    }

    @Override
    public void run() {
        this.isRunning = true;

        while (isRunning) {
            // Game logic for the server
        }
    }

    public void stopLoop() {
        this.isRunning = false;
    }

    public void handleMessage(String message) {
        // Handle incoming messages from the clients
    }
}
