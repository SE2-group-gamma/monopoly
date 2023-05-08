package com.example.monopoly.network;

import com.example.monopoly.gamelogic.ClientLoop;

import java.io.BufferedReader;
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
    private ClientLoop clientLoop;

    public Client(InetAddress host, int port) {
        this.host = host;
        this.port = port;
        this.clientLoop = new ClientLoop(this);
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

    public void startClientLoop() {
        this.clientLoop.start();
    }

    public void stopClientLoop() {
        this.clientLoop.stopLoop();
    }

    public ClientLoop getClientLoop() {
        return clientLoop;
    }

    public void run() {
        try {
            clientSocket = new Socket(host, port);

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            startClientLoop();

            while (true) {
                String message = inFromServer.readLine();
                if (message == null) {
                    break;
                }
                this.clientLoop.handleMessage(message);
            }

            stopClientLoop();

            clientSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
