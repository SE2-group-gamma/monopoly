package com.example.monopoly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.monopoly.gamelogic.PlayerMapPosition;


public class PlayerMapPositionTest {

    PlayerMapPosition playerMapPosition;
    @BeforeEach
    void setUp(){
        playerMapPosition = new PlayerMapPosition(1000, 1000, 1);
    }

    @Test
    public void testPositionXGetterSetter() {
        int expectedX = 2000;
        playerMapPosition.setPositionX(expectedX);
        int actualX = playerMapPosition.getPositionX();

        assertEquals(expectedX, actualX);
    }

    @Test
    public void testPositionYGetterSetter() {
        int expectedY = 2000;
        playerMapPosition.setPositionY(expectedY);
        int actualY = playerMapPosition.getPositionY();

        assertEquals(expectedY, actualY);
    }

    @Test
    public void testRoundGetterSetter() {
        int expectedRound = 3;
        playerMapPosition.setRound(expectedRound);
        int actualRound = playerMapPosition.getRound();

        assertEquals(expectedRound, actualRound);
    }

}
