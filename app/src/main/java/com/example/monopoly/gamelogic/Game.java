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

public class Game extends Thread{
    private HashMap<Integer,Player> players;        //ID,Player ... ID will be set server-side
    private static final AtomicInteger count = new AtomicInteger(0);
    private ArrayList<String> msgBuffer;

    public Game() {
        players = new HashMap<>();
        msgBuffer = new ArrayList<>();
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

    public HashMap<Integer, Player> getPlayers() {
        return players;
    }

    @Override
    public void run() {
        Log.d("gameOut","Game-Start");
        while (true) {
            /*synchronized (msgBuffer) {
                if (msgBuffer.size() != 0) {
                    for (int i = msgBuffer.size() - 1; i >= 0; i--) {
                        //Log.d("msgBuffer", msgBuffer.get(i));
                        Log.d("gameOut", "" + msgBuffer.get(i));
                    }
                }
            }*/
            //if(players.containsKey(1))
            //    Log.d("gameOut",players.get(1).getUsername());
        }
    }
}
