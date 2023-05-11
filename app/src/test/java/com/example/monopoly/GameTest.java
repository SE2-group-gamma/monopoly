package com.example.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import android.graphics.Color;

import com.example.monopoly.gamelogic.Game;
import com.example.monopoly.gamelogic.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {

    Game g;
    Player p;

    @BeforeEach
    void setup(){
        g = Game.getInstance();
        p = new Player("Test",new Color(),100.00,true);
    }

    @Test
    public void testAddPlayer(){
        assertEquals(0,g.getPlayers().size());
        assertEquals(true,g.addPlayer(p));
        assertEquals(p,g.getPlayers().get(1));
        assertEquals(1,g.getPlayers().size());
        assertEquals(false,g.addPlayer(p));
        assertEquals(1,g.getPlayers().size());
    }
}
