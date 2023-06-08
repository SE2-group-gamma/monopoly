package com.example.monopoly.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.monopoly.network.Client;
import com.example.monopoly.ui.GameBoardUI;

public class GameBoardUIViewModel extends ViewModel {
    private final MutableLiveData<Boolean> uncover = new MutableLiveData<>();
    private final MutableLiveData<Boolean> throwDice = new MutableLiveData<>();
    private final MutableLiveData<Boolean> endTurn = new MutableLiveData<>();
    private final MutableLiveData<String> currentPlayer = new MutableLiveData<>();


    public void setUncoverEnabled(boolean enabled){
        this.uncover.setValue(enabled);
    }
    public void setThrowDiceEnabled(boolean enabled){
        this.throwDice.setValue(enabled);
    }
    public void setEndTurnEnabled(boolean enabled){
        this.endTurn.setValue(enabled);
    }
    public LiveData<Boolean> getUncoverEnabled() {
        return this.uncover;
    }
    public LiveData<Boolean> getThrowDiceEnabled() {
        return this.throwDice;
    }
    public LiveData<Boolean> getEndTurnEnabled() {
        return this.endTurn;
    }
    public void setCurrentPlayer(String currentPlayer){
        this.currentPlayer.setValue(currentPlayer);
    }
    public LiveData<String> getCurrentPlayer() {
        return this.currentPlayer;
    }
}
