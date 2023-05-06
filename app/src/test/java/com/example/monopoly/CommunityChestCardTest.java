package com.example.monopoly;

import com.example.monopoly.gamelogic.CommunityChestCard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommunityChestCardTest {

    @Test
    public void createCommunityChestTest(){
        CommunityChestCard card = new CommunityChestCard(0);
        card.setFunction("test");
        Assertions.assertEquals(0,card.getId());
        Assertions.assertEquals("test",card.getFunction());
    }

}
