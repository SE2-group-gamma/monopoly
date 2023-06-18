package com.example.monopoly.gamelogic;

import com.example.monopoly.network.Client;

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
        return true;
    }


    public HashMap<Integer, Player> getPlayers() {
        return players;
    }
    public void incrementPlayerPosition(int id,int incr) throws Exception {
        if(incr<=12 && incr >= 2)
            this.players.get(id).incrementPosition(incr);
        else
            throw new Exception("Dice Value invalid!");
    }

    public int getPlayerIDByName(String userName) throws Exception {
        for(int i = 0; i < this.players.size(); i++){
            if(userName.equals(this.players.get(i).getUsername())){
                return i;
            }
        }
        throw new Exception("404 not found!");
    }

    public String getCurrentPlayersTurn() {
        return currentPlayersTurn;
    }

    public void setCurrentPlayersTurn(String currentPlayersTurn) {
        this.currentPlayersTurn = currentPlayersTurn;
    }

    public void removeAllPlayers(){
        this.players.clear();
        this.count.set(0);
    }
}
