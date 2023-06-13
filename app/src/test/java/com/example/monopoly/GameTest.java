package com.example.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import android.graphics.Color;

import com.example.monopoly.gamelogic.Board;
import com.example.monopoly.gamelogic.Game;
import com.example.monopoly.gamelogic.Player;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GameTest {

    Game g;
    Player p;
    Player q;
    @BeforeEach
    void setup(){
        g = Game.getInstance();
        p = new Player("Test",new Color(),100.00,true);
        q = new Player("HAns", new Color(),123.00,true);
    }

    @AfterEach
    void cleanUp(){
        g.removeAllPlayers();
    }

    @Test
    void testAddPlayer(){
        assertEquals(0,g.getPlayers().size());
        assertEquals(true,g.addPlayer(p));
        assertEquals(p,g.getPlayers().get(0));
        assertEquals(1,g.getPlayers().size());
        assertEquals(false,g.addPlayer(p));
        assertEquals(1,g.getPlayers().size());
    }

    @Test
    void testIncrementPlayerPosition() throws Exception {
        g.addPlayer(p);
        g.incrementPlayerPosition(0,10);
        assertEquals(10,p.getPosition());
    }

    @Test
    void testIncrementPlayerPositionThrows() {
        g.addPlayer(p);
        assertThrows(Exception.class, ()->g.incrementPlayerPosition(0,13));
    }

    @Test
    void testGetPlayerIDByName() throws Exception {
        g.addPlayer(p);
        assertEquals(0,g.getPlayerIDByName(p.getUsername()));
    }

    @Test
    void testGetPlayerIDByNameThrows() {
        assertThrows(Exception.class,()->g.getPlayerIDByName("404"));
    }

    @Test
    void testGetSetCurrentPlayersTurn() {
        assertEquals(null, g.getCurrentPlayersTurn());
        g.setCurrentPlayersTurn(p.getUsername());
        assertEquals(p.getUsername(),g.getCurrentPlayersTurn());
    }
}
