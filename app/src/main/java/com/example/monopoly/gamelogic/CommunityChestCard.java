package com.example.monopoly.gamelogic;

public class CommunityChestCard {
    private int id;
    private String function;
    private int imageID;

    public CommunityChestCard(int id) {
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
