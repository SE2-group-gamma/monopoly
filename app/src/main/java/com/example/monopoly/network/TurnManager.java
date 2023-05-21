package com.example.monopoly.network;

import java.util.List;



//Client Handler->client->player->username==responsesplit3 take move
// accept or not accept

public class TurnManager {
    private List<ClientHandler> clients;
    private int currentTurnIndex = 0;

    public TurnManager(List<ClientHandler> clients) {
        this.clients = clients;
    }

    public void startTurn() {
        // Set the flag on the current client to allow them to send requests.
       // if (clients.get(currentTurnIndex).getClientClient() != null) {

        for(ClientHandler h : this.clients){
           System.out.println(h.getClientClient().getUser().getUsername()+"WHHHHHHHHHHHHHHHHHHHHYYY");

        }
            clients.get(currentTurnIndex).getClientClient().setCanSendRequests(true);
       // }


        // Start a timer for 60 seconds.
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        endTurn();
                    }
                },
                60000
        );
    }

    public void endTurn() {
        // Set the flag on the current client to prevent them from sending requests.
        clients.get(currentTurnIndex).getClientClient().setCanSendRequests(false);

        // Move to the next client in the list (wrapping around if necessary).
        currentTurnIndex = (currentTurnIndex + 1) % clients.size();

        // Start the next turn.
        startTurn();
    }
}