package com.example.monopoly;

import android.graphics.Color;

public class Player {
    private String username;
    private Color col;
    private double capital;
    private boolean alive;
    private boolean inPrison;

    public Player(String username, Color col, double capital, boolean alive) {
        this.username = username;
        this.col = col;
        this.capital = capital;
        this.alive = true;
        this.inPrison = false;
    }
}
