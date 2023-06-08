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
    private int cardID;
    private int outOfJailFreeCounter;


    public Player(String username, Color col, double capital, boolean alive) {
        this.username = username;
        this.col = col;
        this.capital = capital;
        this.alive = alive;
        this.inPrison = false;
        this.position = 0;
    }

    public int getCardID(){
        return cardID;
    }

    public void setCardID(int cardID){
        this.cardID = cardID;
    }

    public int getOutOfJailFreeCounter() {
        return outOfJailFreeCounter;
    }

    public void setOutOfJailFreeCounter(int outOfJailFreeCounter){
        this.outOfJailFreeCounter = outOfJailFreeCounter;
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

    public boolean isAlive() {
        return alive;
    }

    public boolean isInPrison() {
        return inPrison;
    }

    public void transferMoneyPlayerToPlayer(Player sender, Player receiver, double amount){
        double senderMoney = sender.getCapital();
        sender.setCapital(senderMoney-amount);

        double receiverMoney = receiver.getCapital();
        receiver.setCapital(receiverMoney+amount);
    }
}
