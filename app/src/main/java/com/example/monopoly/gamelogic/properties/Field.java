package com.example.monopoly.gamelogic.properties;

import com.example.monopoly.gamelogic.Player;

public abstract class Field {
    private String id;
    private int price;
    private Player owner;

    public String getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public Player getOwner() {
        return owner;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
