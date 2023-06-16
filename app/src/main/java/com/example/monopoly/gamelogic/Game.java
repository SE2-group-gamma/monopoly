package com.example.monopoly.gamelogic;

import android.util.Log;

import com.example.monopoly.R;
import com.example.monopoly.network.Client;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton Object
 */
public class Game{
    private static final Game OBJ = new Game();
    private HashMap<Integer,Player> players;        //ID,Player ... ID will be set server-side

    private String currentPlayersTurn;
    Client client;
    private static final AtomicInteger count = new AtomicInteger(0);
    //private volatile int ids = 0;

    private Game() {
        players = new HashMap<>();
    }

    public static Game getInstance() {
        return OBJ;
    }

    /***
     * Adds the player to the Hashmap + Auto-Increment-ID
     * @return
     * true on success, false otherwise
     */
    public boolean addPlayer(Player player){

        if(players.containsValue(player))
            return false;
        int id = count.getAndIncrement();
        players.put(id,player);
        player.setId(id);
        // TODO Are there any better implementations?
        return true;
    }

    public HashMap<Integer, Player> getPlayers() {
        return players;
    }
    public void incrementPlayerPosition(int id,int incr){
        Log.i("Dices","Player:"+this.players.get(id).getUsername()+"; Pos to increment:"+incr+"; Current Pos:"+this.players.get(id).getPosition());
        this.players.get(id).incrementPosition(incr);
    }

    public int getPlayerIDByName(String userName){
        for(int i = 0; i < this.players.size(); i++){
            if(userName.equals(this.players.get(i).getUsername())){
                return i;
            }
        }
        return 0;
    }

    public String getCurrentPlayersTurn() {
        return currentPlayersTurn;
    }

    public void setCurrentPlayersTurn(String currentPlayersTurn) {
        this.currentPlayersTurn = currentPlayersTurn;
    }


    public void doAction(int cardID, Client client) throws IOException {
        this.client = client;
        int playerID = getPlayerIDByName(getCurrentPlayersTurn());
        players.get(playerID).setCardID(cardID);

        //Your building loan matures. Receive $150.
        if (players.get(playerID).getCardID() == R.drawable.chance3) {
            transferToPlayerProtocol(150);
            endTurnProtocol();
        }

        //Bank pays you dividend of $50.
        if (players.get(playerID).getCardID() == R.drawable.chance5) {
            transferToPlayerProtocol(50);
            endTurnProtocol();
        }

        //Get out of Jail Free.
        if (players.get(playerID).getCardID() == R.drawable.chance6 || players.get(playerID).getCardID() == R.drawable.chance12 || players.get(playerID).getCardID() == R.drawable.community4) {
            outOfJailCounterProtocol(1);
            endTurnProtocol();
        }

        //Parking Ticket! Pay $15.
        if (players.get(playerID).getCardID() == R.drawable.chance14 || players.get(playerID).getCardID() == R.drawable.community14) {
            transferToBankProtocol(15);
            endTurnProtocol();
        }


        //Happy Birthday! Receive $100.
        if (players.get(playerID).getCardID() == R.drawable.chance17) {
            transferToPlayerProtocol(100);
            endTurnProtocol();
        }

        //Bank error in your favor. Collect $200.
        if (players.get(playerID).getCardID() == R.drawable.community1) {
            transferToPlayerProtocol(200);
            endTurnProtocol();
        }

        //Doctor’s fee. Pay $50.
        if (players.get(playerID).getCardID() == R.drawable.community2) {
            transferToBankProtocol(50);
            endTurnProtocol();
        }

        //From sale of stock you receive $50.
        if (players.get(playerID).getCardID() == R.drawable.community3) {
            transferToPlayerProtocol(50);
            endTurnProtocol();
        }

        //community6: Holiday fund matures. Receive $100.
        if (players.get(playerID).getCardID() == R.drawable.community6) {
            transferToPlayerProtocol(100);
            endTurnProtocol();
        }

        //Income tax refund. Collect $20.
        if (players.get(playerID).getCardID() == R.drawable.community7) {
            transferToPlayerProtocol(20);
            endTurnProtocol();
        }

        //Life insurance matures. Collect $100.
        if (players.get(playerID).getCardID() == R.drawable.community9) {
            transferToPlayerProtocol(100);
            endTurnProtocol();
        }

        //Pay hospital fees of $100.
        if (players.get(playerID).getCardID() == R.drawable.community10) {
            transferToBankProtocol(100);
            endTurnProtocol();
        }

        //Pay school fees of $50.
        if (players.get(playerID).getCardID() == R.drawable.community11) {
            transferToBankProtocol(50);
            endTurnProtocol();
        }

        //Receive $25 consultancy fee.
        if (players.get(playerID).getCardID() == R.drawable.community12) {
            transferToPlayerProtocol(25);
            endTurnProtocol();
        }

        //You have won second prize in a beauty contest. Collect $10.
        if (players.get(playerID).getCardID() == R.drawable.community15) {
            transferToPlayerProtocol(10);
            endTurnProtocol();
        }

        //You inherit $100.
        if (players.get(playerID).getCardID() == R.drawable.community16) {
            transferToPlayerProtocol(100);
            endTurnProtocol();
        }

        //You receive $50 from warehouse sales.
        if (players.get(playerID).getCardID() == R.drawable.community17) {
            transferToPlayerProtocol(50);
            endTurnProtocol();
        }

        //You receive a 7% dividend on preferred stock: $25.
        if (players.get(playerID).getCardID() == R.drawable.community18) {
            transferToPlayerProtocol(25);
            endTurnProtocol();
        }

        //cardDrawnProtocol(cardID);

    }


    public void transferToPlayerProtocol(int amount) throws IOException {
        //Log.i("Cards", "transferToPlayerProtocol");
        client.writeToServer("GameBoardUI|giveMoney|" + amount + "|" + getCurrentPlayersTurn());
    }

    public void transferToBankProtocol(int amount) throws IOException {
        //Log.i("Cards", "transferToBankProtocol");
        int amountNew= -amount;
        client.writeToServer("GameBoardUI|transferToBank|" + amountNew + "|" + currentPlayersTurn);
    }


   /* public void moveProtocol(int incr) throws IOException {
        //Log.i("Cards", "moveProtocol");
        client.writeToServer("GameBoardUI|move|" + incr + ":f:f|" + currentPlayersTurn);
    }*/

    public void outOfJailCounterProtocol(int amount) throws IOException {
        //Log.i("Cards", "outOfJailCounterProtocol");
        client.writeToServer("GameBoardUI|outOfJailCounter|" + amount + "|" + currentPlayersTurn);
    }

    public void endTurnProtocol() throws IOException {
        //Log.i("Cards", "endTurnProtocol");
        client.writeToServer("GameBoardUI|turnEnd|:|");
    }

    /*public void cardDrawnProtocol(int cardID) throws IOException {
        //Log.i("Cards", "cardDrawn");
        client.writeToServer("GameBoardUI|cardDrawn|"+ cardID + "|"+ currentPlayersTurn);
    }*/

}
