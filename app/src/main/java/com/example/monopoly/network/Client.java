package com.example.monopoly.network;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.ui.UIHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Client extends Thread {
    private InetAddress host;
    private int port;
    private Socket clientSocket;
    private String response;
    private String request;
    private Player user;
    private DataOutputStream outToServer;
    private ArrayList<String> msgBuffer;

    private static HashMap<String, UIHandler> handlers;
    static{
        handlers = new HashMap<>();
    }

    public InetAddress getHost() {
        return host;
    }

    public void setUser(Player user) {
        this.user = user;
    }

    public void writeToServer(String msg) throws IOException {
        synchronized (msgBuffer) {
            msgBuffer.add(msg);
        }
    }

    /***
     * Subscribe Fragment to handler
     * @param frag
     * @param type
     */
    public static void subscribe(Fragment frag, String type){
        handlers.put(type,new UIHandler(frag));
    }

    public Client(InetAddress host, int port, Player user) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.msgBuffer = new ArrayList<>();
    }
    public Client(InetAddress host, int port) {
        this.host = host;
        this.port = port;
        this.msgBuffer = new ArrayList<>();
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

            //Network Protocol: [Fragment Name]|[Action]|[Data]
            //Could also use OP-Codes

            if(host != null && port != 0)
                clientSocket = new Socket(host, port);
            else
                clientSocket = new Socket("localhost",6969);

            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while(true) {

                //outToServer.writeBytes(request + 'n');
                if(inFromServer.ready()) {
                    response = inFromServer.readLine();

                    String[] responseSplit = response.split("\\|");

                    Thread.sleep(100);

                    if (handlers.containsKey(responseSplit[0])) {
                        android.os.Message handleMessage = new Message();
                        Bundle b = new Bundle();
                        b.putString("ActionType", responseSplit[1]);
                        b.putString("Data", responseSplit[2]);
                        handleMessage.setData(b);
                        handlers.get(responseSplit[0]).sendMessage(handleMessage);
                    }
                }
                synchronized (msgBuffer){
                    if(msgBuffer.size()!=0){
                        for(int i = msgBuffer.size()-1; i >= 0; i--){
                            Log.d("msgBuffer",msgBuffer.get(i));
                            outToServer.writeBytes(msgBuffer.get(i)+System.lineSeparator());
                            outToServer.flush();
                            msgBuffer.remove(i);
                        }
                    }
                }
            }
            //clientSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
