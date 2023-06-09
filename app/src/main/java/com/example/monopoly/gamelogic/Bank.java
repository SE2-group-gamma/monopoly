package com.example.monopoly.gamelogic;

public class Bank {
    /**
     * SINGLETON OBJECT
     */
     private double cash= 500000.00;
     private static final Bank INSTANCE = new Bank();
     private Bank(){
     }
     public static Bank getInstance(){
         return INSTANCE;
     }

     public double getCash(){
         return this.cash;
     }

    public void setCash( double cash){
        this.cash=cash;
    }
    public void transferMoneyBankToPlayer(Player receiver, double amount){
        double senderMoney = this.getCash();
        this.setCash(senderMoney-amount);

        double receiverMoney = receiver.getCapital();
        receiver.setCapital(receiverMoney+amount);
    }
    public void transferMoneyPlayerToBank(Player sender, double amount){
        double senderMoney = sender.getCapital();
        sender.setCapital(senderMoney-amount);

        double receiverMoney = this.getCash();
        this.setCash(receiverMoney+amount);
    }
    

    public void savingBank(Bank bank){
         double savings=500000.00;
         bank.setCash(savings);
    }
}
