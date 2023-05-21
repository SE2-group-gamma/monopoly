package com.example.monopoly.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread{

    private Socket socket;

    public BufferedReader br;
    public BufferedWriter bw;

    private String hostname;
    private Client client;


    private String clientName;
    public ArrayList<String> msgBuffer;

    public MonopolyServer server;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

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
        //this.clientName=client.getName();
        this.msgBuffer=new ArrayList<>();

    }

    public String getClientName() {
        return clientName;
    }

    public Socket getClient() {
        return socket;
    }

    public Client getClientClient() {
        return client;
    }



    @Override
    public void run(){
        try {
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //game = new Game();

            //bw.write("Lobby|changeText|Martin Jäger"+System.lineSeparator());
            //bw.flush();
            while(true){
                replacer();
                msgBuffer();

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

    public void replacer(){
        try {
            if(br.ready()){
                String msg = br.readLine();
                //Log.d("testOut",msg);
                String[] strings = msg.split("\\|");
                synchronized (clientToken){
                    //Log.d("testOut","AAAAAAAAAAAAAAAAAAA");
                    String[] response = client.handleMessage(strings);
                    if(response!=null){
                        for (String str: response) {
                            //bw.write(str.replaceAll("REPLACER",hostname));
                            System.out.println("WHHHHHHHHHYYYY"+hostname);
                            server.broadCast(str.replaceAll("REPLACER",hostname));
                            //server.broadCastExceptSelf(str.replaceAll("REPLACER",hostname),this);
                            //bw.flush();
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void msgBuffer(){
        synchronized (msgBuffer) {
            if (msgBuffer.size() != 0) {
                for (int i = msgBuffer.size() - 1; i >= 0; i--) {
                    //Log.d("msgBuffer", msgBuffer.get(i));
                    Log.d("testOut",""+msgBuffer.get(i)+":"+hostname);
                    try {
                        bw.write(msgBuffer.get(i)/*+"|"+hostname */+ System.lineSeparator());
                        bw.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    msgBuffer.remove(i);
                }
            }
        }

    }




}
