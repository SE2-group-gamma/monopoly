package com.example.monopoly.gamelogic;

public class ChanceCard {
    private int id;
    private String function;

   public ChanceCard(int id) {
       this.id = id;
   }

   public int getId(){
        return id;
   }

   public void setId(int id){
        this.id = id;
   }

   public String getFunction(){
        return function;
   }

   public void setFunction(String function){
        this.function = function;
   }


}
