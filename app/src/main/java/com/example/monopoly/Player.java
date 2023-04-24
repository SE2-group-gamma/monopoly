package com.example.monopoly;

public class Player {
    private String id;
    private int diceValue;

    public Player(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getDiceValue() {
        return diceValue;
    }

    public void setDiceValue(int diceValue) {
        this.diceValue = diceValue;
    }
}
