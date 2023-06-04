package com.example.monopoly.gamelogic;

import java.util.ArrayList;

public class Board {

    private int id;
    private ArrayList<Field> fields = new ArrayList<Field>();
    public static final int FELDER_ANZAHL = 40;
    public static final int GELD_LOS = 200;

    public Board(int id, ArrayList<Field> fields) {
        this.id = id;
        this.fields = fields;
    }

}
