package com.example.monopoly.gamelogic.properties;

public class TrainStation extends Field{
    private static final int TRAIN_STATION_PRICE = 200;
    public TrainStation(String id) {
        this.id = id;
        this.price = TRAIN_STATION_PRICE;
        this.owner = null;
    }
}
