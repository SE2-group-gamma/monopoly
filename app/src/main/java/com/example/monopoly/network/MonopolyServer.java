package com.example.monopoly.network;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MonopolyServer extends Thread{

    private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    private int localPort;
    private int maxNumberOfClients;
    private boolean isListening;

    public MonopolyServer(int maxNumberOfClients) throws IOException {
        this.serverSocket = new ServerSocket(6969);
        this.localPort = serverSocket.getLocalPort();
        this.maxNumberOfClients = maxNumberOfClients;
        this.clients = new ArrayList<ClientHandler>();
        this.isListening = false;
    }

    public List<ClientHandler> getClients() {
        return clients;
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
        int count = 0;
        //Log.d("",""+this.maxNumberOfClients);
        while(isListening() && this.clients.size() < maxNumberOfClients){
            ClientHandler clientHandler = null;
            try {
                // serverSocket.accept() waits for Clients to connect
                Socket socket = serverSocket.accept();
                clientHandler = new ClientHandler(socket);
                count++;

                String message = "#" + count + " from "
                        + socket.getInetAddress() + ":"
                        + socket.getPort() + "\n";
                Log.d("SocketConn",message);
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

}
