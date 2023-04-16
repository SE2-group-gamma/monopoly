package com.example.monopoly;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.monopoly.utils.LobbyKey;

import org.junit.jupiter.api.Test;

public class LobbyKeyTest {

    @Test
    public void testGenerateKeyRange() {
        LobbyKey lobbyKey = new LobbyKey();
        int key = lobbyKey.generateKey();
        assertTrue(key >= 1000 && key <= 9999);
    }

    @Test
    public void testGenerateKeyRandomness() {
        LobbyKey lobbyKey = new LobbyKey();
        int key1 = lobbyKey.generateKey();
        int key2 = lobbyKey.generateKey();
        int key3 = lobbyKey.generateKey();
        assertNotEquals(key1, key2);
        assertNotEquals(key1, key3);
        assertNotEquals(key2, key3);


    }

}