package com.example.monopoly.gamelogic.properties;

import com.example.monopoly.R;
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
        this.properties = new HashMap<>();

        this.properties.put("strandbad", new PropertyField(R.drawable.strandbad,"strandbad", 60, PropertyFieldColors.PURPLE, new RentConfiguration(2, 4, 10, 30, 90, 160, 250, 50)));
        this.properties.put("city_arkaden", new PropertyField(R.drawable.city_arkaden,"city_arkaden", 60, PropertyFieldColors.PURPLE, new RentConfiguration(4, 8, 20, 60, 180, 320, 450, 50)));

        this.properties.put("europapark", new PropertyField(R.drawable.europapark,"europapark", 100, PropertyFieldColors.LIGHTBLUE, new RentConfiguration(6, 12, 30, 90, 270, 400, 550, 50)));
        this.properties.put("minimundus", new PropertyField(R.drawable.minimundus,"minimundus", 100, PropertyFieldColors.LIGHTBLUE, new RentConfiguration(6, 12, 30, 90, 270, 400, 550, 50)));
        this.properties.put("lindwurm", new PropertyField(R.drawable.lindwurm,"lindwurm", 120, PropertyFieldColors.LIGHTBLUE, new RentConfiguration(8, 16, 40, 100, 300, 450, 600, 50)));

        this.properties.put("wappensaal", new PropertyField(R.drawable.wappensaal,"wappensaal", 140, PropertyFieldColors.PINK, new RentConfiguration(10, 20, 50, 150, 450, 625, 750, 100)));
        this.properties.put("leberkas_pepi", new PropertyField(R.drawable.leberkas_pepi, "leberkas_pepi", 140, PropertyFieldColors.PINK, new RentConfiguration(10, 20, 50, 150, 450, 625, 750, 100)));
        this.properties.put("eurospar", new PropertyField(R.drawable.eurospar,"eurospar", 160, PropertyFieldColors.PINK, new RentConfiguration(12, 24, 60, 180, 500, 700, 900, 100)));

        this.properties.put("xxxlutz", new PropertyField(R.drawable.xxxlutz,"xxxlutz", 180, PropertyFieldColors.ORANGE, new RentConfiguration(14, 28, 70, 200, 550, 750, 950, 100)));
        this.properties.put("billa", new PropertyField(R.drawable.billa,"billa", 180, PropertyFieldColors.ORANGE, new RentConfiguration(14, 28, 70, 200, 550, 750, 950, 100)));
        this.properties.put("hartlauer", new PropertyField(R.drawable.hartlauer,"hartlauer", 200, PropertyFieldColors.ORANGE, new RentConfiguration(16, 32, 80, 220, 600, 800, 1000, 100)));

        this.properties.put("baerenland", new PropertyField(R.drawable.baerenland,"baerenland", 220, PropertyFieldColors.RED, new RentConfiguration(18, 36, 90, 250, 700, 875, 1050, 150)));
        this.properties.put("hussel", new PropertyField(R.drawable.hussel,"hussel", 220, PropertyFieldColors.RED, new RentConfiguration(18, 36, 90, 250, 700, 875, 1050, 150)));
        this.properties.put("wienerroither", new PropertyField(R.drawable.wienerroither,"wienerroither", 240, PropertyFieldColors.RED, new RentConfiguration(20, 40, 100, 300, 750, 925, 1100, 150)));

        this.properties.put("pumpe", new PropertyField(R.drawable.pumpe,"pumpe", 260, PropertyFieldColors.YELLOW, new RentConfiguration(22, 44, 110, 330, 800, 975, 1150, 150)));
        this.properties.put("bollwerk", new PropertyField(R.drawable.bollwerk,"bollwerk", 260, PropertyFieldColors.YELLOW, new RentConfiguration(22, 44, 110, 330, 800, 975, 1150, 150)));
        this.properties.put("molly_malone", new PropertyField(R.drawable.molly_malone,"molly_malone", 260, PropertyFieldColors.YELLOW, new RentConfiguration(22, 44, 110, 330, 800, 975, 1150, 150)));

        this.properties.put("rathaus", new PropertyField(R.drawable.rathaus,"rathaus", 300, PropertyFieldColors.GREEN, new RentConfiguration(26, 52, 130, 390, 900, 1100, 1275, 200)));
        this.properties.put("kreuzbergl", new PropertyField(R.drawable.kreuzbergl,"kreuzbergl", 300, PropertyFieldColors.GREEN, new RentConfiguration(26, 52, 130, 390, 900, 1100, 1275, 200)));
        this.properties.put("botanischer_garten", new PropertyField(R.drawable.botanischer_garten,"botanischer_garten", 320, PropertyFieldColors.GREEN, new RentConfiguration(28, 56, 150, 450, 1000, 1200, 1400, 200)));

        this.properties.put("maria_loretto", new PropertyField(R.drawable.maria_loretto,"maria_loretto", 350, PropertyFieldColors.BLUE, new RentConfiguration(35, 70, 175, 500, 1100, 1300, 1500, 200)));
        this.properties.put("reptilienzoo_happ", new PropertyField(R.drawable.reptilienzoo_happ,"reptilienzoo_happ", 400, PropertyFieldColors.BLUE, new RentConfiguration(50, 100, 200, 600, 1400, 1700, 2000, 200)));

        this.properties.put("s_bahn_kaernten", new TrainStation(R.drawable.s_bahn_kaernten,"s_bahn_kaernten"));
        this.properties.put("s_bahn_tirol", new TrainStation(R.drawable.s_bahn_tirol,"s_bahn_tirol"));
        this.properties.put("s_bahn_steiermark", new TrainStation(R.drawable.s_bahn_steiermark,"s_bahn_steiermark"));
        this.properties.put("s_bahn_wien", new TrainStation(R.drawable.s_bahn_wien,"s_bahn_wien"));

        this.properties.put("kelag", new UtilityField(R.drawable.kelag,"kelag"));
        this.properties.put("water_works", new UtilityField(R.drawable.water_works,"water_works"));
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
        }
        throw new IllegalFieldException("Field is not a colored property");
    }

    public void buyProperty(String propertyId, Player owner) {
        if(!hasField(propertyId)) throw new IllegalFieldException("Invalid field name: " + propertyId);
        this.properties.get(propertyId).setOwner(owner);
    }

    public int addHouse(String propertyId){
        if(!hasField(propertyId)) throw new IllegalFieldException("Invalid field name: " + propertyId);
        Field field = this.properties.get(propertyId);
        if(!(field instanceof PropertyField)) throw new IllegalFieldException("Field is not a PropertyField");
        if(!hasAllColours(field.getOwner(), ((PropertyField) field).getColor())) throw new IllegalStateException("Owner does not have all colors");
        ((PropertyField) field).addHouse();
        return ((PropertyField) field).getNumOfHouses();
    }

    public void addHotel(String propertyId){
        if(!hasField(propertyId)) throw new IllegalFieldException("Invalid field name: " + propertyId);
        Field field = this.properties.get(propertyId);
        if(!(field instanceof PropertyField)) throw new IllegalFieldException("Field is not a PropertyField");
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

    public Player getOwner(String propertyId){
        return properties.get(propertyId).getOwner();
    }
}
