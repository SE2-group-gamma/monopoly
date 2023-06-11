package com.example.monopoly.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameBoardUIViewModel extends ViewModel {
    private final MutableLiveData<Boolean> uncover = new MutableLiveData<>();
    private final MutableLiveData<Boolean> throwDice = new MutableLiveData<>();
    private final MutableLiveData<String> currentPlayer = new MutableLiveData<>();

    private final MutableLiveData<String> currentMoney = new MutableLiveData<>();

    private final MutableLiveData<String> currentTime = new MutableLiveData<>();


    public void setUncoverEnabled(boolean enabled){
        this.uncover.setValue(enabled);
    }
    public LiveData<Boolean> getUncoverEnabled() {
        return this.uncover;
    }
    public void setCurrentPlayer(String currentPlayer){
        this.currentPlayer.setValue(currentPlayer);
    }
    public LiveData<String> getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void setCurrentMoney(String currentMoney){
        this.currentMoney.setValue(currentMoney);
    }
    public LiveData<String> getCurrentMoney() {
        return this.currentMoney;
    }

    public MutableLiveData<String> getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime){
        this.currentTime.setValue(currentTime);
    }
}
