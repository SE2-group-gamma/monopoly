package com.example.monopoly.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientHandler extends Thread{

    private Socket client;
    private BufferedReader br;
    private BufferedWriter bw;

    private volatile boolean turn;

    private TurnManager turnManager;

    public ClientHandler(Socket client, TurnManager turnManager) {
        this.client = client;
        this.turnManager = turnManager;
        this.turn = false;
    }

    public void giveTurn() {
        turn = true;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public OutputStream getOutputStream() throws IOException {
        return client.getOutputStream();
    }


    @Override
    public void run() {
        try {
            this.br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            turnManager.handleClientHandlerTurn(this, this.br, this.bw);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
