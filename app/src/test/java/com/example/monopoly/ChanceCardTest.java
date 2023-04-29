package com.example.monopoly;

import com.example.monopoly.gamelogic.ChanceCard;
import com.example.monopoly.gamelogic.ChanceCardCollection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class ChanceCardTest {

    public ChanceCardCollection collection;

    @BeforeEach
    public void createCollection() {
        collection = new ChanceCardCollection();
        collection.fillList();
    }

    @Test
    public void testFill() {
        Assertions.assertEquals(11, collection.getAllChanceCards().size());
    }

    @Test
    public void printAllChanceCards() {
        for (int i = 0; i < collection.getAllChanceCards().size(); i++) {
            ChanceCard card = (ChanceCard) collection.getAllChanceCards().get(i);
            System.out.println(card.getFunction());
        }
        Assertions.assertEquals(11, collection.getAllChanceCards().size());
    }

    @Test
    public void printDeck() {
        ChanceCard newCard = new ChanceCard(collection.getChanceCardDeck().size() + 1);
        newCard.setFunction("test");
        collection.getChanceCardDeck().add(newCard);
        for (int i = 0; i < collection.getChanceCardDeck().size(); i++) {
            ChanceCard card = (ChanceCard) collection.getChanceCardDeck().get(i);
            System.out.println(card.getFunction());
        }
        Assertions.assertEquals(12, collection.getChanceCardDeck().size());
    }

    @Test
    public void testGenerateRandom() {
        int random;
        for (int i = 0; i < 10; i++) {
            random = collection.generateRandom();
            System.out.println(random);
            Assertions.assertTrue(random <= collection.getChanceCardDeck().size());
        }

    }
}
