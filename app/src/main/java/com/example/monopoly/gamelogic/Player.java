package com.example.monopoly.gamelogic;

import android.graphics.Color;

import com.example.monopoly.network.Client;

public class Player {
    private String username;
    private Color col;
    private double capital;
    private boolean alive;
    private boolean inPrison;
    private Client myClient;        //Reference to the players client
    private int id;
    private int position;

    private boolean broke;

    private int totalAssetValue;

    private PlayerMapPosition playerMapPosition;

    private int doubletsCounter = 0;

    private boolean doubletsFirstChain = true;

    private int turnUnlockCounter = 0;


    public Player(String username, Color col, double capital, boolean alive) {
        this.username = username;
        this.col = col;
        this.capital = capital;
        this.alive = alive;
        this.inPrison = false;
        this.position = 0;
        this.broke = false;
        this.totalAssetValue=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getMyClient() {
        return myClient;
    }

    public void setMyClient(Client myClient) {
        this.myClient = myClient;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    public void incrementPosition(int diff){
        this.position+=diff;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

    public int getDoubletsCounter() {
        return doubletsCounter;
    }

    public void setDoubletsCounter(int doubletsCounter) {
        this.doubletsCounter = doubletsCounter;
    }

    public boolean isDoubletsFirstChain() {
        return doubletsFirstChain;
    }

    public void setDoubletsFirstChain(boolean doubletsFirstChain) {
        this.doubletsFirstChain = doubletsFirstChain;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isInPrison() {
        return inPrison;
    }

    public void setPlayerMapPosition(PlayerMapPosition playerMapPosition){
        this.playerMapPosition=playerMapPosition;
    }

    public PlayerMapPosition getPlayerMapPosition(){
        return this.playerMapPosition;
    }

    public boolean isBroke() {
        return broke;
    }

    public void setBroke(boolean broke) {
        this.broke = broke;
    }

    public int getTotalAssetValue() {
        return totalAssetValue;
    }

    public void setTotalAssetValue(int totalAssetValue) {
        this.totalAssetValue = totalAssetValue;
    }

    public void transferMoneyPlayerToPlayer(Player sender, Player receiver, double amount){
        double senderMoney = sender.getCapital();
        sender.setCapital(senderMoney-amount);

        double receiverMoney = receiver.getCapital();
        receiver.setCapital(receiverMoney+amount);
    }

    public int getTurnUnlockCounter() {
        return turnUnlockCounter;
    }

    public void setTurnUnlockCounter(int turnUnlockCounter) {
        this.turnUnlockCounter = turnUnlockCounter;
    }
}
