package com.example.monopoly;

import com.example.monopoly.gamelogic.ChanceCard;
import com.example.monopoly.gamelogic.ChanceCardCollection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class ChanceCardTest {

    public ChanceCardCollection collection;
    public ArrayList<ChanceCard> list;

    @BeforeEach
    public void createCollection(){
        list = new ArrayList<ChanceCard>();
        collection = new ChanceCardCollection();
        collection.fillList();
        list = collection.getList();
    }

    @Test
    public void testFill(){
        Assertions.assertEquals(11,list.size());
    }

    @Test
    public void printCollection(){
        for (int i =0; i<list.size(); i++){
            ChanceCard card = (ChanceCard) list.get(i);
            System.out.println(card.getFunction());
        }
    }
}
