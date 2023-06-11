package com.example.monopoly.gamelogic.properties;

public class TrainStation extends Field{
    private static final int TRAIN_STATION_PRICE = 200;
    public TrainStation(int imageId, String name) {
        this.setImageId(imageId);
        this.setName(name);
        this.setPrice(TRAIN_STATION_PRICE);
        this.setOwner(null);
    }
}
