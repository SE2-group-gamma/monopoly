package com.example.monopoly.gamelogic.properties;

import com.example.monopoly.gamelogic.Player;

public abstract class Field {
    private String name;
    private int price;
    private Player owner;

    private int imageId;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Player getOwner() {
        return owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
