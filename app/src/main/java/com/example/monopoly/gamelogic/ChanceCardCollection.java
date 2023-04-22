package com.example.monopoly.gamelogic;

import java.util.ArrayList;

public class ChanceCardCollection {
    private ArrayList<ChanceCard> list = new ArrayList<ChanceCard>();



    public void setList(ArrayList list) {
        this.list = list;
    }

    public ArrayList getList() {
        return list;
    }

    public void addToCollection(ChanceCard card){
        list.add(card);
    }

    public void fillList() {

        ChanceCard card0 = new ChanceCard(0);
        card0.setFunction("Advance to \"Go\".");
        addToCollection(card0);

        ChanceCard card1 = new ChanceCard(1);
        card1.setFunction("Advance to <Location1>. If you pass Go, collect $200.");
        addToCollection(card1);

        ChanceCard card2 = new ChanceCard(2);
        card2.setFunction("Advance to <Location2>. If you pass Go, collect $200.");
        addToCollection(card2);

        ChanceCard card3 = new ChanceCard(3);
        card3.setFunction("Your building loan matures. Receive $150.");
        addToCollection(card3);

        ChanceCard card4 = new ChanceCard(4);
        card4.setFunction("Advance to the nearest Railroad. If unowned, you may buy it from the Bank. " +
                "If owned, pay owner twice the rental to which they are otherwise entitled.");
        addToCollection(card4);

        ChanceCard card5 = new ChanceCard(5);
        card5.setFunction("Bank pays you dividend of $50.");
        addToCollection(card5);

        ChanceCard card6 = new ChanceCard(6);
        card6.setFunction("Get out of Jail Free.");
        addToCollection(card6);

        ChanceCard card7 = new ChanceCard(7);
        card7.setFunction("Go back 3 spaces.");
        addToCollection(card7);

        ChanceCard card8 = new ChanceCard(8);
        card8.setFunction("Make general repairs on all your property: For each house pay $25, for each hotel pay $100.");
        addToCollection(card8);

        ChanceCard card9 = new ChanceCard(9);
        card9.setFunction("Take a trip to <Railway Station>.");
        addToCollection(card9);

        ChanceCard card10 = new ChanceCard(10);
        card10.setFunction("You have been elected Chairman of the Board. Pay each player $50.");
        addToCollection(card10);
    }

}
