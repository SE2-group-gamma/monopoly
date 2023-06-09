package com.example.monopoly;

import android.graphics.Color;

import com.example.monopoly.gamelogic.Board;
import com.example.monopoly.gamelogic.Field;
import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.gamelogic.properties.PropertyStorage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class BoardTest {

    static PropertyStorage ps;
    @BeforeAll
    static void initPropertyStorage(){
        ps = PropertyStorage.getInstance();
    }

    void testSpecialFields(){
        assertEquals("go", Board.getFieldName(0));
        assertEquals("jail", Board.getFieldName(10));
        assertEquals("free_parking", Board.getFieldName(20));
        assertEquals("goto_jail", Board.getFieldName(30));

        assertEquals("community", Board.getFieldName(2));
        assertEquals("income_tax", Board.getFieldName(4));
        assertEquals("chance", Board.getFieldName(7));
        assertEquals("community", Board.getFieldName(17));
        assertEquals("chance", Board.getFieldName(22));
        assertEquals("community", Board.getFieldName(33));
        assertEquals("chance", Board.getFieldName(36));
        assertEquals("luxury_tax", Board.getFieldName(38));
    }

    @Test
    void testPropertyFields(){
        List<String> specialFields = List.of("go", "jail","free_parking","goto_jail","community","income_tax","chance","luxury_tax");

        for(int i = 0; i < 40; i++){
            String name = Board.getFieldName(i);
            if(!specialFields.contains(name)){
                assertTrue(ps.hasField(name));
            }
        }
    }

    @Test
    public void testBoardConstructor() {
        int id = 1;
        ArrayList<Field> fields = new ArrayList<>();
        Board board = new Board(id, fields);

        assertEquals(id, board.getId());
        assertEquals(fields, board.getFields());
    }

}