package com.example.monopoly.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.monopoly.network.Client;
import com.example.monopoly.ui.GameBoardUI;

public class GameBoardUIViewModel extends ViewModel {
    private final MutableLiveData<Boolean> uncover = new MutableLiveData<>();
    private final MutableLiveData<Boolean> throwDice = new MutableLiveData<>();
    private final MutableLiveData<String> currentPlayer = new MutableLiveData<>();


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
}
