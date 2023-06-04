package com.example.monopoly.gamelogic;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton Object
 */
public class Game{
    private static final Game OBJ = new Game();
    private HashMap<Integer,Player> players;        //ID,Player ... ID will be set server-side
    private HashMap<Integer,Field> fields;

    private String currentPlayersTurn;
    private static final AtomicInteger count = new AtomicInteger(0);

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

    public void doAction(int playerID) throws IOException {

        //chance0: Advance to "Go".
        if (players.get(playerID).getCardID() == 2131165320) {
            int incr = fields.size() - players.get(playerID).getPosition();
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|" + incr +"|" + players.get(playerID).getUsername());
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn|" + players.get(playerID).getUsername());
        }

        //chance1: Advance to Strandbad. If you pass Go, collect $200.
        if (players.get(playerID).getCardID() == 2131165321) {
            int fieldId = 0;
            int incr;
            for (int i = 0; i < fields.size(); i++) {
                if (fields.get(i).getName() == "Strandbad") {
                    fieldId = fields.get(i).getId();
                }
            }
            if (players.get(playerID).getPosition() > fieldId) {
                players.get(playerID).setCapital(players.get(playerID).getCapital() + 200);
                incr = fields.size() - players.get(playerID).getPosition() + fieldId;
            }else {
                incr = fieldId - players.get(playerID).getPosition();
            }

            players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|" + incr + "|" + players.get(playerID).getUsername());
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn|" + players.get(playerID).getUsername());
        }

        //chance2: Advance to Lindwurm. If you pass Go, collect $200.
        if (players.get(playerID).getCardID() == 2131165332) {
            int fieldId = 0;
            int incr;
            for (int i = 0; i < fields.size(); i++) {
                if (fields.get(i).getName() == "Lindwurm") {
                    fieldId = fields.get(i).getId();
                }
            }
            if (players.get(playerID).getPosition() > fieldId) {
                players.get(playerID).setCapital(players.get(playerID).getCapital() + 200);
                incr = fields.size() - players.get(playerID).getPosition() + fieldId;
            }else {
                incr = fieldId - players.get(playerID).getPosition();
            }

            players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|" + incr + "|" + players.get(playerID).getUsername());
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn|" + players.get(playerID).getUsername());
        }

        //chance3, Your building loan matures. Receive $150.
        if (players.get(playerID).getCardID() == 2131165333) {
            players.get(playerID).setCapital(players.get(playerID).getCapital() + 150);
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn");
        }

        //chance4: Advance to the nearest Railroad. If unowned, you may buy it from the Bank.
        // If owned, pay owner twice the rental to which they are otherwise entitled.
        if (players.get(playerID).getCardID() == 2131165334) {
            //TODO: everything
        }

        //chance5: Bank pays you dividend of $50.
        if (players.get(playerID).getCardID() == 2131165335) {
            players.get(playerID).setCapital(players.get(playerID).getCapital() + 50);
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn");
        }

        //chance6: Get out of Jail Free.
        if (players.get(playerID).getCardID() == 2131165336) {
            //TODO: add attribute boolean outOfJailFree to player?
        }

        //chance7: Go back 3 spaces.
        if (players.get(playerID).getCardID() == 2131165337) {
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|-3" + players.get(playerID).getUsername());
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn");
        }

        //chance8: Make general repairs on all your property: For each house pay $25, for each hotel pay $100.
        if (players.get(playerID).getCardID() == 2131165338) {
            //TODO: everything
        }

        //chance9: Take a trip to S-Bahn Wien.
        if (players.get(playerID).getCardID() == 2131165339) {
            //TODO: calculate increment to S-Bahn Wien
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|move|REPLACER");
            players.get(playerID).getMyClient().writeToServer("GameBoardUI|endTurn");
        }

        //chance10, You have been elected Chairman of the Board. Pay each player $50.
        if (players.get(playerID).getCardID() == 2131165322) {
            for(int i =0; i< players.size(); i++){
                if (i != playerID){
                    //TODO: implement moneyTransfer in Client
                    players.get(playerID).getMyClient().writeToServer("GameBoardUI|transferMoney|50");
                }
            }
        }
    }
}
