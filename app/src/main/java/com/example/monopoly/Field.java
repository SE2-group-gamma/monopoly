package com.example.monopoly;

import android.graphics.Color;

public class Field {

    private int id;
    private String type;
    private Color col;          //Fields with approximately the same price have the same color
    private int cost;
    private Player owner;       //Gets set if bought for the first time

    public Field(int id, String type, Color col, int cost) {
        this.id = id;
        this.type = type;
        this.col = col;
        this.cost = cost;
    }

}


