package com.example.monopoly;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.monopoly.gamelogic.ChanceCard;
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
        collection.removeCardFromDeck(2);
        collection.removeCardFromDeck(7);
        collection.removeCardFromDeck(5);
        for (int i = 0; i < collection.getCommunityChestCardDeck().size(); i++) {
            CommunityChestCard card = collection.getCommunityChestCardDeck().get(i);
            System.out.println(card.getId() + ", " + card.getFunction());
        }

        Assertions.assertEquals(collection.getAllCommunityChestCards().size()-3, collection.getCommunityChestCardDeck().size());

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
        System.out.println("Deck: " + collection.getCommunityChestCardDeck().size());
    }

    @Test
    public void allCardsGetterSetterTest(){
        ArrayList<CommunityChestCard> list = new ArrayList<CommunityChestCard>();
        collection.setAllCommunityChestCards(list);
        Assertions.assertEquals(0,collection.getAllCommunityChestCards().size());
    }

    @Test
    public void testGettersSettersImage() {
        Drawable image = new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {

            }

            @Override
            public void setAlpha(int i) {
                i = 1;
            }

            @Override
            public void setColorFilter(@Nullable ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        };

        CommunityChestCard card = new CommunityChestCard(1);
        card.setImage(image);
        Assertions.assertNotEquals(card.getImage(), null);
    }
}
