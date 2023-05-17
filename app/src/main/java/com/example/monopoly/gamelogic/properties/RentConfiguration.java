package com.example.monopoly.gamelogic.properties;

public class RentConfiguration {
    private int rent, rentAllColors, rent1House, rent2Houses, rent3Houses, rent4Houses, rentHotel;
    private int priceHouseOrHotel;

    public RentConfiguration(int rent, int rentAllColors, int rent1House, int rent2Houses, int rent3Houses, int rent4Houses, int rentHotel, int priceHouseOrHotel) {
        this.rent = rent;
        this.rentAllColors = rentAllColors;
        this.rent1House = rent1House;
        this.rent2Houses = rent2Houses;
        this.rent3Houses = rent3Houses;
        this.rent4Houses = rent4Houses;
        this.rentHotel = rentHotel;
        this.priceHouseOrHotel = priceHouseOrHotel;
    }

    public int getRent() {
        return rent;
    }

    public int getRentAllColors() {
        return rentAllColors;
    }

    public int getRent1House() {
        return rent1House;
    }

    public int getRent2Houses() {
        return rent2Houses;
    }

    public int getRent3Houses() {
        return rent3Houses;
    }

    public int getRent4Houses() {
        return rent4Houses;
    }

    public int getRentHotel() {
        return rentHotel;
    }

    public int getPriceHouseOrHotel() {
        return priceHouseOrHotel;
    }
}
