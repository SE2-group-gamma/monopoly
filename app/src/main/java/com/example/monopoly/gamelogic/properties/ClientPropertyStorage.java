package com.example.monopoly.gamelogic.properties;

import com.example.monopoly.R;
import com.example.monopoly.gamelogic.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ClientPropertyStorage {
    private HashMap<String, Field> properties;
    private static ClientPropertyStorage instance;

    public static ClientPropertyStorage getInstance(){
        if(instance == null) instance = new ClientPropertyStorage();
        return instance;
    }

    private ClientPropertyStorage() {
        this.properties = PropertyUtils.getPropertyFields();
    }

    public void updateOwner(String id, Player newOwner){
        this.properties.get(id).setOwner(newOwner);
    }

    public List<Field> getPropertyList(){
        return List.copyOf(properties.values());
    }

    public Field getProperty(String id){
        if(!this.properties.containsKey(id)) throw new IllegalFieldException("Invalid field id");
        return this.properties.get(id);
    }

    public void addHouse(String id){
        if(!this.properties.containsKey(id)) throw new IllegalFieldException("Invalid field id");
        Field f = properties.get(id);
        if(!(f instanceof PropertyField)) throw new IllegalFieldException("Field is not a property field");
        ((PropertyField) f).addHouse();
    }

    public void addHotel(String id){
        if(!this.properties.containsKey(id)) throw new IllegalFieldException("Invalid field id");
        Field f = properties.get(id);
        if(!(f instanceof PropertyField)) throw new IllegalFieldException("Field is not a property field");
        ((PropertyField) f).addHotel();
    }
}
