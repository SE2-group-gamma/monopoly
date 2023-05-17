package com.example.monopoly.gamelogic.properties;

import java.util.List;

public class PropertyStorage {
    public List<PropertyField> properties;

    public PropertyStorage() {
        this.properties = List.of(
            new PropertyField("strandbad", 60, PropertyFieldColors.PURPLE, new RentConfiguration(2, 4, 10, 30, 90, 160, 250, 50)),
            new PropertyField("city_arkaden", 60, PropertyFieldColors.PURPLE, new RentConfiguration(4, 8, 20, 60, 180, 320, 450, 50)),

            new PropertyField("europapark", 100, PropertyFieldColors.LIGHTBLUE, new RentConfiguration(6, 12, 30, 90, 270, 400, 550, 50)),
            new PropertyField("minimundus", 100, PropertyFieldColors.LIGHTBLUE, new RentConfiguration(6, 12, 30, 90, 270, 400, 550, 50)),
            new PropertyField("lindwurm", 120, PropertyFieldColors.LIGHTBLUE, new RentConfiguration(8, 16, 40, 100, 300, 450, 600, 50)),

            new PropertyField("wappensaal", 140, PropertyFieldColors.PINK, new RentConfiguration(10, 20, 50, 150, 450, 625, 750, 100)),
            new PropertyField("leberkas_pepi", 140, PropertyFieldColors.PINK, new RentConfiguration(10, 20, 50, 150, 450, 625, 750, 100)),
            new PropertyField("eurospar", 160, PropertyFieldColors.PINK, new RentConfiguration(12, 24, 60, 180, 500, 700, 900, 100)),

            new PropertyField("xxxlutz", 180, PropertyFieldColors.ORANGE, new RentConfiguration(14, 28, 70, 200, 550, 750, 950, 100)),
            new PropertyField("billa", 180, PropertyFieldColors.ORANGE, new RentConfiguration(14, 28, 70, 200, 550, 750, 950, 100)),
            new PropertyField("hartlauer", 200, PropertyFieldColors.ORANGE, new RentConfiguration(16, 32, 80, 220, 600, 800, 1000, 100)),

            new PropertyField("baerenland", 220, PropertyFieldColors.RED, new RentConfiguration(18, 36, 90, 250, 700, 875, 1050, 150)),
            new PropertyField("hussel", 220, PropertyFieldColors.RED, new RentConfiguration(18, 36, 90, 250, 700, 875, 1050, 150)),
            new PropertyField("wienerroither", 240, PropertyFieldColors.RED, new RentConfiguration(20, 40, 100, 300, 750, 925, 1100, 150)),

            new PropertyField("pumpe", 260, PropertyFieldColors.YELLOW, new RentConfiguration(22, 44, 110, 330, 800, 975, 1150, 150)),
            new PropertyField("bollwerk", 260, PropertyFieldColors.YELLOW, new RentConfiguration(22, 44, 110, 330, 800, 975, 1150, 150)),
            new PropertyField("molly_malone", 260, PropertyFieldColors.YELLOW, new RentConfiguration(22, 44, 110, 330, 800, 975, 1150, 150)),

            new PropertyField("rathaus", 300, PropertyFieldColors.GREEN, new RentConfiguration(26, 52, 130, 390, 900, 1100, 1275, 200)),
            new PropertyField("kreuzbergl", 300, PropertyFieldColors.GREEN, new RentConfiguration(26, 52, 130, 390, 900, 1100, 1275, 200)),
            new PropertyField("botanischer_garten", 320, PropertyFieldColors.GREEN, new RentConfiguration(28, 56, 150, 450, 1000, 1200, 1400, 200)),

            new PropertyField("maria_loretto", 350, PropertyFieldColors.BLUE, new RentConfiguration(35, 70, 175, 500, 1100, 1300, 1500, 200)),
            new PropertyField("reptilienzoo_happ", 400, PropertyFieldColors.BLUE, new RentConfiguration(50, 100, 200, 600, 1400, 1700, 2000, 200))
        );
    }
}
