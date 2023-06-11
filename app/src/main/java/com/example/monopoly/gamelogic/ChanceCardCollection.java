package com.example.monopoly.gamelogic;

import com.example.monopoly.R;

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
        card1.setFunction("Advance to Strandbad. If you pass Go, collect $200.");
        addToAllChanceCards(card1);

        ChanceCard card2 = new ChanceCard(2);
        card2.setFunction("Advance to Lindwurm. If you pass Go, collect $200.");
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
        card9.setFunction("Take a trip to S-Bahn Wien.");
        addToAllChanceCards(card9);

        ChanceCard card10 = new ChanceCard(10);
        card10.setFunction("You have been elected Chairman of the Board. Pay each player $50.");
        addToAllChanceCards(card10);

        ChanceCard card11 = new ChanceCard(11);
        card11.setFunction("Advance to City Arkaden. If you pass Go, collect $200.");
        addToAllChanceCards(card11);

        ChanceCard card12 = new ChanceCard(12);
        card12.setFunction("Get out of Jail Free.");
        addToAllChanceCards(card12);

        ChanceCard card13 = new ChanceCard(13);
        card13.setFunction("Go to Jail directly.");
        addToAllChanceCards(card13);

        ChanceCard card14 = new ChanceCard(14);
        card14.setFunction("Parking Ticket! Pay $15.");
        addToAllChanceCards(card14);

        ChanceCard card15 = new ChanceCard(15);
        card15.setFunction("Advance two spaces.");
        addToAllChanceCards(card15);

        ChanceCard card16 = new ChanceCard(16);
        card16.setFunction("Go to Jail directly.");
        addToAllChanceCards(card16);

        ChanceCard card17 = new ChanceCard(17);
        card17.setFunction("Happy Birthday! Receive $100.");
        addToAllChanceCards(card17);

        ChanceCard card18 = new ChanceCard(18);
        card18.setFunction("Go back one space.");
        addToAllChanceCards(card18);

        ChanceCard card19 = new ChanceCard(19);
        card19.setFunction("Advance to Rathaus. If you pass Go, collect $200. ");
        addToAllChanceCards(card19);

        addCardsToDeck();
        setDrawables();
    }

    private void setDrawables() {
       getChanceCardDeck().get(0).setImageId(R.drawable.chance0);
       getAllChanceCards().get(0).setImageId(R.drawable.chance0);
       getChanceCardDeck().get(1).setImageId(R.drawable.chance1);
       getAllChanceCards().get(1).setImageId(R.drawable.chance1);
       getChanceCardDeck().get(2).setImageId(R.drawable.chance2);
       getAllChanceCards().get(2).setImageId(R.drawable.chance2);
       getChanceCardDeck().get(3).setImageId(R.drawable.chance3);
       getAllChanceCards().get(3).setImageId(R.drawable.chance3);
       getChanceCardDeck().get(4).setImageId(R.drawable.chance4);
       getAllChanceCards().get(4).setImageId(R.drawable.chance4);
       getChanceCardDeck().get(5).setImageId(R.drawable.chance5);
       getAllChanceCards().get(5).setImageId(R.drawable.chance5);
       getChanceCardDeck().get(6).setImageId(R.drawable.chance6);
       getAllChanceCards().get(6).setImageId(R.drawable.chance6);
       getChanceCardDeck().get(7).setImageId(R.drawable.chance7);
       getAllChanceCards().get(7).setImageId(R.drawable.chance7);
       getChanceCardDeck().get(8).setImageId(R.drawable.chance8);
       getAllChanceCards().get(8).setImageId(R.drawable.chance8);
       getChanceCardDeck().get(9).setImageId(R.drawable.chance9);
       getAllChanceCards().get(9).setImageId(R.drawable.chance9);
       getChanceCardDeck().get(10).setImageId(R.drawable.chance10);
       getAllChanceCards().get(10).setImageId(R.drawable.chance10);
       getChanceCardDeck().get(11).setImageId(R.drawable.chance11);
       getAllChanceCards().get(11).setImageId(R.drawable.chance11);
       getChanceCardDeck().get(12).setImageId(R.drawable.chance12);
       getAllChanceCards().get(12).setImageId(R.drawable.chance12);
       getChanceCardDeck().get(13).setImageId(R.drawable.chance13);
       getAllChanceCards().get(13).setImageId(R.drawable.chance13);
       getChanceCardDeck().get(14).setImageId(R.drawable.chance14);
       getAllChanceCards().get(14).setImageId(R.drawable.chance14);
       getChanceCardDeck().get(15).setImageId(R.drawable.chance15);
       getAllChanceCards().get(15).setImageId(R.drawable.chance15);
       getChanceCardDeck().get(16).setImageId(R.drawable.chance16);
       getAllChanceCards().get(16).setImageId(R.drawable.chance16);
       getChanceCardDeck().get(17).setImageId(R.drawable.chance17);
       getAllChanceCards().get(17).setImageId(R.drawable.chance17);
       getChanceCardDeck().get(18).setImageId(R.drawable.chance18);
       getAllChanceCards().get(18).setImageId(R.drawable.chance18);
       getChanceCardDeck().get(19).setImageId(R.drawable.chance19);
       getAllChanceCards().get(19).setImageId(R.drawable.chance19);
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
        random --;
        if (random <= 0){
            return 0;
        }
        return random;
    }


}
