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
    public ArrayList<ChanceCard> allCards;
    public ArrayList<ChanceCard> deck;

    @BeforeEach
    public void createCollection(){
        allCards = new ArrayList<ChanceCard>();
        collection = new ChanceCardCollection();
        collection.fillList();
        allCards = collection.getAllChanceCards();
        deck = collection.getChanceCardDeck();
    }

    @Test
    public void testFill(){
        Assertions.assertEquals(11, allCards.size());
    }

    @Test
    public void printAllChanceCards(){
        for (int i = 0; i< allCards.size(); i++){
            ChanceCard card = (ChanceCard) allCards.get(i);
            System.out.println(card.getFunction());
        }
        Assertions.assertEquals(10,allCards.size());
    }

    @Test
    public void printDeck(){
        ChanceCard newCard = new ChanceCard(deck.size()+1);
        newCard.setFunction("test");
        deck.add(newCard);
        for (int i = 0; i< deck.size(); i++){
            ChanceCard card = (ChanceCard) deck.get(i);
            System.out.println(card.getFunction());
        }
        Assertions.assertEquals(11,deck.size());
    }
}
