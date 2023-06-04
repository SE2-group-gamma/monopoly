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


}