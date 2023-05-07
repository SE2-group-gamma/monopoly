package com.example.monopoly.network;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.ui.UIHandler;

import com.example.monopoly.gamelogic.ChanceCard;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Client extends Thread {
    private InetAddress host;
    private int port;
    private Socket clientSocket;
    private String response;
    private String request;
    private Player user;
    public DataOutputStream outToServer;
    public ArrayList<String> msgBuffer;
    private MonopolyServer monopolyServer;


    private int key;

    public static HashMap<String, UIHandler> handlers;

    static {
        handlers = new HashMap<>();
    }

    public MonopolyServer getMonopolyServer() {
        return monopolyServer;
    }

    private boolean isHost;

    public void setMonopolyServer(MonopolyServer monopolyServer) {
        this.monopolyServer = monopolyServer;
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
    public static void subscribe(Fragment frag, String type) {
        handlers.put(type, new UIHandler(frag));
    }

    public Client(InetAddress host, int port, Player user, boolean isHost) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.msgBuffer = new ArrayList<>();
        this.isHost = isHost;
    }

    public Client(InetAddress host, int port, boolean isHost) {
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

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Player getUser() {
        return user;
    }

    public int getKey() {
        return key;
    }


    public void run() {
        try {

            //Network Protocol: [Fragment Name]|[Action]|[Data]
            //Could also use OP-Codes

            //checkHostAndPort();
            if (host != null && port != 0)
                clientSocket = new Socket(host, port);
            else
                clientSocket = new Socket("localhost", 6969);

            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Thread.sleep(100);
            if(!isHost){
                writeToServer("CLIENTMESSAGE|key|" + key);
            }else{
                handleMessage("Lobby|displayKey| ".split("\\|"));
            }


            while (true) {

                //outToServer.writeBytes(request + 'n');
                if (inFromServer.ready()) {
                    response = inFromServer.readLine();

                    String[] responseSplit = response.split("\\|");

                    Thread.sleep(100);

                    handleMessage(responseSplit);
                }
                synchronized (msgBuffer) {
                    if (msgBuffer.size() != 0) {
                        for (int i = msgBuffer.size() - 1; i >= 0; i--) {
                            Log.d("msgBuffer", msgBuffer.get(i));
                            outToServer.writeBytes(msgBuffer.get(i) + System.lineSeparator());
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



    public String[] handleMessage(String[] responseSplit) {
        if (handlers.containsKey(responseSplit[0])) {
            android.os.Message handleMessage = new Message();
            Bundle b = new Bundle();
            b.putString("ActionType", responseSplit[1]);
            b.putString("Data", responseSplit[2]);
            handleMessage.setData(b);
            handlers.get(responseSplit[0]).sendMessage(handleMessage);
        }

        if (isHost) {
            // TODO: call game logic
            // e.g. responseSplit[1] to throw dice
            if (responseSplit[0].equals("CLIENTMESSAGE") && responseSplit[1].equals("key")) {
                Log.d("",monopolyServer.getClients().size()+"");
                int keyReceived = Integer.parseInt(responseSplit[2]);
                if (key == keyReceived) {

                    //monopolyServer.getClients().get(0).writeToClient("JoinLobby|keyFromLobby|1");      // TODO make this with IDs instead (properly)
                    return new String[]{"JoinGame|keyFromLobby|1"+System.lineSeparator(),"Lobby|hostJoined|"+"REPLACER"+System.lineSeparator()};

                } else {

                    //monopolyServer.getClients().get(0).writeToClient("JoinLobby|keyFromLobby|0");
                    return new String[]{"JoinGame|keyFromLobby|0"+System.lineSeparator(),"Lobby|hostJoined|"+"REPLACER"+System.lineSeparator()};

                }
            }
            if(responseSplit[1].equals("JOINED")){
                synchronized (monopolyServer.getClients()){
                    for (ClientHandler handler: monopolyServer.getClients()) {

                        handler.writeToClient("Lobby|userJoined|"+responseSplit[2]);

                    }
                }
            }
        } else {
            for (String str: responseSplit) {
               // Log.d("test ",str);
            }
            if (responseSplit[1].equals("keyFromLobby") && responseSplit[2].equals("1")) {
                try {
                    writeToServer("Lobby|JOINED|" + user.getUsername());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(responseSplit[1].equals("hostJoined")){
                //writeToServer();
            }
        }
        return null;
    }
}
