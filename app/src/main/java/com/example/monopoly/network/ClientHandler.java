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
import java.util.ArrayList;
import java.util.HashMap;

public class ClientHandler extends Thread{

    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;

    private String hostname;
    Client client;
    private ArrayList<String> msgBuffer;

    private MonopolyServer server;

    public void setServer(MonopolyServer server) {
        this.server = server;
    }

    private Object clientToken="";

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void setClient(Client client) {
        synchronized (clientToken){
            this.client = client;
        }
    }

    public ClientHandler(Socket socket, String hostname, Client client) {
        this.hostname = hostname;
        this.socket=socket;
        this.client=client;
        this.msgBuffer=new ArrayList<>();
    }

    public Socket getClient() {
        return socket;
    }

    public Client getClientClient() {
        return client;
    }

    @Override
    public void run() {
        try {
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //bw.write("Lobby|changeText|Martin Jäger"+System.lineSeparator());
            //bw.flush();




            while(true){
                if(br.ready()){
                    String msg = br.readLine();
                    String[] strings = msg.split("\\|");
                    synchronized (clientToken){
                        String[] response = client.handleMessage(strings);
                        if(response!=null){
                            for (String str: response) {


                                bw.write(str.replaceAll("REPLACER",hostname));
                                bw.flush();


                            }
                        }

                    }
                    Log.d("msg123",msg);

                }
                synchronized (msgBuffer) {
                    if (msgBuffer.size() != 0) {
                        for (int i = msgBuffer.size() - 1; i >= 0; i--) {
                            Log.d("msgBuffer", msgBuffer.get(i));
                            bw.write(msgBuffer.get(i) + System.lineSeparator());
                            bw.flush();
                            msgBuffer.remove(i);
                        }
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeToClient(String msg) {
        synchronized (msgBuffer) {
            msgBuffer.add(msg);
        }
    }


}
