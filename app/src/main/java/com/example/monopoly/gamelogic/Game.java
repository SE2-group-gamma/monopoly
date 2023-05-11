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

public class Game{
    private static final Game OBJ = new Game();
    private HashMap<Integer,Player> players;        //ID,Player ... ID will be set server-side
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
        players.put(count.incrementAndGet(),player);
        Log.d("gameOut",player.getUsername());
        return true;
    }

    public HashMap<Integer, Player> getPlayers() {
        return players;
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
