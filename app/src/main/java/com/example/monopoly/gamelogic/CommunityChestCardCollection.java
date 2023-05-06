package com.example.monopoly.gamelogic;

import java.util.ArrayList;

public class CommunityChestCardCollection {
    private ArrayList<CommunityChestCard> allCommunityChestCards = new ArrayList<CommunityChestCard>();

    private ArrayList<CommunityChestCard> communityChestCardDeck = new ArrayList<CommunityChestCard>();

    public CommunityChestCardCollection() {

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


}
