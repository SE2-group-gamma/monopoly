package com.example.monopoly.ui.viewmodels;

import android.graphics.drawable.LayerDrawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

public class UIHandlerViewModel extends ViewModel {
    private final MutableLiveData<HashMap> playerObjects = new MutableLiveData<>();

    private final MutableLiveData<double[]> playerPositionX = new MutableLiveData<>();
    private final MutableLiveData<double[]> playerPositionY = new MutableLiveData<>();

    private final MutableLiveData<int[]> currentPosition = new MutableLiveData<>();

    private final MutableLiveData<Boolean> checkFirst = new MutableLiveData<>(true);

    private final MutableLiveData<int[]> playerGravityLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> currentMoney = new MutableLiveData<>();


    public MutableLiveData<int[]> getPlayerGravityLiveData() {
        return this.playerGravityLiveData;
    }
    public void setPlayerGravityLiveData(int[] playerGravityLiveData){
        this.playerGravityLiveData.setValue(playerGravityLiveData);
    }

    public void setCheckFirst(boolean checkFirst){
        this.checkFirst.setValue(checkFirst);
    }
    public LiveData<Boolean> getCheckFirst() {
        return this.checkFirst;
    }

    public void setPlayerObjects(HashMap<Integer, String> playerObjects){
        this.playerObjects.setValue(playerObjects);
    }
    public LiveData<HashMap> getPlayerObjects() {
        return this.playerObjects;
    }

    public void setPlayerPositionX(double[] playerPositionX){
        this.playerPositionX.setValue(playerPositionX);
    }
    public LiveData<double[]> getPlayerPositionX() {
        return this.playerPositionX;
    }

    public void setPlayerPositionY(double[] playerPositionY){
        this.playerPositionY.setValue(playerPositionY);
    }
    public LiveData<double[]> getPlayerPositionY() {
        return this.playerPositionY;
    }

    public void setCurrentMoney(int currentMoney){
        this.currentMoney.setValue(currentMoney);
    }
    public LiveData<Integer> getCurrentMoney() {
        return this.currentMoney;
    }


    public void setCurrentPosition(int[] currentPosition){
        this.currentPosition.setValue(currentPosition);
    }
    public LiveData<int[]> getCurrentPosition() {
        return this.currentPosition;
    }


}
