package com.example.monopoly.network;

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
        this.msgBuffer=new ArrayList<>();
    }


    public Socket getSocket() {
        return socket;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public void run(){
        try {
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while(true){
                replacer();
                readMsgBuffer();

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
                String[] strings = msg.split("\\|");
                synchronized (clientToken){
                    String[] response = client.handleMessage(strings);
                    if(response!=null){
                        for (String str: response) {
                            server.broadCast(str.replaceAll("REPLACER",hostname));
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void readMsgBuffer(){
        synchronized (msgBuffer) {
            if (msgBuffer.size() != 0) {
                for (int i = msgBuffer.size() - 1; i >= 0; i--) {
                    try {
                        bw.write(msgBuffer.get(i)+ System.lineSeparator());
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
