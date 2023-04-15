package com.example.monopoly;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import android.graphics.Color;
import android.media.Image;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Mock;

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