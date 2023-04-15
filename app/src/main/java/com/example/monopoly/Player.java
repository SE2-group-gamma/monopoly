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
        this.alive = alive;
        this.inPrison = false;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setInPrison(boolean inPrison) {
        this.inPrison = inPrison;
    }

    public String getUsername() {
        return username;
    }

    public Color getCol() {
        return col;
    }

    public double getCapital() {
        return capital;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isInPrison() {
        return inPrison;
    }
}
