package com.example.monopoly;

import com.example.monopoly.gamelogic.ChanceCard;
import com.example.monopoly.gamelogic.ChanceCardCollection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ChanceCardTest {

    public ChanceCardCollection collection;

    @BeforeEach
    public void createCollection() {
        collection = new ChanceCardCollection();
    }

    @Test
    public void testConstructor() {
        Assertions.assertTrue(collection.getAllChanceCards().size()>0);
    }

    @Test
    public void printAllChanceCards() {
        for (int i = 0; i < collection.getAllChanceCards().size(); i++) {
            ChanceCard card = (ChanceCard) collection.getAllChanceCards().get(i);
            System.out.println(card.getId() + ", " + card.getFunction());
        }
    }

    @Test
    public void printDeck() {
        ChanceCard newCard = new ChanceCard(collection.getChanceCardDeck().size());
        newCard.setFunction("test");
        collection.getChanceCardDeck().add(newCard);
        for (int i = 0; i < collection.getChanceCardDeck().size(); i++) {
            ChanceCard card = (ChanceCard) collection.getChanceCardDeck().get(i);
            System.out.println(card.getId() + ", " + card.getFunction());
        }
    }

    @Test
    public void testGenerateRandom() {
        int random;
        for (int i = 0; i < 20; i++) {
            random = collection.generateRandom();
            System.out.println(random);
            Assertions.assertTrue(random <= collection.getChanceCardDeck().size());
        }

    }

    @Test
    public void testRemove() {
        collection.removeCardFromDeck(2);
        collection.removeCardFromDeck(7);
        collection.removeCardFromDeck(5);
        for (int i = 0; i < collection.getChanceCardDeck().size(); i++) {
            ChanceCard card = collection.getChanceCardDeck().get(i);
            System.out.println(card.getId() + ", " + card.getFunction());
        }

        Assertions.assertEquals(collection.getAllChanceCards().size()-3, collection.getChanceCardDeck().size());

    }

    @Test
    public void testDrawFromDeck() {
        for (int i = 0; i < 100; i++) {
            ChanceCard card;
            card = collection.drawFromDeck();
            if (card != null) {
                System.out.println(card.getId() + ", " + card.getFunction());
            }

        }
        System.out.println("\nAll: " + collection.getAllChanceCards().size());
        System.out.println("Deck: " + collection.getChanceCardDeck().size());
    }

    @Test
    public void testGettersSettersChanceCard(){
        ChanceCard card = collection.getAllChanceCards().get(0);
        card.setId(17);
        card.setFunction("test");
        Assertions.assertEquals(17,card.getId());
        Assertions.assertEquals("test", card.getFunction());
    }

    @Test
    public void testGettersSettersChanceCardCollection(){
        ArrayList<ChanceCard> list = new ArrayList<ChanceCard>();
        collection.setAllChanceCards(list);
        collection.setChanceCardDeck(list);
        Assertions.assertEquals(0,collection.getAllChanceCards().size());
        Assertions.assertEquals(0,collection.getChanceCardDeck().size());
    }
}
