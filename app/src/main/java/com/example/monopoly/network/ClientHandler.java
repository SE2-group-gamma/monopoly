package com.example.monopoly.network;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.monopoly.ui.UIHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;

public class ClientHandler extends Thread{

    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;

    private String hostname;
    Client client;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void setClient(Client client) {
        synchronized (this.client){
            this.client = client;
        }
    }

    public ClientHandler(Socket socket, String hostname, Client client) {
        this.hostname = hostname;
        this.socket=socket;
        this.client=client;
    }

    public Socket getClient() {
        return socket;
    }

    @Override
    public void run() {
        try {
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //bw.write("Lobby|changeText|Martin Jäger"+System.lineSeparator());
            //bw.flush();
            bw.write("Lobby|hostJoined|"+hostname+System.lineSeparator());
            bw.flush();


            while(true){
                if(br.ready()){
                    String msg = br.readLine();
                    String[] strings = msg.split("\\|");
                    synchronized (client){
                        client.handleMessage(strings);
                    }
                    Log.d("msg123",msg);
                    bw.write(msg+System.lineSeparator());
                    bw.flush();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
