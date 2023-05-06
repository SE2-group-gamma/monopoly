package com.example.monopoly;

import com.example.monopoly.gamelogic.ChanceCard;
import com.example.monopoly.gamelogic.CommunityChestCard;
import com.example.monopoly.gamelogic.CommunityChestCardCollection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommunityChestCardTest {
    CommunityChestCardCollection collection;

    @Test
    public void createCommunityChestTest(){
        CommunityChestCard card = new CommunityChestCard(0);
        card.setFunction("test");
        Assertions.assertEquals(0,card.getId());
        Assertions.assertEquals("test",card.getFunction());
    }

    @BeforeEach
    public void createCardsAndFill(){
        collection = new CommunityChestCardCollection();
    }
    @Test
    public void createCardsTest(){
        Assertions.assertEquals(20, collection.getAllCommunityChestCards().size());
        Assertions.assertEquals(20, collection.getCommunityChestCardDeck().size());
    }

    @Test
    public void printAllCards() {
        for (int i = 0; i < collection.getAllCommunityChestCards().size(); i++) {
            CommunityChestCard card = (CommunityChestCard) collection.getAllCommunityChestCards().get(i);
            System.out.println(card.getId() + ", " + card.getFunction());
        }
    }

    @Test
    public void printDeck() {
        for (int i = 0; i < collection.getCommunityChestCardDeck().size(); i++) {
            CommunityChestCard card = (CommunityChestCard) collection.getCommunityChestCardDeck().get(i);
            System.out.println(card.getId() + ", " + card.getFunction());
        }
    }

}
