package com.example.monopoly.network;

import android.util.Log;

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

    private TurnManager turnManager;

    public MonopolyServer(int maxNumberOfClients) throws IOException {
        this.serverSocket = new ServerSocket(0);
        this.localPort = serverSocket.getLocalPort();
        this.maxNumberOfClients = maxNumberOfClients;
        this.clients = new ArrayList<ClientHandler>();
        this.isListening = false;
    }

    // Constructor for testing
    public MonopolyServer(int maxNumberOfClients, ServerSocket serverSocket) throws IOException {
        this.serverSocket = serverSocket;
        this.localPort = this.serverSocket.getLocalPort();
        this.maxNumberOfClients = maxNumberOfClients;
        this.clients = new ArrayList<ClientHandler>();
        this.isListening = false;
    }

    @Override
    public void run() {
        this.isListening = true;
        this.turnManager = new TurnManager(this.clients);
        ServerLoop serverLoop = new ServerLoop(this.turnManager);
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


        this.turnManager.start();
        serverLoop.start();
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

}
