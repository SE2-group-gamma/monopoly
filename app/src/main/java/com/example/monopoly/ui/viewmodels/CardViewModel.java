package com.example.monopoly.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.monopoly.gamelogic.ChanceCardCollection;
import com.example.monopoly.gamelogic.CommunityChestCardCollection;
public class CardViewModel extends ViewModel {

    private final MutableLiveData<ChanceCardCollection> chanceCards = new MutableLiveData<>();
    private final MutableLiveData<CommunityChestCardCollection> communityCards = new MutableLiveData<>();

    public void setChanceCards(ChanceCardCollection chanceCards) {
        this.chanceCards.setValue(chanceCards);
    }
    public LiveData<ChanceCardCollection> getChanceCards() {
        return this.chanceCards;
    }

    public void setCommunityCards(CommunityChestCardCollection communityCards) {
        this.communityCards.setValue(communityCards);
    }
    public LiveData<CommunityChestCardCollection> getCommunityCards() {
        return this.communityCards;
    }
}
