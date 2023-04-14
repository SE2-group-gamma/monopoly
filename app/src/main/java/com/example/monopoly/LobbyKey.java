package com.example.monopoly;

import java.util.Random;

public class LobbyKey {

    public LobbyKey() {
    }

    public int generateKey(){
        return new Random().nextInt(9000) + 1000;
    }
}
