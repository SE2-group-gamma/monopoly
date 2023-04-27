package com.example.monopoly.gamelogic;

public class Bank {
    //starting amount for Bank
     private double cash= 500000.00;

     public Bank(){
         this.cash=cash;
     }

     public double getCash(){
         return this.cash;
     }

    public void setCash( double cash){
        this.cash=cash;
    }
    //used to pay out player e.g moving over "START"
    public void transferMoneyBankToPlayer(Player receiver, Bank bank, double amount){
        double senderMoney = bank.getCash();
        bank.setCash(senderMoney-amount);

        double receiverMoney = receiver.getCapital();
        receiver.setCapital(receiverMoney+amount);
    }
    // used to send money to the bank from given player e.g penalty
    public void transferMoneyPlayerToBank(Player sender, Bank bank, double amount){
        double senderMoney = sender.getCapital();
        sender.setCapital(senderMoney-amount);

        double receiverMoney = bank.getCash();
        bank.setCash(receiverMoney+amount);
    }

    //if Bank goes bankrupt
    public void savingBank(Bank bank){
         double savings=50000.00;
         bank.setCash(savings);
    }
}
