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
    public void testAdvanceAndCollect() throws IOException {

        field = new Field(0, "Strandbad", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(0, field);

        g.setFields(fields);

       for(int i =0; i<3; i++) {

           p.setPosition(i);
           g.addPlayer(p);
           g.setCurrentPlayersTurn("Test");


           assertThrows(NullPointerException.class, () ->{
               g.advanceAndCollect("Strandbad");
           });
       }
    }

    @Test
    public void chance0() throws IOException {
        Player p0 = new Player("User",new Color(),100.00,true);
        p0.setPosition(g.getPlayers().size()+1);
        p0.setCardID(R.drawable.chance0);


        HashMap<Integer, Field> fields = new HashMap<>();
        fields.put(1,mock(Field.class));

        g.addPlayer(p0);
        g.setCurrentPlayersTurn("User");

        assertThrows(NullPointerException.class, () ->{
            g.doAction();
        });
    }

    @Test
    public void community0() throws IOException {

        Player p1 = new Player("User1",new Color(),100.00,true);
        p1.setPosition(g.getPlayers().size()+1);
        p1.setCardID(R.drawable.chance0);

        HashMap<Integer, Field> fields = new HashMap<>();
        fields.put(1,mock(Field.class));

        g.addPlayer(p1);
        g.setCurrentPlayersTurn("User1");

        assertThrows(NullPointerException.class, () ->{
            g.doAction();
        });
    }

    @Test
    public void chance1() {
        Player p2 = new Player("User2",new Color(),100.00,true);
        p2.setCardID(R.drawable.chance1);

        g.addPlayer(p2);
        g.setCurrentPlayersTurn("User2");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance2() {
        Player p3 = new Player("User3",new Color(),100.00,true);
        p3.setCardID(R.drawable.chance2);

        g.addPlayer(p3);
        g.setCurrentPlayersTurn("User3");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance3() {
        Player p4 = new Player("User4",new Color(),100.00,true);
        p4.setCardID(R.drawable.chance3);

        g.addPlayer(p4);
        g.setCurrentPlayersTurn("User4");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void chance4() {
        Field field1 = new Field(0, "S-Bahn Kärnten", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(0, field1);
        Field field2 = new Field(1, "S-Bahn Wien", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(1, field2);
        Field field3 = new Field(2, "S-Bahn Tirol", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(2, field3);
        Field field4 = new Field(3, "S-Bahn Steiermark", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(3, field4);

        g.setFields(fields);

        System.out.println(fields.size());

        Player p5 = new Player("User5",new Color(),100.00,true);
        p5.setCardID(R.drawable.chance4);
        p5.setPosition(14);

        g.addPlayer(p5);
        g.setCurrentPlayersTurn("User5");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance5() {
        Player p6 = new Player("User6",new Color(),100.00,true);
        p6.setCardID(R.drawable.chance5);

        g.addPlayer(p6);
        g.setCurrentPlayersTurn("User6");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance6() {
        Player p7 = new Player("User7",new Color(),100.00,true);
        p7.setCardID(R.drawable.chance6);

        g.addPlayer(p7);
        g.setCurrentPlayersTurn("User7");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance7() {
        Player p8 = new Player("User8",new Color(),100.00,true);
        p8.setCardID(R.drawable.chance7);

        g.addPlayer(p8);
        g.setCurrentPlayersTurn("User8");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance9() {
        Field field1 = new Field(0, "S-Bahn Wien", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(0, field1);
        g.setFields(fields);

        Player p9 = new Player("User9",new Color(),100.00,true);
        p9.setCardID(R.drawable.chance9);

        g.addPlayer(p9);
        g.setCurrentPlayersTurn("User9");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void chance10() {
        Player p10 = new Player("User10",new Color(),100.00,true);
        p10.setCardID(R.drawable.chance10);

        g.addPlayer(p10);
        g.setCurrentPlayersTurn("User10");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

}

