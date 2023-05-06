package com.example.monopoly.gamelogic;

import java.util.ArrayList;
import java.util.Random;

public class CommunityChestCardCollection {
    private ArrayList<CommunityChestCard> allCommunityChestCards = new ArrayList<CommunityChestCard>();

    private ArrayList<CommunityChestCard> communityChestCardDeck = new ArrayList<CommunityChestCard>();

    public CommunityChestCardCollection() {
    createCards();
    }

    public void setCommunityChestCardDeck(ArrayList communityChestCardDeck) {
        this.communityChestCardDeck = communityChestCardDeck;
    }

    public ArrayList<CommunityChestCard> getCommunityChestCardDeck() {
        return communityChestCardDeck;
    }

    public ArrayList<CommunityChestCard> getAllCommunityChestCards() {
        return allCommunityChestCards;
    }


    public void setAllCommunityChestCards(ArrayList allCommunityChestCards) {
        this.allCommunityChestCards = allCommunityChestCards;
    }

    public void createCards() {

        CommunityChestCard card0 = new CommunityChestCard(0);
        card0.setFunction("Advance to \"Go\".");
        addToAllCommunityChestCards(card0);

        CommunityChestCard card1 = new CommunityChestCard(1);
        card1.setFunction("Bank error in your favor. Collect $200.");
        addToAllCommunityChestCards(card1);

        CommunityChestCard card2 = new CommunityChestCard(2);
        card2.setFunction("Doctor’s fee. Pay $50.");
        addToAllCommunityChestCards(card2);

        CommunityChestCard card3 = new CommunityChestCard(3);
        card3.setFunction("From sale of stock you receive $50.");
        addToAllCommunityChestCards(card3);

        CommunityChestCard card4 = new CommunityChestCard(4);
        card4.setFunction("Get Out of Jail Free.");
        addToAllCommunityChestCards(card4);

        CommunityChestCard card5 = new CommunityChestCard(5);
        card5.setFunction("Go to Jail directly.");
        addToAllCommunityChestCards(card5);

        CommunityChestCard card6 = new CommunityChestCard(6);
        card6.setFunction("Holiday fund matures. Receive $100.");
        addToAllCommunityChestCards(card6);

        CommunityChestCard card7 = new CommunityChestCard(7);
        card7.setFunction("Income tax refund. Collect $20.");
        addToAllCommunityChestCards(card7);

        CommunityChestCard card8 = new CommunityChestCard(8);
        card8.setFunction("It is your birthday. Collect $10 from every player.");
        addToAllCommunityChestCards(card8);

        CommunityChestCard card9 = new CommunityChestCard(9);
        card9.setFunction("Life insurance matures. Collect $100.");
        addToAllCommunityChestCards(card9);

        CommunityChestCard card10 = new CommunityChestCard(10);
        card10.setFunction("Pay hospital fees of $100.");
        addToAllCommunityChestCards(card10);

        CommunityChestCard card11 = new CommunityChestCard(11);
        card11.setFunction("Pay school fees of $50.");
        addToAllCommunityChestCards(card11);

        CommunityChestCard card12 = new CommunityChestCard(12);
        card12.setFunction("Receive $25 consultancy fee.");
        addToAllCommunityChestCards(card12);

        CommunityChestCard card13 = new CommunityChestCard(13);
        card13.setFunction("You are assessed for street repair. $40 per house. $115 per hotel.");
        addToAllCommunityChestCards(card13);

        CommunityChestCard card14 = new CommunityChestCard(14);
        card14.setFunction("Parking Ticket! Pay $15.");
        addToAllCommunityChestCards(card14);

        CommunityChestCard card15 = new CommunityChestCard(15);
        card15.setFunction("You have won second prize in a beauty contest. Collect $10.");
        addToAllCommunityChestCards(card15);

        CommunityChestCard card16 = new CommunityChestCard(16);
        card16.setFunction("You inherit $100.");
        addToAllCommunityChestCards(card16);

        CommunityChestCard card17 = new CommunityChestCard(17);
        card17.setFunction("You receive $50 from warehouse sales.");
        addToAllCommunityChestCards(card17);

        CommunityChestCard card18 = new CommunityChestCard(18);
        card18.setFunction("You receive a 7% dividend on Preferred Stock: $25.");
        addToAllCommunityChestCards(card18);

        CommunityChestCard card19 = new CommunityChestCard(19);
        card19.setFunction("Receive $100 Compensation for Pain and Suffering from the next Player.");
        addToAllCommunityChestCards(card19);

        addCardsToDeck();
    }

    public void addToAllCommunityChestCards(CommunityChestCard card) {
        allCommunityChestCards.add(card);
    }

    public void addCardsToDeck(){
        setCommunityChestCardDeck((ArrayList<CommunityChestCard>) allCommunityChestCards.clone());
    }

    public int generateRandom() {
        Random r = new Random();
        if (communityChestCardDeck.size() == 0){
            return -1;
        }
        int random = r.nextInt(communityChestCardDeck.size());
        return random;
    }

}
