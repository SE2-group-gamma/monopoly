package com.example.monopoly.gamelogic.properties;

public class PropertyField extends Field{
    private PropertyFieldColors color;
    private RentConfiguration rents;
    private int numOfHouses;
    private boolean hasHotel;

    public PropertyField(int imageId, String name, int price, PropertyFieldColors color, RentConfiguration rents) {
        this.setImageId(imageId);
        this.setName(name);
        this.setPrice(price);
        this.color = color;
        this.numOfHouses = 0;
        this.hasHotel = false;
        this.rents = rents;
        this.setOwner(null);
    }

    public void addHouse() {
        if(numOfHouses >= 4 || hasHotel) throw new IllegalStateException("Can not add house");
        numOfHouses++;
    }

    public void addHotel() {
        if(numOfHouses < 4 || hasHotel) throw new IllegalStateException("Can not buy hotel");
        this.hasHotel = true;
    }
    public boolean hasHotel(){
        return this.hasHotel;
    }

    public RentConfiguration getRent(){
        return this.rents;
    }
}
