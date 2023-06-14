package com.example.monopoly.gamelogic.properties;

import com.example.monopoly.R;
import com.example.monopoly.gamelogic.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public List<Field> getSortedPropertyListByPlayer(Player player){
        List<Field> fields = new ArrayList<>(properties.values());

        fields.sort((field, t1) -> {
            if(field.getOwner() != null && t1.getOwner() != null){
                if(field.getOwner().getUsername().equals(player.getUsername()) && t1.getOwner() == null){
                    return -1;
                } else if(t1.getOwner().getUsername().equals(player.getUsername()) && field.getOwner() == null){
                    return 1;
                } else {
                    return compareHouses(field, t1);
                }
            } else if (field.getOwner() != null) {
                return -1;
            } else if (t1.getOwner() != null) {
                return 1;
            } else {
                return 0;
            }
        });

        return fields;
    }

    private int compareHouses(Field field, Field t1) {
        if(field instanceof PropertyField && t1 instanceof PropertyField){
            return ((PropertyField) t1).getNumOfHouses() - ((PropertyField) field).getNumOfHouses();
        } else if (field instanceof PropertyField) {
            return -1;
        } else if (t1 instanceof  PropertyField) {
            return 1;
        } else {
            return 0;
        }
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

    public boolean hasAllColours(Player owner, PropertyFieldColors color) {
        return properties.values().stream().filter(x -> x instanceof PropertyField && ((PropertyField) x).getColor() == color).allMatch(x -> x.getOwner() != null && x.getOwner().equals(owner));
    }
}
