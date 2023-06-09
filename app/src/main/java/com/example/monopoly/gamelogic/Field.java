package com.example.monopoly.gamelogic;

import android.graphics.Color;
import android.media.Image;

public class Field {

    private int id;
    private String name;
    private String type;        //Type of Field
    private Color col;          //Fields with approximately the same price have the same color
    private int cost;           //Field Cost
    private int houses;         //Houses on Field
    private Player owner;       //Gets set if bought for the first time
    private int houseCost;      //Price per house
    private int mortgageValue;
    private int mortgageCancellationValue;
    private Image img;
    private int imageId;

    public Field(int id, String name, String type, Color col, int cost, int houses, Player owner, int houseCost, int mortgageValue, int mortgageCancellationValue, Image img) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.col = col;
        this.cost = cost;
        this.houses = houses;
        this.owner = owner;
        this.houseCost = houseCost;
        this.mortgageValue = mortgageValue;
        this.mortgageCancellationValue = mortgageCancellationValue;
        this.img = img;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setHouses(int houses) {
        this.houses = houses;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getHouseCost() {
        return houseCost;
    }

    public int getMortgageValue() {
        return mortgageValue;
    }

    public int getMortgageCancellationValue() {
        return mortgageCancellationValue;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Color getCol() {
        return col;
    }

    public int getCost() {
        return cost;
    }

    public int getHouses() {
        return houses;
    }

    public Player getOwner() {
        return owner;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}


