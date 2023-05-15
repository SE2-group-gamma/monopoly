package com.example.monopoly.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.monopoly.gamelogic.Dices;
import com.example.monopoly.network.Client;

public class ClientViewModel extends ViewModel {

    private final MutableLiveData<Client> client = new MutableLiveData<>();
    public void setClient(Client client) {
        this.client.setValue(client);
    }
    public LiveData<Client> getClientData() {
        return this.client;
    }
}
