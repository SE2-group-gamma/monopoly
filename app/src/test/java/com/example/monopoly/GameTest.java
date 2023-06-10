package com.example.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.graphics.Color;
import android.media.Image;

import com.example.monopoly.gamelogic.Field;
import com.example.monopoly.gamelogic.Game;
import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.network.Client;
import com.example.monopoly.network.MonopolyServer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockStatic;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

public class GameTest {

    Game g;
    Player p;

    Field field;
    HashMap<Integer,Player> players;
    HashMap<Integer,Field> fields;

    @Mock
    MonopolyServer mockServer;
    @Mock
    Client mockClient;

   @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        g = Game.getInstance();
        p = new Player("Test",new Color(),100.00,true);
        fields = new HashMap<>();
    }


    @Test
    public void testAddPlayer(){
        assertEquals(0,g.getPlayers().size());
        assertEquals(true,g.addPlayer(p));
        assertEquals(p,g.getPlayers().get(0));
        assertEquals(1,g.getPlayers().size());
        assertEquals(false,g.addPlayer(p));
        assertEquals(1,g.getPlayers().size());
    }
    @Test
    public void advanceAndCollect() throws IOException {
       for(int i =0; i<3; i++) {
           field = new Field(1, "Strandbad", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
           fields.put(0, field);
           g.setFields(fields);

           p.setPosition(i);
           g.addPlayer(p);
           g.setCurrentPlayersTurn("Test");


           assertThrows(NullPointerException.class, () ->{
               g.advanceAndCollect("Strandbad");
           });
       }
    }

    @Test
    public void testDoActionChance0() throws IOException {
        p.setPosition(1);
        p.setCardID(R.drawable.chance0);

        HashMap<Integer, Field> fields = new HashMap<>();
        fields.put(1,mock(Field.class));

        g.setFields(fields);
        g.addPlayer(p);
        g.setCurrentPlayersTurn("User");

        assertThrows(NullPointerException.class, () ->{
            g.doAction();
        });
    }

    @Test
    public void testDoActionCommunity1() throws IOException {
        p.setPosition(1);
        p.setCardID(R.drawable.community1);

        HashMap<Integer, Field> fields = new HashMap<>();
        fields.put(1,mock(Field.class));

        g.setFields(fields);
        g.addPlayer(p);
        g.setCurrentPlayersTurn("User");

        assertThrows(NullPointerException.class, () ->{
            g.doAction();
        });
    }




}

