package com.example.monopoly.network;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.monopoly.gamelogic.Game;
import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.ui.HostGame;
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
    // besser wenn getrennt in host und client über abstrakte klasse
    private InetAddress host;
    private int id;     //Useless I think
    private int port;
    private Socket clientSocket;
    private String response;
    private String request;
    private Player user;
    public DataOutputStream outToServer;
    public ArrayList<String> msgBuffer;
    private MonopolyServer monopolyServer;
    private boolean isHost;
    private int key;

    private int serverTurnCounter = 0;

    private boolean turnEnd =false;

    //private boolean canSendRequests;



    private Game game;

    public static HashMap<String, UIHandler> handlers;

    static {
        handlers = new HashMap<>();
    }

    public MonopolyServer getMonopolyServer() {
        return monopolyServer;
    }

    public void setMonopolyServer(MonopolyServer monopolyServer) {
        this.monopolyServer = monopolyServer;
    }

    public InetAddress getHost() {
        return host;
    }

    public Game getGame() {
        return game;
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

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return id;
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
                            //Log.d("msgBuffer", msgBuffer.get(i));
                            outToServer.writeBytes(msgBuffer.get(i) + System.lineSeparator());
                            outToServer.flush();
                            msgBuffer.remove(i);
                        }
                    }
                }

                if(turnEnd==true){
                    turnPocess();
                }
            }
            //clientSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }



    public String[] handleMessage(String[] responseSplit) {
        if (handlers.containsKey(responseSplit[0])) {
            android.os.Message handleMessage = new Message();
            Bundle b = new Bundle();
            b.putString("ActionType", responseSplit[1]);
            b.putString("Data", responseSplit[2]);
            try {
                if (responseSplit[3] != null) {
                    b.putString("Client", responseSplit[3]);
                }
            }catch (Exception e){}
            //b.putSerializable("clientObject",this);
            handleMessage.setData(b);
            handlers.get(responseSplit[0]).sendMessage(handleMessage);      // UI Handler do ur thing
        }

        if (isHost) {
            // TODO: call game logic
            // e.g. responseSplit[1] to throw dice
            if (responseSplit[0].equals("CLIENTMESSAGE") && responseSplit[1].equals("key")) {
                //Log.d("",monopolyServer.getClients().size()+"");
                int keyReceived = Integer.parseInt(responseSplit[2]);
                if (key == keyReceived) {

                    //monopolyServer.getClients().get(0).writeToClient("JoinLobby|keyFromLobby|1");
                    // TODO make this with IDs instead (properly)
                    return new String[]{"JoinGame|keyFromLobby|1","Lobby|hostJoined|"+"REPLACER"};

                } else {

                    //monopolyServer.getClients().get(0).writeToClient("JoinLobby|keyFromLobby|0");
                    return new String[]{"JoinGame|keyFromLobby|0","Lobby|hostJoined|"+"REPLACER"};

                }
            }
            try {
                Log.d("Dices", "Message to host: " + responseSplit[0] + "; " + responseSplit[1] + "; " + responseSplit[2] + "; " + responseSplit[3]);
            }catch (Exception e){}
            game = Game.getInstance();
            //Host should only join once
            if(responseSplit[1].equals("hostJoined") && game.getPlayers().isEmpty()){       //Host should only join once

                Player tempPlayer = new Player(responseSplit[2],new Color(),500.00,true);
                //Log.i("Dices","Host gonna join: ");
                game.addPlayer(tempPlayer);

            }
            if(responseSplit[1].equals("JOINED")){
                synchronized (monopolyServer.getClients()){
                    /*for (ClientHandler handler: monopolyServer.getClients()) {

                        handler.writeToClient("Lobby|userJoined|"+responseSplit[2]);

                    }*/
                    monopolyServer.broadCast("Lobby|userJoined|"+responseSplit[2]);
                    monopolyServer.broadCast("Lobby|hostJoined|"+monopolyServer.getClientsTurn().get(0).getUser().getUsername());
                    Player tempPlayer = new Player(responseSplit[2],new Color(),500.00,true);
                    //Log.i("Dices","Client Gonna join: ");
                    //game = Game.getInstance();
                    game.addPlayer(tempPlayer);

                }
            }




            //if(game.getCurrentPlayersTurn()== user.getUsername()){
            if(responseSplit[1].equals("move")){
                int tempID = game.getPlayerIDByName(responseSplit[3]);
                if(game.getCurrentPlayersTurn().equals(responseSplit[3])) {
                    game.incrementPlayerPosition(tempID, Integer.parseInt(responseSplit[2]));
                    Log.d("gameturnCurr", "currPlayer" + game.getCurrentPlayersTurn());
                    Log.d("gameturnCurr", "currUser" + responseSplit[3]);
                }
            }//}

            if(responseSplit[1].equals("gameStart")){
                Log.d("gameRevCheck", "Yo hey"+game.getPlayers().get(0).getUsername());
                //Log.d("gameRevCheck", "Yo hey"+game.getPlayers().get(1).getUsername());
                turnPocess();

            }
        } else {
            for (String str: responseSplit) {
                //Log.d("poggies123 ",str);
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
                try {
                    writeToServer("Lobby|hostJoined|"+"REPLACER");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }


    public void turnPocess(){
        turnEnd = false;
        game.setCurrentPlayersTurn(game.getPlayers().get(serverTurnCounter).getUsername());
        monopolyServer.broadCast("GameBoardUI|playersTurn|"+game.getPlayers().get(serverTurnCounter).getUsername());
        Log.d("gameTurnCheck", "Yo hey"+game.getCurrentPlayersTurn());
        serverTurnCounter++;
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {

                        turnEnd = true;
                    }
                },
                60000
        );
        if(serverTurnCounter== HostGame.getMonopolyServer().getNumberOfClients()){
            serverTurnCounter=0;
        }
    }
}
