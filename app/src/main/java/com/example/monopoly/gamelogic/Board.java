package com.example.monopoly.gamelogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Board {
    private static final List<String> fieldNames = List.of(
            "go",
            "strandbad",
            "community",
            "city_arkaden",
            "income_tax",
            "s_bahn_kaernten",
            "europapark",
            "chance",
            "minimundus",
            "lindwurm",
            "jail",
            "wappensaal",
            "kelag",
            "leberkas_pepi",
            "eurospar",
            "s_bahn_tirol",
            "xxxlutz",
            "community",
            "billa",
            "hartlauer",
            "free_parking",
            "baerenland",
            "chance",
            "hussel",
            "wienerroither",
            "s_bahn_steiermark",
            "pumpe",
            "bollwerk",
            "water_works",
            "molly_malone",
            "goto_jail",
            "rathaus",
            "kreuzbergl",
            "community",
            "botanischer_garten",
            "s_bahn_wien",
            "chance",
            "maria_loretto",
            "luxury_tax",
            "reptilienzoo_happ"
    );

    public static String getFieldName(int id){
        return fieldNames.get(id);
    }
}
