package com.example.monopoly.gamelogic.properties;

public class TrainStation extends Field{
    private static final int TRAIN_STATION_PRICE = 200;
    public TrainStation(String id) {
        this.setId(id);
        this.setPrice(TRAIN_STATION_PRICE);
        this.setOwner(null);
    }
}
