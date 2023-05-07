package com.example.monopoly.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.monopoly.gamelogic.Dices;

public class DiceViewModel extends ViewModel {
    private final MutableLiveData<Dices> dices = new MutableLiveData<>();
    public void setDices(Dices dices) {
        this.dices.setValue(dices);
    }
    public LiveData<Dices> getDicesData() {
        return this.dices;
    }
}
