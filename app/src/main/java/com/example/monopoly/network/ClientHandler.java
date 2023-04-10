package com.example.monopoly.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientHandler extends Thread{

    private Socket client;
    private BufferedReader br;
    private BufferedWriter bw;

    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            this.br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
