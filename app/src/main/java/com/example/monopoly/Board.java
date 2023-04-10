package com.example.monopoly;

import java.util.ArrayList;

public class Board {

    private int id;
    private ArrayList<Field> fields = new ArrayList<Field>();

    public Board(int id, ArrayList<Field> fields) {
        this.id = id;
        this.fields = fields;
    }
}
