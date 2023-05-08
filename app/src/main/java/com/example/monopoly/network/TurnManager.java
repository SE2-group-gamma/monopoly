package com.example.monopoly.network;

import com.example.monopoly.gamelogic.ServerLoop;

import java.util.List;

public class TurnManager {

    private MonopolyServer server;
    private List<ClientHandler> clients;
    private int currentPlayerIndex;
    private ServerLoop serverLoop;

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public MonopolyServer getServer() {
        return server;
    }

    public void setServer(MonopolyServer server) {
        this.server = server;
    }

    public List<ClientHandler> getClients() {
        return clients;
    }

    public void setClients(List<ClientHandler> clients) {
        this.clients = clients;
    }

    public ServerLoop getServerLoop() {
        return serverLoop;
    }

    public void setServerLoop(ServerLoop serverLoop) {
        this.serverLoop = serverLoop;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public TurnManager(MonopolyServer server, List<ClientHandler> clients, ServerLoop serverLoop) {
        this.server = server;
        this.clients = clients;
        this.currentPlayerIndex = 0;
        this.serverLoop = serverLoop;
    }

    public synchronized void addClient(ClientHandler client) {
        this.clients.add(client);
    }

    public synchronized void removeClient(ClientHandler client) {
        this.clients.remove(client);
    }

    public synchronized void handleMessage(ClientHandler client, String message) {
        if (clients.get(currentPlayerIndex).equals(client)) {
            serverLoop.handleMessage(message);
            currentPlayerIndex = (currentPlayerIndex + 1) % clients.size();
        }
    }
}
