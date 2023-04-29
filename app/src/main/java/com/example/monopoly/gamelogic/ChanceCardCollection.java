package com.example.monopoly.gamelogic;

import java.util.ArrayList;
import java.util.Random;

public class ChanceCardCollection {
    private ArrayList<ChanceCard> allChanceCards = new ArrayList<ChanceCard>();

    private ArrayList<ChanceCard> chanceCardDeck = new ArrayList<ChanceCard>();

    public ChanceCardCollection(){
        createCards();
    }

    public void setChanceCardDeck(ArrayList chanceCardDeck) {
        this.chanceCardDeck = chanceCardDeck;
    }

    public ArrayList<ChanceCard> getChanceCardDeck() {
        return chanceCardDeck;
    }

    public void setAllChanceCards(ArrayList allChanceCards) {
        this.allChanceCards = allChanceCards;
    }

    public ArrayList<ChanceCard> getAllChanceCards() {
        return allChanceCards;
    }

    public void addToAllChanceCards(ChanceCard card) {
        allChanceCards.add(card);
    }

    public void createCards() {

        ChanceCard card0 = new ChanceCard(0);
        card0.setFunction("Advance to \"Go\".");
        addToAllChanceCards(card0);

        ChanceCard card1 = new ChanceCard(1);
        card1.setFunction("Advance to <Location1>. If you pass Go, collect $200.");
        addToAllChanceCards(card1);

        ChanceCard card2 = new ChanceCard(2);
        card2.setFunction("Advance to <Location2>. If you pass Go, collect $200.");
        addToAllChanceCards(card2);

        ChanceCard card3 = new ChanceCard(3);
        card3.setFunction("Your building loan matures. Receive $150.");
        addToAllChanceCards(card3);

        ChanceCard card4 = new ChanceCard(4);
        card4.setFunction("Advance to the nearest Railroad. If unowned, you may buy it from the Bank. " +
                "If owned, pay owner twice the rental to which they are otherwise entitled.");
        addToAllChanceCards(card4);

        ChanceCard card5 = new ChanceCard(5);
        card5.setFunction("Bank pays you dividend of $50.");
        addToAllChanceCards(card5);

        ChanceCard card6 = new ChanceCard(6);
        card6.setFunction("Get out of Jail Free.");
        addToAllChanceCards(card6);

        ChanceCard card7 = new ChanceCard(7);
        card7.setFunction("Go back 3 spaces.");
        addToAllChanceCards(card7);

        ChanceCard card8 = new ChanceCard(8);
        card8.setFunction("Make general repairs on all your property: For each house pay $25, for each hotel pay $100.");
        addToAllChanceCards(card8);

        ChanceCard card9 = new ChanceCard(9);
        card9.setFunction("Take a trip to <Railway Station>.");
        addToAllChanceCards(card9);

        ChanceCard card10 = new ChanceCard(10);
        card10.setFunction("You have been elected Chairman of the Board. Pay each player $50.");
        addToAllChanceCards(card10);

        addCardsToDeck();
    }

    public void addCardsToDeck() {
        setChanceCardDeck((ArrayList<ChanceCard>) allChanceCards.clone());
    }

    public void removeCardFromDeck(int index) {
        chanceCardDeck.remove(index);
    }

    public ChanceCard drawFromDeck() {
        int random = generateRandom();
        if (random >= 0) {
            ChanceCard card = chanceCardDeck.get(random);
            removeCardFromDeck(random);
            return card;
        } else return null;
    }

    public int generateRandom() {
        Random r = new Random();
        if (chanceCardDeck.size() == 0){
            return -1;
        }
        int random = r.nextInt(chanceCardDeck.size());
        return random;
    }


}
