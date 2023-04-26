package com.example.monopoly.gamelogic;

import java.util.Random;

public class Dices {
    private int dice1, dice2;
    private boolean isLastRollFlawed;

    private Random random;

    public Dices() {
        super();
        this.dice1 = 0;
        this.dice2 = 0;
        this.isLastRollFlawed = false;
        random = new Random();
    }

    public void rollDices(){
        this.isLastRollFlawed = false;
        this.dice1 = random.nextInt(6)+1;
        this.dice2 = random.nextInt(6)+1;
    }

    public void rollDicesFlawed(int sum) throws IllegalArgumentException{
        if(sum < 2 || sum > 12) throw new IllegalArgumentException("Invalid sum of two dices: " + sum);
        this.isLastRollFlawed = true;
        if (sum > 6)
            this.dice1 = random.nextInt(6)+1;
        else
            this.dice1 = random.nextInt(sum-1)+1;
        this.dice2 = sum - this.dice1;
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
}
