package com.example.monopoly.gamelogic.properties;

import com.example.monopoly.gamelogic.Player;

import java.util.HashMap;

public class PropertyStorage {
    private HashMap<String, Field> properties;
    private static PropertyStorage instance;

    public static PropertyStorage getInstance(){
        if(instance == null) instance = new PropertyStorage();
        return instance;
    }

    private PropertyStorage() {
        this.properties = PropertyUtils.getPropertyFields();
    }

    public int getRentOnPropertyField(String propertyId, Player player) {
        if(!hasField(propertyId)) throw new IllegalFieldException("Invalid field name: " + propertyId);
        Field property = properties.get(propertyId);
        if(property.getOwner() == null || property.getOwner().equals(player)) return 0;
        if(property instanceof PropertyField){
            RentConfiguration rents = ((PropertyField) property).getRent();
            if(((PropertyField) property).hasHotel()) return rents.getRentHotel();
            switch (((PropertyField) property).getNumOfHouses()) {
                case 1:
                    return rents.getRent1House();
                case 2:
                    return rents.getRent2Houses();
                case 3:
                    return rents.getRent3Houses();
                case 4:
                    return rents.getRent4Houses();
                case 0:
                    return hasAllColours(property.getOwner(), ((PropertyField) property).getColor()) ? rents.getRentAllColors() : rents.getRent();
                default:
                    throw new IllegalFieldException("Field has too many houses");
            }
        } else if(property instanceof TrainStation) {
            switch ((int) numOfTrainStations(property.getOwner())){
                case 1:
                    return 25;
                case 2:
                    return 50;
                case 3:
                    return 100;
                case 4:
                    return 200;
                default:
                    return 0;
            }
        } else if(property instanceof UtilityField){
            return 150;
        }
        throw new IllegalFieldException("Field is not a colored property or train station");
    }

    public void buyProperty(String propertyId, Player owner) {
        if(!hasField(propertyId)) throw new IllegalFieldException("Invalid field name: " + propertyId);
        this.properties.get(propertyId).setOwner(owner);
    }

    public int addHouse(String propertyId, Player player){
        if(!hasField(propertyId)) throw new IllegalFieldException();
        Field field = this.properties.get(propertyId);
        if(!(field instanceof PropertyField)) throw new IllegalFieldException("Field is not a PropertyField");
        if(!hasAllColours(field.getOwner(), ((PropertyField) field).getColor())) throw new IllegalStateException("Owner does not have all colors");
        ((PropertyField) field).addHouse();
        player.setCapital(player.getCapital()-((PropertyField) field).getRent().getPriceHouseOrHotel());
        return ((PropertyField) field).getNumOfHouses();
    }

    public void addHotel(String propertyId, Player player){
        if(!hasField(propertyId)) throw new IllegalFieldException("Invalid field name: " + propertyId);
        Field field = this.properties.get(propertyId);
        if(!(field instanceof PropertyField)) throw new IllegalFieldException("Field is not a PropertyField");
        player.setCapital(player.getCapital()-((PropertyField) field).getRent().getPriceHouseOrHotel());
        ((PropertyField) field).addHotel();
    }

    private boolean hasAllColours(Player owner, PropertyFieldColors color) {
        return properties.values().stream().filter(x -> x instanceof PropertyField && ((PropertyField) x).getColor() == color).allMatch(x -> x.getOwner() != null && x.getOwner().equals(owner));
    }

    private long numOfTrainStations(Player owner) {
        return properties.values().stream().filter(x -> x instanceof TrainStation && x.getOwner() != null && x.getOwner().equals(owner)).count();
    }

    public boolean hasField(String name) {
        return properties.containsKey(name);
    }

    public String getOwnerName(String propertyId){
        return properties.get(propertyId).getOwner().getUsername();
    }
    public Player getOwner(String propertyId){
        return properties.get(propertyId).getOwner();
    }


    public int getTotalAssets(Player player) {
        int totalValue = 0;

        for(Field field : properties.values()) {
            if(field.getOwner() != null && field.getOwner().equals(player)) {
                totalValue += field.getPrice();
                if(field instanceof PropertyField) {
                    PropertyField propertyField = (PropertyField) field;
                    int numOfHouses = propertyField.getNumOfHouses();
                    boolean hasHotel = propertyField.hasHotel();
                    totalValue += (numOfHouses * propertyField.getRent().getPriceHouseOrHotel()) + (hasHotel ? propertyField.getRent().getPriceHouseOrHotel() : 0);
                }
            }
        }
        return totalValue;
    }

    public HashMap<String, Field> getProperties() {
        return properties;
    }
}
