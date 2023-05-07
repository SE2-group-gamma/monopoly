package com.example.monopoly.gamelogic;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Random;

public class Dices {
    private int dice1, dice2;
    private boolean isLastRollFlawed;

    private Random random;

    public Dices() {
        super();
        dice1 = 0;
        dice2 = 0;
        isLastRollFlawed = false;
        random = new Random();
    }

    public void rollDices(){
        isLastRollFlawed = false;
        dice1 = random.nextInt(6)+1;
        dice2 = random.nextInt(6)+1;
    }

    public void rollDicesFlawed(int sum) throws IllegalArgumentException{
        if(sum < 2 || sum > 12) throw new IllegalArgumentException("Invalid sum of two dices: " + sum);
        isLastRollFlawed = true;

        if(sum % 2 == 0) {
            dice1 = dice2 = sum / 2;
        } else {
            if(sum < 6) {
                dice1 = random.nextInt(sum/2)+1;
                dice2 = sum - dice1;
            } else {
                dice1 = random.nextInt(6)+1;
                dice2 = sum - dice1;
            }
        }
    }

    public int getDice1() {
        return dice1;
    }

    public int getDice2() {
        return dice2;
    }

    public int getSum() {
        return dice1 + dice2;
    }

    public boolean isLastRollFlawed() {
        return isLastRollFlawed;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("dice1=%d, dice2=%d, sum=%d, isflawed=%b", dice1, dice2, getSum(), isLastRollFlawed);
    }

}
