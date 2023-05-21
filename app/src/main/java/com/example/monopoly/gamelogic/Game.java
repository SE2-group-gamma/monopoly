package com.example.monopoly.gamelogic;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton Object
 */
public class Game{
    private static final Game OBJ = new Game();
    private HashMap<Integer,Player> players;        //ID,Player ... ID will be set server-side
    private static final AtomicInteger count = new AtomicInteger(0);
    //private volatile int ids = 0;
    private Board board;
    private int currentPlayerIndex;
    private Dices dices;
    private Bank bank;
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
        /*
        if(players.containsValue(player))
            return false;
        int id = count.getAndIncrement();           // ID wont get incremented... why?
        //Log.i("Dices","Set ID: "+id+"; Player: "+player.getUsername());
        players.put(id,player);
        player.setId(id);

         */
        //this.ids++;
        // TODO Are there any better implementations?
        //Log.d("gameOut",player.getUsername());
        //possible better implemenation?
        //removes check as player is being added immediately afterward
        //you can use the players size(represents number of player added) as ID directly?
        int id = players.size();
        players.put(id, player);
        player.setId(id);
        return true;
    }


    public HashMap<Integer, Player> getPlayers() {
        return players;
    }
    public void incrementPlayerPosition(int id,int incr){
        //Player user = this.players.get
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

    public Player getCurrentPlayer(){
        return players.get(currentPlayerIndex);
    }
    private void setCurrentPlayer(Player player) {
        int currentPlayerIndex = -1;
        for (int index : players.keySet()) {
            if (players.get(index) == player) {
                currentPlayerIndex = index;
                break;
            }
        }

        // Set the currentPlayerIndex to the found index
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public void performPlayerTurn() {
        try{
            // Roll the dice
            dices.rollDices();
            int diceRoll = dices.getSum();

            Player currentPlayer = getCurrentPlayer();
            int currentPosition = currentPlayer.getPosition();
            int newPosition = (currentPosition + diceRoll) % Board.FELDER_ANZAHL;
            incrementPlayerPosition(currentPlayer.getId(),newPosition);

            int currentField= newPosition;
            Board.movePlayer(currentPlayer,diceRoll);
            // add something like drawing cards, buying,paying rent etc after sunday

            //updateGameState();
            proceedToNextPlayer();
        }catch (NullPointerException e) {
            System.out.println("Initialize objects");
        }

    }

    /*
    public void updateGameState(){
        //add win/los conditions
    }

     */

    public void proceedToNextPlayer(){
        Player currentPlayer = getCurrentPlayer();

        int currentPlayerIndex = -1;
        for (int index : players.keySet()) {
            if (players.get(index) == currentPlayer) {
                currentPlayerIndex = index;
                break;
            }
        }

        // Calculate the index of the next player
        int nextPlayerIndex = (currentPlayerIndex + 1) % players.size();

        // Set the next player as the current player for the next turn
        Player nextPlayer = players.get(nextPlayerIndex);
        setCurrentPlayer(nextPlayer);

        // Perform any additional actions or updates related to transitioning to the next player's turn
        // For example, resetting player-specific variables or updating UI elements

        // Start the next player's turn
        performPlayerTurn();

    }

    /*@Override
    public void run() {
        Log.d("gameOut","Game-Start");
        int count = 0;

        // mit setter methode fürm msgBuffer nachrichten setzen und in der while mit synchronized abarbeiten + client check und dann erst game logik

        while (true) {
            synchronized (msgBuffer) {
                if (msgBuffer.size() != 0) {
                    for (int i = msgBuffer.size() - 1; i >= 0; i--) {
                        //Log.d("msgBuffer", msgBuffer.get(i));
                        Log.d("gameOut", "" + msgBuffer.get(i));
                    }
                }
            }
            if(players.containsKey(1) && count == 0) {
                Log.d("gameOut", players.get(1).getUsername());
                count++;
            }
            if(players.containsKey(2) && count == 1) {
                Log.d("gameOut", "asdasdad"/*+players.get(2).getUsername());
                count++;
            }
        }
    }*/
}
