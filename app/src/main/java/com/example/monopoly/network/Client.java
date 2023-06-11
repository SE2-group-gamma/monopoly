package com.example.monopoly.network;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.monopoly.gamelogic.Bank;
import com.example.monopoly.gamelogic.ChanceCardCollection;
import com.example.monopoly.gamelogic.CommunityChestCard;
import com.example.monopoly.gamelogic.CommunityChestCardCollection;
import com.example.monopoly.gamelogic.Game;
import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.ui.DrawCardFragment;
import com.example.monopoly.ui.HostGame;
import com.example.monopoly.ui.MainActivity;
import com.example.monopoly.ui.UIHandler;

import com.example.monopoly.gamelogic.ChanceCard;
import com.example.monopoly.ui.viewmodels.CardViewModel;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

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
    boolean onCoolDown = false;

    private int serverTurnCounter = 0;

    private boolean turnEnd =false;

    private Game game;
    private String cheated;
    private Bank bank;

    public static HashMap<String, UIHandler> handlers;
    private CardViewModel cardViewModel;
    private Timer timer;

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
            this.cheated = "f";
            //Network Protocol: [Fragment Name]|[Action]|[Data]
            //Could also use OP-Codes
            //New Protocoll: Fragment|action|data:additionalData|sender
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

                if(turnEnd){
                    turnProcess();
                }
            }

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
            handleMessage.setData(b);
            handlers.get(responseSplit[0]).sendMessage(handleMessage);      // UI Handler do ur thing
        }



        if (isHost) {
            String[] dataResponseSplit = responseSplit[2].split(":");
            // TODO: call game logic
            // e.g. responseSplit[1] to throw dice
            if (responseSplit[0].equals("CLIENTMESSAGE") && responseSplit[1].equals("key")) {
                Log.d("",monopolyServer.getClients().size()+"");
                Log.d("Dices","A");
                int keyReceived = Integer.parseInt(dataResponseSplit[0]);
                Log.d("Dices","key:"+keyReceived);
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
                Player tempPlayer = new Player(dataResponseSplit[0],new Color(),500.00,true);
                Log.i("Dices","Host gonna join: ");
                game.addPlayer(tempPlayer);
            }
            if(responseSplit[1].equals("JOINED")){
                synchronized (monopolyServer.getClients()){
                    monopolyServer.broadCast("Lobby|userJoined|"+responseSplit[2]);
                    monopolyServer.broadCast("Lobby|hostJoined|"+monopolyServer.getClient().getUser().getUsername());
                    Player tempPlayer = new Player(responseSplit[2],new Color(),500.00,true);
                    Log.i("Dices","Client Gonna join: ");
                    game.addPlayer(tempPlayer);

                }
            }

            if(responseSplit[1].equals("initializePlayerBottomRight") && (!onCoolDown)){
                onCoolDown = true;
                monopolyServer.broadCast("GameBoardUI|initializePlayerBottomRight1| : |"+responseSplit[3]);
                Timer cdTimer = new Timer();
                cdTimer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                onCoolDown = false;
                            }
                        },
                        300
                );
            }
            if(responseSplit[1].equals("turnEnd")){
                Log.d("endTurn","end turn test");
                endTurnPressed();
            }
            if(responseSplit[1].equals("move")){
                // TODO sent player to jail after 3 doubles
                // data: 8:t:f  => increment:cheated:double
                cheated = dataResponseSplit[1];
                int tempID = game.getPlayerIDByName(responseSplit[3]);
                if(game.getCurrentPlayersTurn().equals(responseSplit[3])) {
                    game.incrementPlayerPosition(tempID, Integer.parseInt(dataResponseSplit[0]));
                    //Log.d("gameturnCurr", "currPlayer" + game.getCurrentPlayersTurn());
                    //Log.d("gameturnCurr", "currUser" + responseSplit[3]);
                    monopolyServer.broadCast("GameBoardUI|movePlayer|"+responseSplit[2]+"|"+responseSplit[3]);      // broadcast with different action to not interfere with game logic
                }
            }//}

            if (responseSplit[1].equals("transferToPlayer")) {
                int receiverID = game.getPlayerIDByName(responseSplit[3]);
                int amount = Integer.parseInt(dataResponseSplit[0]);
                if (game.getCurrentPlayersTurn().equals(responseSplit[3])) {
                    bank.transferMoneyBankToPlayer(game.getPlayers().get(receiverID), bank, amount);
                    Log.i("MoneyTransfer", "Player: " + game.getPlayers().get(receiverID).getUsername() + ", New Capital: " +
                            game.getPlayers().get(receiverID).getCapital());
                }
            }
            if (responseSplit[1].equals("transferPlayerToPlayer")) {
                //[Fragment]|transferPlayerToPlayer|[receiverID]:[amount]|[senderUserName]
                int senderID = game.getPlayerIDByName(responseSplit[3]);
                int receiverID = Integer.parseInt(dataResponseSplit[0]);
                int amount = Integer.parseInt(dataResponseSplit[1]);
                if (game.getCurrentPlayersTurn().equals(responseSplit[3])) {
                    game.getPlayers().get(senderID).
                            transferMoneyPlayerToPlayer(game.getPlayers().get(senderID), game.getPlayers().get(receiverID), amount);
                    Log.i("MoneyTransfer", game.getPlayers().get(senderID).getUsername()
                            + " -> " + game.getPlayers().get(receiverID).getUsername() + " : $" + amount);
                }
            }
            if (responseSplit[1].equals("outOfJailCounter")) {
                //[Fragment]|outOfJailFree|[amount]|[senderUserName]
                int playerID = game.getPlayerIDByName(responseSplit[3]);
                int amount = Integer.parseInt(dataResponseSplit[0]);
                if (game.getCurrentPlayersTurn().equals(responseSplit[3])) {
                    game.getPlayers().get(playerID).setOutOfJailFreeCounter(game.getPlayers().get(playerID).getOutOfJailFreeCounter()+amount);
                }
            }
            if (responseSplit[1].equals("transferToBank")) {
                int senderID = game.getPlayerIDByName(responseSplit[3]);
                int amount = Integer.parseInt(dataResponseSplit[0]);
                if (game.getCurrentPlayersTurn().equals(responseSplit[3])) {
                    bank.transferMoneyPlayerToBank(game.getPlayers().get(senderID), bank, amount);
                    Log.i("MoneyTransfer", "Player: " + game.getPlayers().get(senderID).getUsername() + ", New Capital: " +
                            game.getPlayers().get(senderID).getCapital());
                }
            }
            if (responseSplit[1].equals("setCard")) {
                //[Fragment]|setImage|[imageID]|[senderUserName]
                int playerID = game.getPlayerIDByName(responseSplit[3]);
                int cardID = Integer.parseInt(dataResponseSplit[0]);
                if (game.getCurrentPlayersTurn().equals(responseSplit[3])) {
                    game.getPlayers().get(playerID).setCardID(cardID);
                }
            }
            if (responseSplit[1].equals("removeCardBroadcast")) {
                //[Fragment]|removeCardBroadcast|[CardID]:[CardType]|[senderUserName]
                int cardID = Integer.parseInt(dataResponseSplit[0]);
                String cardType = dataResponseSplit[1];
                synchronized (monopolyServer.getClients()) {
                    monopolyServer.broadCast("GameBoardUI|removeCard|" + cardID + ":" + cardType + "|" + monopolyServer.getClient().getUser().getUsername());
                }
            }
            if(responseSplit[1].equals("gameStart")){
                Log.d("gameRevCheck", "Yo hey"+game.getPlayers().get(0).getUsername());
                //Log.d("gameRevCheck", "Yo hey"+game.getPlayers().get(1).getUsername());
                turnProcess();
            }
            if(responseSplit[1].equals("uncover")){         // Only 1 player should be able to uncover, else others will just chime in
                try{
                    Log.d("uncover","Who: "+responseSplit[3]);
                    if(this.cheated.equals("t")){       // TODO if cheated punish current player (Reference should be saved in Host)
                        Log.d("Dices","Gschummelt->"+cheated);
                    } else {                                    // TODO if not punish sender
                        Log.d("Dices","Ois OK->"+cheated);
                    }
                }catch (Exception e){

                }
            }
            if(responseSplit[1].equals("endTurn")){
                // TODO next player turn
            }
        } else {
            for (String str: responseSplit) {
            }
            String[] dataResponseSplit = responseSplit[2].split(":");

            if (responseSplit[1].equals("keyFromLobby") && responseSplit[2].equals("1")) {
                try {
                    writeToServer("Lobby|JOINED|" + user.getUsername());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(responseSplit[1].equals("hostJoined")){
                try {
                    writeToServer("Lobby|hostJoined|"+"REPLACER");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (responseSplit[1].equals("removeCard")) {
                //[Fragment]|removeCard|[CardID]:[CardType]|[senderUserName]

                Context appContext = DrawCardFragment.getAppContext();
                cardViewModel = new ViewModelProvider((ViewModelStoreOwner) appContext).get(CardViewModel.class);

                int cardID = Integer.parseInt(dataResponseSplit[0]);
                String cardType = dataResponseSplit[1];
                if (cardType.equals("chance")) {
                    ChanceCard card = cardViewModel.getChanceCards().getValue().getAllChanceCards().get(cardID);
                    if (cardViewModel.getChanceCards().getValue().getChanceCardDeck().contains(card)) {
                        cardViewModel.getChanceCards().getValue().getChanceCardDeck().
                                remove(cardViewModel.getChanceCards().getValue().getChanceCardDeck().get(cardID));
                    }
                } else if (cardType.equals("community")) {
                    CommunityChestCard card = cardViewModel.getCommunityCards().getValue().getAllCommunityChestCards().get(cardID);
                    if (cardViewModel.getCommunityCards().getValue().getCommunityChestCardDeck().contains(card)) {
                        cardViewModel.getCommunityCards().getValue().getCommunityChestCardDeck().
                                remove(cardViewModel.getCommunityCards().getValue().getCommunityChestCardDeck().get(cardID));
                    }
                }
            }
        }
        return null;
    }


    public void turnProcess(){
        turnEnd = false;
        game.setCurrentPlayersTurn(game.getPlayers().get(serverTurnCounter).getUsername());
        monopolyServer.broadCast("GameBoardUI|playersTurn|"+game.getPlayers().get(serverTurnCounter).getUsername());
        //Log.d("gameTurnCheck", "Yo hey "+game.getCurrentPlayersTurn());
        serverTurnCounter++;
        timer = new Timer();

        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        monopolyServer.broadCast("GameBoardUI|exitDiceFragment|:|");   // send exit signal // crashes if any other fragment is open (only if the dice frag hasn't been opened before)
                    }
                },
                15000 - 10
        );
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        turnEnd = true;
                    }
                },
                15000
        );
        if(serverTurnCounter== HostGame.getMonopolyServer().getNumberOfClients()){
            serverTurnCounter=0;
        }
    }


    public void endTurnPressed(){
        monopolyServer.broadCast("GameBoardUI|exitDiceFragment|:|");             // if endTurn is pressed the game will crash if someone is in another fragment
        //timer.cancel();
        Timer fragChange = new Timer();
        fragChange.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        timer.cancel();
                        turnProcess();
                    }
                },
                10
        );
        //turnProcess();
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
