package com.example.monopoly.gamelogic.properties;

public class UtilityField extends Field{
    private static final int UTILITY_FIELD_PRICE = 200;

    public UtilityField(int imageId, String name) {
        this.setName(name);
        this.setImageId(imageId);
        this.setPrice(UTILITY_FIELD_PRICE);
        this.setOwner(null);
    }
}
