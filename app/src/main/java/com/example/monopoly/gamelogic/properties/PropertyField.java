package com.example.monopoly.gamelogic.properties;

import com.example.monopoly.gamelogic.Player;

public class PropertyField extends Field{
    private PropertyFieldColors color;
    private RentConfiguration rents;
    private int numOfHouses;
    private boolean hasHotel;

    public PropertyField(String id, int price, PropertyFieldColors color, RentConfiguration rents) {
        this.id = id;
        this.price = price;
        this.color = color;
        this.numOfHouses = 0;
        this.hasHotel = false;
        this.rents = rents;
        this.owner = null;
    }

    public void addHouse() {
        if(numOfHouses >= 4 || hasHotel) throw new IllegalStateException("Can not add house");
        numOfHouses++;
    }

    public void addHotel() {
        if(numOfHouses < 4 || hasHotel) throw new IllegalStateException("Can not buy hotel");
        this.hasHotel = true;
    }

    public Player getOwner(){
        return this.owner;
    }

    public boolean hasHotel(){
        return this.hasHotel;
    }

    public RentConfiguration getRent(){
        return this.rents;
    }
}
