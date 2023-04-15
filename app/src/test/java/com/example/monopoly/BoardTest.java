package com.example.monopoly;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

class BoardTest {
    static Board board;

    @BeforeAll
    static void setup(){
        ArrayList<Field> arr = new ArrayList<>();
        board = new Board(0,arr);
    }

}