package com.example.monopoly;

import org.junit.jupiter.api.Test;

import android.graphics.Color;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.example.monopoly.gamelogic.Player;

class PlayerTest {
    static Player player;
    static Color col;

    @BeforeEach
    void setup(){
        col = mock(Color.class);
        player = new Player("Hans",col,100.00,true);
    }

    @Test
    void setGetCapital() {
        assertEquals(player.getCapital(),100.00);
        player.setCapital(120.00);
        assertEquals(player.getCapital(),120.00);
    }

    @Test
    void setGetAlive() {
        assertEquals(player.isAlive(),true);
        player.setAlive(false);
        assertEquals(player.isAlive(),false);
    }

    @Test
    void setGetInPrison() {
        assertEquals(player.isInPrison(),false);
        player.setInPrison(true);
        assertEquals(player.isInPrison(),true);
    }

    @Test
    void getUsername() {
        assertEquals(player.getUsername(),"Hans");
    }

    @Test
    void getCol() {
        assertEquals(player.getCol(),col);
    }

}