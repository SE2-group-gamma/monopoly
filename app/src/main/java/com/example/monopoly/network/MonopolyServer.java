package com.example.monopoly.network;

import android.util.Log;

import com.example.monopoly.ui.HostGame;
import com.example.monopoly.ui.NSD_Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MonopolyServer extends Thread{

    private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    private int localPort;
    private int maxNumberOfClients;
    private boolean isListening;
    private String hostname;
    private Client client;
    private int counter=1;


    private HashMap<Integer, ClientHandler> keyedHandlers;

    public MonopolyServer(int maxNumberOfClients) throws IOException {
        this.serverSocket = new ServerSocket(6969);
        this.localPort = serverSocket.getLocalPort();
        this.maxNumberOfClients = maxNumberOfClients;
        this.clients = new ArrayList<ClientHandler>();
        this.isListening = false;
        this.keyedHandlers=new HashMap<>();

    }

    public void setHostname(String hostname){
        this.hostname=hostname;
    }

    public void setClient(Client client){
        synchronized (this.clients){
            this.client=client;

            for (ClientHandler handler:this.clients) {
                handler.setClient(this.client);
            }
        }
    }

    public Client getClient() {
        return client;
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
        this.keyedHandlers=new HashMap<>();

    }

    public void broadCast(String msg){
        synchronized (this.clients) {
            for (ClientHandler clientHandler : clients) {
                clientHandler.writeToClient(msg);
            }
        }
    }

    public void broadCastExceptSelf(String msg, ClientHandler hostHandler){
        synchronized (this.clients) {
            for (ClientHandler clientHandler : clients) {
                if(hostHandler != clientHandler)
                    clientHandler.writeToClient(msg);
            }
        }
    }

    @Override
    public void run() {
        this.isListening = true;
        int count = 0;
        while(isListening() && this.clients.size() < maxNumberOfClients){
            ClientHandler clientHandler = null;

            try {
                // serverSocket.accept() waits for Clients to connect
                Socket socket = serverSocket.accept();
                clientHandler = new ClientHandler(socket,hostname,client);
                clientHandler.setServer(this);
                count++;

                String message = "#" + count + " from "
                        + socket.getInetAddress() + ":"
                        + socket.getPort() + "\n";
            } catch (SocketException e) {
                if (!serverSocket.isClosed()) {
                    Log.e("MonopolyServer", "Error accepting socket connection", e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            clientHandler.start();
            synchronized (this.clients){
                this.clients.add(clientHandler);
                keyedHandlers.put(counter++,clientHandler);
            }

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

    public synchronized void closeConnectionsAndShutdown() {
        try {
            this.broadCast("GameBoardUI|endFrag");
            clients.clear();
            keyedHandlers.clear();
            stopListening();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeClientConnection() {
        ClientHandler clientHandler= null;

        try{
            Socket socket = new Socket();
            clientHandler = new ClientHandler(socket);
            clientHandler.getSocket().close();
        }catch (IOException e){
            e.printStackTrace();
        }

        NSD_Client nsdClient = new NSD_Client();
        nsdClient.stopDiscovery();

    }

    //for testing
    public void setNSDClient(NSD_Client mockNSDClient) {
    }
}
