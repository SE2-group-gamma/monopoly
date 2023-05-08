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
    private TurnManager turnManager;

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public ClientHandler(Socket client, TurnManager turnManager) {
        this.client = client;
        this.turnManager = turnManager;
    }

    public OutputStream getOutputStream() throws IOException {
        return client.getOutputStream();
    }

    @Override
    public void run() {
        try {
            this.br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            this.turnManager.addClient(this);
            while (true) {
                String message = br.readLine();
                if (message == null) {
                    break;
                }
                turnManager.handleMessage(this, message);
            }

        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                this.turnManager.removeClient(this);
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


