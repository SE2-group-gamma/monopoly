package com.example.monopoly.gamelogic;

public class Money {
    private double cash;
    public Money(int cash){
        this.cash=cash;
    }

    public double  getCash(){
        return this.cash;
    }

    public void setCash( double cash){
        this.cash=cash;
    }
    // can also be added to Player class and delete this one
    // transfer Money between players e.g rent
     /*
    public void transferMoneyPlayerToPlayer(Player sender, Player receiver, double amount){
        double senderMoney = sender.getCapital();
        sender.setCapital(senderMoney-amount);

        double receiverMoney = receiver.getCapital();
        receiver.setCapital(receiverMoney+amount);
    }
    // transfer Money between Player and Bank
   public void transferMoneyBankToPlayer(Player sender,Bank bank, double amount){
        double senderMoney = sender.getCapital();
        sender.setCapital(senderMoney-amount);

        double bankMoney=bank.getCash();
        bank.setCash(bankMoney+amount);
    }*/


}



