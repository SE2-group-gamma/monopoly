package com.example.monopoly.gamelogic;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;

public class ChanceCard {
    private int id;
    private String function;
    private Drawable image;

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

    public Drawable getImage(){
        return image;
    }

    public void setImage(Drawable image){
        this.image = image;
    }


}
