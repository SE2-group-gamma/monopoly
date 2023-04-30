package com.example.monopoly.gamelogic;

import com.example.monopoly.network.TurnManager;

public class ServerLoop extends Thread {
    private TurnManager turnManager;

    public ServerLoop(TurnManager turnManager) {
        this.turnManager = turnManager;
    }

    @Override
    public void run() {

    }
}