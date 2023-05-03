package com.example.monopoly.gamelogic;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {
    private HashMap<Integer,Player> players;        //ID,Player ... ID will be set server-side
    private static final AtomicInteger count = new AtomicInteger(0);

    public Game() {
        players = new HashMap<>();
    }

    /***
     * Adds the player to the Hashmap + Auto-Increment-ID
     * @return
     * true on success, false otherwise
     */
    public boolean addPlayer(Player player){
        if(players.containsValue(player))
            return false;
        players.put(count.incrementAndGet(),player);
        return true;
    }

}
