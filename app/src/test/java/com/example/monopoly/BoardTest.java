package com.example.monopoly;

import android.graphics.Color;

import com.example.monopoly.gamelogic.Board;
import com.example.monopoly.gamelogic.Field;
import com.example.monopoly.gamelogic.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {
    static Board board;
    static Player player,player_two;
    static Color col;
    @BeforeAll
    static void setup(){
        ArrayList<Field> arr = new ArrayList<>();
        board = new Board(0,arr);
    }

    @Test
    public void testMovePlayer() {
        // Move the player with positive steps
        player = new Player("Hans",col,100.00,true);
        player_two = new Player("Fritz",col,100.00,true);
        int steps = 3;
        int expectedPosition = (player.getPosition() + steps) % Board.FELDER_ANZAHL;
        Board.movePlayer(player, steps);
        assertEquals(expectedPosition, player.getPosition());

        // Move the player with negative steps
        steps = -2;
        expectedPosition = (player.getPosition() + steps) % Board.FELDER_ANZAHL;
        Board.movePlayer(player, steps);
        assertEquals(expectedPosition, player.getPosition());

        // Test money transfer if new position is less than the previous position
        double initialPlayerMoney = player.getCapital();
        Board.movePlayer(player, -4);
        assertTrue(player.getCapital() > initialPlayerMoney);
    }



}