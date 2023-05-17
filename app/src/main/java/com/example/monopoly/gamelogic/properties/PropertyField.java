package com.example.monopoly.gamelogic.properties;

import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.network.Client;

public class PropertyField extends Field{
    private PropertyFieldColors color;
    private RentConfiguration rents;
    private int numOfHouses;
    private boolean hasHotel;
    private Player owner;

    public PropertyField(String name, int price, PropertyFieldColors color, RentConfiguration rents) {
        this.name = name;
        this.price = price;
        this.color = color;
        this.numOfHouses = 0;
        this.hasHotel = false;
        this.rents = rents;
        this.owner = null;
    }

    private void addHouse() {
        if(numOfHouses >= 4 || hasHotel) throw new IllegalStateException("Can not add house");
        numOfHouses++;
    }

    private void addHotel() {
        if(numOfHouses < 4 || hasHotel) throw new IllegalStateException("Can not buy hotel");
        this.hasHotel = true;
    }
}
