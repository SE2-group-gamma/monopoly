package com.example.monopoly.network;

import com.example.monopoly.gamelogic.ClientLoop;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread {
    private InetAddress host;
    private int port;
    private Socket clientSocket;
    private String response;
    private String request;

    private TurnManager turnManager;


    public Client(InetAddress host, int port, TurnManager turnManager) {
        this.host = host;
        this.port = port;
        this.turnManager = turnManager;
    }

    public Client(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void run() {
        try {

            clientSocket = new Socket(host, port);

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


            outToServer.writeBytes(request + 'n');
            response = inFromServer.readLine();

            turnManager.handleClientTurn(this, inFromServer, outToServer);
            ClientLoop clientLoop = new ClientLoop(this);
            clientLoop.start();


            clientSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
