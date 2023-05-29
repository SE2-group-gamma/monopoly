package com.example.monopoly;

import com.example.monopoly.gamelogic.CommunityChestCard;
import com.example.monopoly.gamelogic.CommunityChestCardCollection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CommunityChestCardTest {
    CommunityChestCardCollection collection;

    @Test
    public void testCreateCommunityChest(){
        CommunityChestCard card = new CommunityChestCard(0);
        card.setFunction("test");
        card.setId(1);
        Assertions.assertEquals(1,card.getId());
        Assertions.assertEquals("test",card.getFunction());
    }

    @BeforeEach
    public void createCardsAndFill(){
        collection = new CommunityChestCardCollection();
    }
    @Test
    public void testCreateCards(){
        Assertions.assertEquals(20, collection.getAllCommunityChestCards().size());
        Assertions.assertEquals(20, collection.getCommunityChestCardDeck().size());
    }

    @Test
    public void printAllCards() {
        Assertions.assertEquals(20, collection.getAllCommunityChestCards().size());
        for (int i = 0; i < collection.getAllCommunityChestCards().size(); i++) {
            CommunityChestCard card = (CommunityChestCard) collection.getAllCommunityChestCards().get(i);
            System.out.println(card.getId() + ", " + card.getFunction());
        }
    }

    @Test
    public void printDeck() {
        Assertions.assertEquals(20, collection.getCommunityChestCardDeck().size());
        for (int i = 0; i < collection.getCommunityChestCardDeck().size(); i++) {
            CommunityChestCard card = (CommunityChestCard) collection.getCommunityChestCardDeck().get(i);
            System.out.println(card.getId() + ", " + card.getFunction());
        }
    }

    @Test
    public void testGenerateRandom() {
        int random;
        for (int i = 0; i < 20; i++) {
            random = collection.generateRandom();
            System.out.println(random);
            Assertions.assertTrue(random <= collection.getCommunityChestCardDeck().size());
        }
    }

    @Test
    public void testRemove() {
        CommunityChestCard card1 = collection.getCommunityChestCardDeck().get(2);
        collection.removeCardFromDeck(2);
        CommunityChestCard card2 = collection.getCommunityChestCardDeck().get(7);
        collection.removeCardFromDeck(7);
        CommunityChestCard card3 = collection.getCommunityChestCardDeck().get(5);
        collection.removeCardFromDeck(5);
        for (int i = 0; i < collection.getCommunityChestCardDeck().size(); i++) {
            CommunityChestCard card = collection.getCommunityChestCardDeck().get(i);
            System.out.println(card.getId() + ", " + card.getFunction());
        }

        Assertions.assertEquals(collection.getAllCommunityChestCards().size()-3, collection.getCommunityChestCardDeck().size());
        Assertions.assertFalse(collection.getCommunityChestCardDeck().contains(card1));
        Assertions.assertFalse(collection.getCommunityChestCardDeck().contains(card2));
        Assertions.assertFalse(collection.getCommunityChestCardDeck().contains(card3));
    }

    @Test
    public void testDrawFromDeck() {
        for (int i = 0; i < 100; i++) {
            CommunityChestCard card;
            card = collection.drawFromDeck();
            if (card != null) {
                System.out.println(card.getId() + ", " + card.getFunction());
            }

        }
        System.out.println("\nAll: " + collection.getAllCommunityChestCards().size());
        Assertions.assertEquals(20, collection.getAllCommunityChestCards().size());
        System.out.println("Deck: " + collection.getCommunityChestCardDeck().size());
        Assertions.assertEquals(0, collection.getCommunityChestCardDeck().size());
    }

    @Test
    public void allCardsGetterSetterTest(){
        ArrayList<CommunityChestCard> list = new ArrayList<CommunityChestCard>();
        collection.setAllCommunityChestCards(list);
        Assertions.assertEquals(0,collection.getAllCommunityChestCards().size());
    }

    @Test
    public void testGettersSettersImage() {
        CommunityChestCard card = new CommunityChestCard(100);
        card.setImageId(R.drawable.community0);
        Assertions.assertNotEquals(0,card.getImageId());
    }

    @Test
    public void testDrawables() {
        for(int i =0; i<collection.getCommunityChestCardDeck().size(); i++){
            Assertions.assertTrue(collection.getCommunityChestCardDeck().get(i).getImageId() > 2131165300);
        }
    }

}
