package com.example.monopoly;

import android.graphics.Color;
import android.media.Image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.example.monopoly.gamelogic.Field;
import com.example.monopoly.gamelogic.Player;

class FieldTest {
    static Field field;
    static Player owner;
    static Image img;
    static Color col;

    @BeforeEach
    void setup(){
        img = mock(Image.class);
        owner = mock(Player.class);
        col = mock(Color.class);
        field = new Field(0,"Billa","Ereignis",col,100,3,owner,50000,33000,30000,img);
    }
    @Test
    void setGetCost() {
        assertEquals(field.getCost(),100);
        field.setCost(10);
        assertEquals(field.getCost(),10);
    }

    @Test
    void setGetHouses() {
        assertEquals(field.getHouses(),3);
        field.setHouses(10);
        assertEquals(field.getHouses(),10);
    }

    @Test
    void setGetOwner() {
        assertEquals(field.getOwner(), owner);
        Player newOwner = mock(Player.class);
        field.setOwner(newOwner);
        assertEquals(field.getOwner(), newOwner);
    }

    @Test
    void getHouseCost() {
        assertEquals(field.getHouseCost(), 50000);
    }

    @Test
    void getMortgageValue() {
        assertEquals(field.getMortgageValue(),33000);
    }

    @Test
    void getMortgageCancellationValue() {
        assertEquals(field.getMortgageCancellationValue(),30000);
    }

    @Test
    void getId() {
        assertEquals(field.getId(),0);
    }

    @Test
    void getName() {
        assertEquals(field.getName(),"Billa");
    }

    @Test
    void getType() {
        assertEquals(field.getType(), "Ereignis");
    }

    @Test
    void getCol() {
        assertEquals(field.getCol(),col);
    }

}