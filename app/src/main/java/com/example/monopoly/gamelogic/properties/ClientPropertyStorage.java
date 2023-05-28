package com.example.monopoly.gamelogic.properties;

import com.example.monopoly.gamelogic.Player;

import java.util.HashMap;

public class ClientPropertyStorage {
    private HashMap<String, Field> properties;
    private static ClientPropertyStorage instance;

    public static ClientPropertyStorage getInstance(){
        if(instance == null) instance = new ClientPropertyStorage();
        return instance;
    }

    private ClientPropertyStorage() {
        this.properties = new HashMap<>();

        this.properties.put("strandbad", new PropertyField("strandbad", 60, PropertyFieldColors.PURPLE, new RentConfiguration(2, 4, 10, 30, 90, 160, 250, 50)));
        this.properties.put("city_arkaden", new PropertyField("city_arkaden", 60, PropertyFieldColors.PURPLE, new RentConfiguration(4, 8, 20, 60, 180, 320, 450, 50)));

        this.properties.put("europapark", new PropertyField("europapark", 100, PropertyFieldColors.LIGHTBLUE, new RentConfiguration(6, 12, 30, 90, 270, 400, 550, 50)));
        this.properties.put("minimundus", new PropertyField("minimundus", 100, PropertyFieldColors.LIGHTBLUE, new RentConfiguration(6, 12, 30, 90, 270, 400, 550, 50)));
        this.properties.put("lindwurm", new PropertyField("lindwurm", 120, PropertyFieldColors.LIGHTBLUE, new RentConfiguration(8, 16, 40, 100, 300, 450, 600, 50)));

        this.properties.put("wappensaal", new PropertyField("wappensaal", 140, PropertyFieldColors.PINK, new RentConfiguration(10, 20, 50, 150, 450, 625, 750, 100)));
        this.properties.put("leberkas_pepi", new PropertyField("leberkas_pepi", 140, PropertyFieldColors.PINK, new RentConfiguration(10, 20, 50, 150, 450, 625, 750, 100)));
        this.properties.put("eurospar", new PropertyField("eurospar", 160, PropertyFieldColors.PINK, new RentConfiguration(12, 24, 60, 180, 500, 700, 900, 100)));

        this.properties.put("xxxlutz", new PropertyField("xxxlutz", 180, PropertyFieldColors.ORANGE, new RentConfiguration(14, 28, 70, 200, 550, 750, 950, 100)));
        this.properties.put("billa", new PropertyField("billa", 180, PropertyFieldColors.ORANGE, new RentConfiguration(14, 28, 70, 200, 550, 750, 950, 100)));
        this.properties.put("hartlauer", new PropertyField("hartlauer", 200, PropertyFieldColors.ORANGE, new RentConfiguration(16, 32, 80, 220, 600, 800, 1000, 100)));

        this.properties.put("baerenland", new PropertyField("baerenland", 220, PropertyFieldColors.RED, new RentConfiguration(18, 36, 90, 250, 700, 875, 1050, 150)));
        this.properties.put("hussel", new PropertyField("hussel", 220, PropertyFieldColors.RED, new RentConfiguration(18, 36, 90, 250, 700, 875, 1050, 150)));
        this.properties.put("wienerroither", new PropertyField("wienerroither", 240, PropertyFieldColors.RED, new RentConfiguration(20, 40, 100, 300, 750, 925, 1100, 150)));

        this.properties.put("pumpe", new PropertyField("pumpe", 260, PropertyFieldColors.YELLOW, new RentConfiguration(22, 44, 110, 330, 800, 975, 1150, 150)));
        this.properties.put("bollwerk", new PropertyField("bollwerk", 260, PropertyFieldColors.YELLOW, new RentConfiguration(22, 44, 110, 330, 800, 975, 1150, 150)));
        this.properties.put("molly_malone", new PropertyField("molly_malone", 260, PropertyFieldColors.YELLOW, new RentConfiguration(22, 44, 110, 330, 800, 975, 1150, 150)));

        this.properties.put("rathaus", new PropertyField("rathaus", 300, PropertyFieldColors.GREEN, new RentConfiguration(26, 52, 130, 390, 900, 1100, 1275, 200)));
        this.properties.put("kreuzbergl", new PropertyField("kreuzbergl", 300, PropertyFieldColors.GREEN, new RentConfiguration(26, 52, 130, 390, 900, 1100, 1275, 200)));
        this.properties.put("botanischer_garten", new PropertyField("botanischer_garten", 320, PropertyFieldColors.GREEN, new RentConfiguration(28, 56, 150, 450, 1000, 1200, 1400, 200)));

        this.properties.put("maria_loretto", new PropertyField("maria_loretto", 350, PropertyFieldColors.BLUE, new RentConfiguration(35, 70, 175, 500, 1100, 1300, 1500, 200)));
        this.properties.put("reptilienzoo_happ", new PropertyField("reptilienzoo_happ", 400, PropertyFieldColors.BLUE, new RentConfiguration(50, 100, 200, 600, 1400, 1700, 2000, 200)));

        this.properties.put("s_bahn_kaernten", new TrainStation("s_bahn_kaernten"));
        this.properties.put("s_bahn_tirol", new TrainStation("s_bahn_kaernten"));
        this.properties.put("s_bahn_steiermark", new TrainStation("s_bahn_kaernten"));
        this.properties.put("s_bahn_wien", new TrainStation("s_bahn_kaernten"));

        this.properties.put("kelag", new UtilityField("kelag"));
        this.properties.put("water_works", new UtilityField("water_works"));
    }

    public void updateOwner(String id, Player newOwner){
        this.properties.get(id).setOwner(newOwner);
    }
}
