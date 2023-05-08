package com.example.monopoly.network;

import com.example.monopoly.gamelogic.ServerLoop;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class MonopolyServer extends Thread{

    private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    private int localPort;
    private int maxNumberOfClients;
    private boolean isListening;
    private ServerLoop serverLoop;
    private TurnManager turnManager;

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public MonopolyServer(int maxNumberOfClients) throws IOException {
        this.serverSocket = new ServerSocket(0);
        this.localPort = serverSocket.getLocalPort();
        this.maxNumberOfClients = maxNumberOfClients;
        this.clients = new ArrayList<ClientHandler>();
        this.isListening = false;
        this.serverLoop = new ServerLoop();
        this.turnManager = new TurnManager(this, clients, serverLoop);
    }

    // Constructor for testing
    public MonopolyServer(int maxNumberOfClients, ServerSocket serverSocket) throws IOException {
        this.serverSocket = serverSocket;
        this.localPort = this.serverSocket.getLocalPort();
        this.maxNumberOfClients = maxNumberOfClients;
        this.clients = new ArrayList<ClientHandler>();
        this.isListening = false;
        this.serverLoop = new ServerLoop();
        this.turnManager = new TurnManager(this, clients, serverLoop);
    }

    @Override
    public void run() {
        this.isListening = true;

        while(isListening() && this.clients.size() < maxNumberOfClients){
            ClientHandler clientHandler = null;
            try {
                // serverSocket.accept() waits for Clients to connect
                clientHandler = new ClientHandler(serverSocket.accept(), this.turnManager);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            clientHandler.start();
            this.clients.add(clientHandler);
        }

        try {
            stopListening();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void stopListening() throws IOException {
        this.isListening = false;
        this.serverSocket.close();
    }

    public synchronized boolean isListening() {
        return isListening;
    }

    public synchronized int getNumberOfClients(){
        return this.clients.size();
    }

    public int getLocalPort() {
        return localPort;
    }

    public void startServerLoop() {
        this.serverLoop.start();
    }

    public void stopServerLoop() {
        this.serverLoop.stopLoop();
    }

    public ServerLoop getServerLoop() {
        return serverLoop;
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }
}
