package com.example.monopoly.gamelogic;

import com.example.monopoly.network.Client;

public class ClientLoop extends Thread {
    private Client client;

    public ClientLoop(Client client) {
        this.client = client;
    }

    @Override
    public void run() {

    }
}