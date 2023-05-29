package com.example.monopoly.gamelogic;

public class ChanceCard {
    private int id;
    private String function;

    private int imageID;

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

    public int getImageId(){
        return imageID;
    }

    public void setImageId(int imageID){
        this.imageID = imageID;
    }

}
