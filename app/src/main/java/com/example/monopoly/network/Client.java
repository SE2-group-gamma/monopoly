package com.example.monopoly.network;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.monopoly.R;
import com.example.monopoly.gamelogic.Board;
import com.example.monopoly.gamelogic.Game;
import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.gamelogic.PlayerMapPosition;
import com.example.monopoly.gamelogic.properties.PropertyStorage;
import com.example.monopoly.ui.HostGame;
import com.example.monopoly.ui.UIHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Client extends Thread {
    private static final String GAME_BOARD_UI_CHANGE_CAPITAL = "GameBoardUI|changeCapital|";
    private static final String WATER_WORKS = "water_works";
    private static final String KELAG = "kelag";
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

    private List<Player> playerList;
    private List<Player> winnerList;
    private List<Player> tempList;

    private int serverTurnCounter = 0;

    private boolean joinGame = true;

    private boolean turnEnd = false;

    private Game game;
    private String cheated;
    public static HashMap<String, UIHandler> handlers;
    private Timer timer;

    private boolean buttonCheck=false;

    private String lastPlayerMoved;

    private PropertyStorage propertyStorage;

    static {
        handlers = new HashMap<>();
    }

    private boolean gameover = false;

    private boolean gameStart = false;

    private boolean gamerank = true;

    private ArrayList<String> lobbyref = new ArrayList<String>();

    public void setMonopolyServer(MonopolyServer monopolyServer) {
        this.monopolyServer = monopolyServer;
    }

    public void setGameover(boolean gameover) {
        this.gameover = gameover;
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
        this.propertyStorage = PropertyStorage.getInstance();
        this.playerList = new ArrayList<>();
        this.winnerList = new ArrayList<>();
        this.tempList = new ArrayList<>();
    }

    public Client(InetAddress host, int port, boolean isHost) {
        this.host = host;
        this.port = port;
        this.msgBuffer = new ArrayList<>();
        this.playerList = new ArrayList<>();
        this.winnerList = new ArrayList<>();
        this.tempList = new ArrayList<>();
        this.propertyStorage = PropertyStorage.getInstance();
    }

    public void setId(int id) {
        this.id = id;
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
            this.lastPlayerMoved = "";
            //Old Network Protocol: [Fragment Name]|[Action]|[Data]
            //Could also use OP-Codes
            //New Protocol: Fragment|action|data:additionalData|sender
            if (host != null && port != 0)
                clientSocket = new Socket(host, port);
            else
                clientSocket = new Socket("localhost", 6969);

            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Thread.sleep(100);
            if (!isHost) {
                writeToServer("CLIENTMESSAGE|key|" + key+"|"+user.getUsername());
            } else {
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
                    for (int i = msgBuffer.size() -1; i >= 0; i--) {
                        outToServer.writeBytes(msgBuffer.get(i) + System.lineSeparator());
                        outToServer.flush();
                        msgBuffer.remove(i);
                    }
                }

                if (turnEnd) {
                    turnProcess();
                }
                if (gameStart == true&&isHost) {
                    setRanks(HostGame.getPlayerCount());
                }
            }

        } catch(IOException io) {
            throw new RuntimeException();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException();
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                    monopolyServer.closeConnectionsAndShutdown();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String[] handleMessage(String[] responseSplit) throws Exception {
        if (handlers.containsKey(responseSplit[0])) {
            android.os.Message handleMessage = new Message();
            Bundle b = new Bundle();
            b.putString("ActionType", responseSplit[1]);
            b.putString("Data", responseSplit[2]);
            try {
                if (responseSplit[3] != null) {
                    b.putString("Client", responseSplit[3]);
                }
            } catch (Exception e) {
            }
            handleMessage.setData(b);
            handlers.get(responseSplit[0]).sendMessage(handleMessage);
        }


        if (isHost) {
            String[] dataResponseSplit = responseSplit[2].split(":");
            if (responseSplit[0].equals("CLIENTMESSAGE") && responseSplit[1].equals("key")) {
                int keyReceived = Integer.parseInt(dataResponseSplit[0]);
                if (key == keyReceived) {
                    return new String[]{"JoinGame|keyFromLobby|1|"+responseSplit[3]};
                } else {
                    return new String[]{"JoinGame|keyFromLobby|0", "Lobby|hostJoined|" + "REPLACER"};
                }
            }
            game = Game.getInstance();
            //Host should only join once
            if (responseSplit[1].equals("hostJoined") && game.getPlayers().isEmpty()) {
                Player tempPlayer = new Player(dataResponseSplit[0], new Color(), 1500, true);
                game.addPlayer(tempPlayer);
            }
            if (responseSplit[1].equals("JOINED")) {
                synchronized (monopolyServer.getClients()) {
                    lobbyref.add(responseSplit[2]);
                    int indexref = 1;
                    for(String users : lobbyref) {
                        monopolyServer.broadCast("Lobby|userJoined"+indexref+"|" + users);
                        indexref++;
                    }
                    monopolyServer.broadCast("Lobby|hostJoined|" + monopolyServer.getClient().getUser().getUsername());
                    Player tempPlayer = new Player(responseSplit[2], new Color(), 1500, true);
                    game.addPlayer(tempPlayer);

                }
            }
            if (responseSplit[1].equals("initializePlayerBottomRight") && (!onCoolDown)) {
                onCoolDown = true;
                monopolyServer.broadCast("GameBoardUI|initializePlayerBottomRight1| : |" + responseSplit[3]);
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
            if (responseSplit[1].equals("turnEnd")) {
                endTurnPressed();
            }
            if (responseSplit[1].equals("move")) {
                int tempID = game.getPlayerIDByName(responseSplit[3]);
                if(game.getCurrentPlayersTurn().equals(responseSplit[3])) {
                    this.cheated = dataResponseSplit[1];
                    this.lastPlayerMoved = responseSplit[3];
                    try {
                        game.incrementPlayerPosition(tempID, Integer.parseInt(dataResponseSplit[0]));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    monopolyServer.broadCast("GameBoardUI|movePlayer|" + responseSplit[2] + "|" + responseSplit[3]);      // broadcast with different action to not interfere with game logic
                }
            }
            if (responseSplit[1].equals("outOfJailCounter")) {
                //[Fragment]|outOfJailFree|[amount]|[senderUserName]
                int playerID = game.getPlayerIDByName(responseSplit[3]);
                Player player = game.getPlayers().get(playerID);
                int amount = Integer.parseInt(dataResponseSplit[0]);
                player.setOutOfJailFreeCounter(player.getOutOfJailFreeCounter()+amount);
            }
            if (responseSplit[1].equals("transferToBank")) {
                int id = game.getPlayerIDByName(responseSplit[3]);
                Player player = game.getPlayers().get(id);
                int money = Integer.parseInt(dataResponseSplit[0]);
                double capital = player.getCapital();
                player.setCapital(capital + money);
                monopolyServer.broadCast(GAME_BOARD_UI_CHANGE_CAPITAL + responseSplit[2] + "|" + responseSplit[3]);
            }
            if (responseSplit[1].equals("gameStart")) {
                turnProcess();

                monopolyServer.broadCast("GameBoardUI|setStartTime|" + HostGame.getMaxTimeMin() * 60000);

                this.playerList = new ArrayList<>(game.getPlayers().values());
                this.gameStart = true;
            }
            if (responseSplit[1].equals("setPlayers")) {
                monopolyServer.broadCast("GameBoardUI|setPlayerCount|" + HostGame.getPlayerCount());
            }
            if(responseSplit[1].equals("uncover") && !(this.lastPlayerMoved.isEmpty()) && !(responseSplit[3].equals(this.lastPlayerMoved))){         // player cant punish himself, or no player
                try{
                    int idPunisher = game.getPlayerIDByName(responseSplit[3]);  //sender
                    Player punisher = game.getPlayers().get(idPunisher);

                    int idPunished = game.getPlayerIDByName(this.lastPlayerMoved);  //last moved player
                    Player punished = game.getPlayers().get(idPunished);

                    if(this.cheated.equals("t")){       // punish last moved player
                        monopolyServer.broadCast("GameBoardUI|uncoverUsed|t:"+this.lastPlayerMoved+"|"+responseSplit[3]);   // punish success

                        punished.setCapital(punished.getCapital()-200);
                        monopolyServer.broadCast(GAME_BOARD_UI_CHANGE_CAPITAL + "-200|" +this.lastPlayerMoved);

                        punisher.setCapital(punisher.getCapital()+200);
                        monopolyServer.broadCast(GAME_BOARD_UI_CHANGE_CAPITAL + "200|" +responseSplit[3]);
                    } else {                                    // punish sender
                        monopolyServer.broadCast("GameBoardUI|uncoverUsed|f:"+this.lastPlayerMoved+"|"+responseSplit[3]);   // punish failed

                        punished.setCapital(punished.getCapital()+200);
                        monopolyServer.broadCast(GAME_BOARD_UI_CHANGE_CAPITAL + "200|" +this.lastPlayerMoved);

                        punisher.setCapital(punisher.getCapital()-200);
                        monopolyServer.broadCast(GAME_BOARD_UI_CHANGE_CAPITAL + "-200|" +responseSplit[3]);
                    }
                } catch (Exception e) {

                }
            }
            if (responseSplit[1].equals("giveMoney")) {
                int id = game.getPlayerIDByName(responseSplit[3]);
                Player player = game.getPlayers().get(id);

                int money = Integer.parseInt(dataResponseSplit[0]);
                double capital = player.getCapital();

                player.setCapital(capital + money);

                monopolyServer.broadCast(GAME_BOARD_UI_CHANGE_CAPITAL + responseSplit[2] + "|" + responseSplit[3]);

            }
            if (responseSplit[1].equals("checkRent")) {
                String[] splitter = responseSplit[2].split(":");
                int playerId = game.getPlayerIDByName(responseSplit[3]);
                int fieldsToMove = Integer.parseInt(splitter[1]);

                Player player = game.getPlayers().get(playerId);
                String fieldName;
                assert player != null;
                if(player.getPosition()==40){
                    fieldName = Board.getFieldName(0);
                    player.setPosition(0);
                }
                else if(player.getPosition()>=40){
                    int fieldNewRound = player.getPosition()-40;
                    fieldName = Board.getFieldName(fieldNewRound);
                    player.setPosition(fieldNewRound);
                }else{
                    fieldName = Board.getFieldName(player.getPosition());
                }

                if(Objects.equals(fieldName, "income_tax")){
                    double capital = player.getCapital();
                    int newCapital=0;
                    if(capital*0.1 > 200){
                        player.setCapital(capital - 200);
                        newCapital = 200;
                    }else {
                        player.setCapital(capital * 0.9);
                        newCapital = (int) (capital * 0.1);
                    }
                    monopolyServer.broadCast(GAME_BOARD_UI_CHANGE_CAPITAL + "-" +newCapital+"|" + responseSplit[3]);
                }
                if(Objects.equals(fieldName, "luxury_tax")){
                    double capital = player.getCapital();
                    player.setCapital(capital - 75);
                    monopolyServer.broadCast(GAME_BOARD_UI_CHANGE_CAPITAL + "-" +75+"|" + responseSplit[3]);
                }

                if(propertyStorage.hasField(fieldName)){
                    int rent = 0;
                    if((Objects.equals(fieldName, WATER_WORKS) || Objects.equals(fieldName, KELAG)) && propertyStorage.getOwner(WATER_WORKS)!=null && propertyStorage.getOwner(WATER_WORKS).equals(propertyStorage.getOwner(KELAG))){
                        rent = fieldsToMove*10;
                    }
                    else if(Objects.equals(fieldName, WATER_WORKS)){
                        if(propertyStorage.getOwner(WATER_WORKS)!=null){
                            rent = fieldsToMove*4;
                        } else{
                            rent = 0;
                        }
                    } else if (Objects.equals(fieldName, KELAG)) {
                        if(propertyStorage.getOwner(KELAG)!=null){
                            rent = fieldsToMove*4;
                        } else{
                            rent = 0;
                        }
                    } else {
                        rent = propertyStorage.getRentOnPropertyField(fieldName,player);
                    }

                    if(rent!=0){
                        double capital = player.getCapital();
                        player.setCapital(capital - rent);

                        String playerOwner = propertyStorage.getOwnerName(fieldName);
                        if(!(Objects.equals(playerOwner, ""))){
                            Player owner = game.getPlayers().get(game.getPlayerIDByName(playerOwner));
                            if(!owner.getUsername().equals(player.getUsername())){
                                double capitalOwner = owner.getCapital();
                                owner.setCapital(capitalOwner + rent);
                                rent=rent*(-1);
                                monopolyServer.broadCast(GAME_BOARD_UI_CHANGE_CAPITAL + rent + ":"+playerOwner+"|" + responseSplit[3]);
                            }
                        }
                    }
                }


            }
            if (responseSplit[1].equals("mapPlayers")) {
                int id = game.getPlayerIDByName(responseSplit[3]);
                Player player = game.getPlayers().get(id);
                int posX = Integer.parseInt(dataResponseSplit[0]);
                String[] dataY = dataResponseSplit[1].split(",");
                int posY = Integer.parseInt(dataY[0]);
                int round = Integer.parseInt(dataY[1]);
                PlayerMapPosition playerMapPosition = new PlayerMapPosition(posX, posY, round);
                player.setPlayerMapPosition(playerMapPosition);
            }
            if (responseSplit[1].equals("addHouse")) {
                String fieldName = dataResponseSplit[0];
                Player player = game.getPlayers().get(game.getPlayerIDByName(responseSplit[3]));
                propertyStorage.addHouse(fieldName, player);
                monopolyServer.broadCast("GameBoardUI|updateHouse|" + fieldName + "|" + player.getUsername());
            }
            if (responseSplit[1].equals("addHotel")) {
                String fieldName = dataResponseSplit[0];
                Player player = game.getPlayers().get(game.getPlayerIDByName(responseSplit[3]));
                propertyStorage.addHotel(fieldName, player);
                monopolyServer.broadCast("GameBoardUI|updateHotel|" + fieldName + "|" + player.getUsername());
            }
            if (responseSplit[1].equals("buyField")) {
                String fieldName = dataResponseSplit[0];
                Player player = game.getPlayers().get(game.getPlayerIDByName(responseSplit[3]));
                propertyStorage.buyProperty(fieldName, player);
                monopolyServer.broadCast("GameBoardUI|updateOwner|" + fieldName + "|" + player.getUsername());
            }
            if (responseSplit[1].equals("cardDrawn")) {
                Player player = game.getPlayers().get(game.getPlayerIDByName(responseSplit[3]));
                String cardID = dataResponseSplit[0];
                monopolyServer.broadCast("GameBoardUI|cardDrawn|" + cardID + "|" + player.getUsername());
            }
        } else {
            if (responseSplit[1].equals("keyFromLobby") && responseSplit[2].equals("1")) {
                try {
                    if(this.joinGame) {
                        writeToServer("Lobby|JOINED|" + user.getUsername());
                        this.joinGame=false;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }


    public void turnProcess(){
        setButtonCheck(true);
        turnEnd = false;
        while (game.getPlayers().get(serverTurnCounter).isBroke() == true) {
            serverTurnCounter++;
            if (serverTurnCounter > HostGame.getMonopolyServer().getNumberOfClients()) {
                serverTurnCounter = 0;
            }
        }
        game.setCurrentPlayersTurn(game.getPlayers().get(serverTurnCounter).getUsername());
        monopolyServer.broadCast("GameBoardUI|playersTurn|" + game.getPlayers().get(serverTurnCounter).getUsername());
        serverTurnCounter++;
        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {

                        turnEnd = true;
                        monopolyServer.broadCast("DiceFragment|exitDiceFragment|:|");
                    }
                },
                45000
        );
        if (serverTurnCounter == HostGame.getMonopolyServer().getNumberOfClients()) {
            serverTurnCounter = 0;
        }
    }


    public void endTurnPressed() {
        timer.cancel();
        turnProcess();
    }

    public void setRanks(int maxPlayers) {
            int revCounter = maxPlayers;
            for (Player player : this.playerList) {
                if ((player.getCapital() <= 0 && player.isBroke() == false && winnerList.size() < maxPlayers - 1)) {
                    player.setBroke(true);
                    this.winnerList.add(player);
                    if (winnerList.size() == maxPlayers - 1) {
                        this.gameover = true;
                    }

                    monopolyServer.broadCast("GameBoardUI|playerBroke|" + player.getUsername());
                }
            }

            if (this.gameover == true&&this.gamerank==true) {
                for (Player player : this.playerList) {
                    if (player.isBroke() == false) {
                        this.tempList.add(player);
                    }
                }
            Collections.sort(this.tempList, new Comparator<Player>() {
                public int compare(Player p1, Player p2) {
                    return Double.compare(p1.getTotalAssetValue(), p2.getTotalAssetValue());
                }
            });
                if (this.tempList != null) {
                    this.winnerList.addAll(this.tempList);
                }

                monopolyServer.broadCast("GameBoardUI|endFrag|:|");

                for (Player winners : this.winnerList) {
                    monopolyServer.broadCast("EndGameFragment|setWinners" + revCounter + "|" + winners.getUsername());
                    revCounter--;
                }
                this.gamerank = false;
            }
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setButtonCheck(boolean buttonCheck) {
        this.buttonCheck = buttonCheck;
    }

    public void doAction() throws Exception {

        //Your building loan matures. Receive $150.
        if (getUser().getCardID() == R.drawable.chance3) {
            transferToPlayerProtocol(150);
            endTurnProtocol();
        }

        //Bank pays you dividend of $50.
        if (getUser().getCardID() == R.drawable.chance5) {
            transferToPlayerProtocol(50);
            endTurnProtocol();
        }

        //Get out of Jail Free.
        if (getUser().getCardID() == R.drawable.chance6 || getUser().getCardID() == R.drawable.chance12 || getUser().getCardID() == R.drawable.community4) {
            outOfJailCounterProtocol(1);
            endTurnProtocol();
        }

        //Parking Ticket! Pay $15.
        if (getUser().getCardID() == R.drawable.chance14 || getUser().getCardID() == R.drawable.community14) {
            transferToBankProtocol(15);
            endTurnProtocol();
        }


        //Happy Birthday! Receive $100.
        if (getUser().getCardID() == R.drawable.chance17) {
            transferToPlayerProtocol(100);
            endTurnProtocol();
        }

        //Bank error in your favor. Collect $200.
        if (getUser().getCardID() == R.drawable.community1) {
            transferToPlayerProtocol(200);
            endTurnProtocol();
        }

        //Doctor’s fee. Pay $50.
        if (getUser().getCardID() == R.drawable.community2) {
            transferToBankProtocol(50);
            endTurnProtocol();
        }

        //From sale of stock you receive $50.
        if (getUser().getCardID() == R.drawable.community3) {
            transferToPlayerProtocol(50);
            endTurnProtocol();
        }

        //community6: Holiday fund matures. Receive $100.
        if (getUser().getCardID() == R.drawable.community6) {
            transferToPlayerProtocol(100);
            endTurnProtocol();
        }

        //Income tax refund. Collect $20.
        if (getUser().getCardID() == R.drawable.community7) {
            transferToPlayerProtocol(20);
            endTurnProtocol();
        }

        //Life insurance matures. Collect $100.
        if (getUser().getCardID() == R.drawable.community9) {
            transferToPlayerProtocol(100);
            endTurnProtocol();
        }

        //Pay hospital fees of $100.
        if (getUser().getCardID() == R.drawable.community10) {
            transferToBankProtocol(100);
            endTurnProtocol();
        }

        //Pay school fees of $50.
        if (getUser().getCardID() == R.drawable.community11) {
            transferToBankProtocol(50);
            endTurnProtocol();
        }

        //Receive $25 consultancy fee.
        if (getUser().getCardID() == R.drawable.community12) {
            transferToPlayerProtocol(25);
            endTurnProtocol();
        }

        //You have won second prize in a beauty contest. Collect $10.
        if (getUser().getCardID() == R.drawable.community15) {
            transferToPlayerProtocol(10);
            endTurnProtocol();
        }

        //You inherit $100.
        if (getUser().getCardID() == R.drawable.community16) {
            transferToPlayerProtocol(100);
            endTurnProtocol();
        }

        //You receive $50 from warehouse sales.
        if (getUser().getCardID() == R.drawable.community17) {
            transferToPlayerProtocol(50);
            endTurnProtocol();
        }

        //You receive a 7% dividend on preferred stock: $25.
        if (getUser().getCardID() == R.drawable.community18) {
            transferToPlayerProtocol(25);
            endTurnProtocol();
        }

    }


    public void transferToPlayerProtocol(int amount) throws IOException {
        writeToServer("GameBoardUI|giveMoney|" + amount + "|" + getUser().getUsername());
    }

    public void transferToBankProtocol(int amount) throws IOException {
        int amountNew= -amount;
        writeToServer("GameBoardUI|transferToBank|" + amountNew + "|" + getUser().getUsername());
    }


    public void outOfJailCounterProtocol(int amount) throws IOException {
        writeToServer("GameBoardUI|outOfJailCounter|" + amount + "|" + getUser().getUsername());
    }

    public void endTurnProtocol() throws IOException {
        writeToServer("GameBoardUI|turnEnd|:|");
    }

}
