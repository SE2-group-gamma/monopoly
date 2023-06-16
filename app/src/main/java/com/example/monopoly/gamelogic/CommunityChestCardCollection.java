package com.example.monopoly.gamelogic;

import com.example.monopoly.R;

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
        card18.setFunction("You receive a 7% dividend on preferred stock: $25.");
        addToAllCommunityChestCards(card18);

        CommunityChestCard card19 = new CommunityChestCard(19);
        card19.setFunction("Receive $100 compensation for pain and suffering from the next player.");
        addToAllCommunityChestCards(card19);

        addCardsToDeck();
        setDrawables();
    }

    public void setDrawables(){
    getCommunityChestCardDeck().get(0).setImageId(R.drawable.community0);
    getCommunityChestCardDeck().get(1).setImageId(R.drawable.community1);
    getCommunityChestCardDeck().get(2).setImageId(R.drawable.community2);
    getCommunityChestCardDeck().get(3).setImageId(R.drawable.community3);
    getCommunityChestCardDeck().get(4).setImageId(R.drawable.community4);
    getCommunityChestCardDeck().get(5).setImageId(R.drawable.community5);
    getCommunityChestCardDeck().get(6).setImageId(R.drawable.community6);
    getCommunityChestCardDeck().get(7).setImageId(R.drawable.community7);
    getCommunityChestCardDeck().get(8).setImageId(R.drawable.community8);
    getCommunityChestCardDeck().get(9).setImageId(R.drawable.community9);
    getCommunityChestCardDeck().get(10).setImageId(R.drawable.community10);
    getCommunityChestCardDeck().get(11).setImageId(R.drawable.community11);
    getCommunityChestCardDeck().get(12).setImageId(R.drawable.community12);
    getCommunityChestCardDeck().get(13).setImageId(R.drawable.community13);
    getCommunityChestCardDeck().get(14).setImageId(R.drawable.community14);
    getCommunityChestCardDeck().get(15).setImageId(R.drawable.community15);
    getCommunityChestCardDeck().get(16).setImageId(R.drawable.community16);
    getCommunityChestCardDeck().get(17).setImageId(R.drawable.community17);
    getCommunityChestCardDeck().get(18).setImageId(R.drawable.community18);
    getCommunityChestCardDeck().get(19).setImageId(R.drawable.community19);
    getAllCommunityChestCards().get(0).setImageId(R.drawable.community0);
    getAllCommunityChestCards().get(1).setImageId(R.drawable.community1);
    getAllCommunityChestCards().get(2).setImageId(R.drawable.community2);
    getAllCommunityChestCards().get(3).setImageId(R.drawable.community3);
    getAllCommunityChestCards().get(4).setImageId(R.drawable.community4);
    getAllCommunityChestCards().get(5).setImageId(R.drawable.community5);
    getAllCommunityChestCards().get(6).setImageId(R.drawable.community6);
    getAllCommunityChestCards().get(7).setImageId(R.drawable.community7);
    getAllCommunityChestCards().get(8).setImageId(R.drawable.community8);
    getAllCommunityChestCards().get(9).setImageId(R.drawable.community9);
    getAllCommunityChestCards().get(10).setImageId(R.drawable.community10);
    getAllCommunityChestCards().get(11).setImageId(R.drawable.community11);
    getAllCommunityChestCards().get(12).setImageId(R.drawable.community12);
    getAllCommunityChestCards().get(13).setImageId(R.drawable.community13);
    getAllCommunityChestCards().get(14).setImageId(R.drawable.community14);
    getAllCommunityChestCards().get(15).setImageId(R.drawable.community15);
    getAllCommunityChestCards().get(16).setImageId(R.drawable.community16);
    getAllCommunityChestCards().get(17).setImageId(R.drawable.community17);
    getAllCommunityChestCards().get(18).setImageId(R.drawable.community18);
    getCommunityChestCardDeck().get(19).setImageId(R.drawable.community19);
}

    public void addToAllCommunityChestCards(CommunityChestCard card) {
        allCommunityChestCards.add(card);
    }

    public void addCardsToDeck() {
        setCommunityChestCardDeck((ArrayList<CommunityChestCard>) allCommunityChestCards.clone());
    }

    public int generateRandom() {
        Random r = new Random();
        //if (communityChestCardDeck.size() == 0) {
          //  return -1;
        //}
        int random = r.nextInt(communityChestCardDeck.size());
        random--;
        if (random <= 0){
            return 0;
        }
        return random;
    }

    public CommunityChestCard drawFromDeck() {
        int random = generateRandom();
        //if (random >= 0) {
            CommunityChestCard card = communityChestCardDeck.get(random);
            //removeCardFromDeck(random);
            return card;
        //} else return null;
    }

    public void removeCardFromDeck(int index) {
        communityChestCardDeck.remove(index);
    }

}
