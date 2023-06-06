package com.example.monopoly.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.monopoly.network.Client;
import com.example.monopoly.ui.GameBoardUI;

public class GameBoardUIViewModel extends ViewModel {
    private final MutableLiveData<Boolean> uncover = new MutableLiveData<>();
    private final MutableLiveData<Boolean> throwDice = new MutableLiveData<>();

    /*public void setClient(Client client) {
        this.client.setValue(client);
    }
    public LiveData<Client> getClientData() {
        return this.client;
    }*/
    public void setUncoverEnabled(boolean enabled){
        this.uncover.setValue(enabled);
    }
    public LiveData<Boolean> getUncoverEnabled() {
        return this.uncover;
    }
}
