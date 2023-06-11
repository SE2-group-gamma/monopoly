package com.example.monopoly.gamelogic;

public class PlayerMapPosition {
    private int positionX;
    private int positionY;
    private int round;

    public PlayerMapPosition(int positionX, int positionY, int round) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.round = round;
    }


    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
