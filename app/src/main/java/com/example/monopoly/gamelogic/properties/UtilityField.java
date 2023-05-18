package com.example.monopoly.gamelogic.properties;

public class UtilityField extends Field{
    private static final int UTILITY_FIELD_PRICE = 200;

    public UtilityField(String id) {
        this.id = id;
        this.price = UTILITY_FIELD_PRICE;
        this.owner = null;
    }
}
