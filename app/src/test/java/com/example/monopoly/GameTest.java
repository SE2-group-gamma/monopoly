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
    Player player;

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
        player = new Player("User",new Color(),100.00,true);
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

    @Test
    public void chance11() {
        Player p11 = new Player("User11",new Color(),100.00,true);
        p11.setCardID(R.drawable.chance11);

        g.addPlayer(p11);
        g.setCurrentPlayersTurn("User11");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance13() {
        Field field1 = new Field(0, "Jail", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(0, field1);
        g.setFields(fields);

        Player p12 = new Player("User12",new Color(),100.00,true);
        p12.setCardID(R.drawable.chance13);

        g.addPlayer(p12);
        g.setCurrentPlayersTurn("User12");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance14() {
        Player p13 = new Player("User13", new Color(), 100.00, true);
        p13.setCardID(R.drawable.chance14);

        g.addPlayer(p13);
        g.setCurrentPlayersTurn("User13");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance15() {
        Player p14 = new Player("User14", new Color(), 100.00, true);
        p14.setCardID(R.drawable.chance15);

        g.addPlayer(p14);
        g.setCurrentPlayersTurn("User14");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void chance17() {
        Player p15 = new Player("User15", new Color(), 100.00, true);
        p15.setCardID(R.drawable.chance17);

        g.addPlayer(p15);
        g.setCurrentPlayersTurn("User15");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance18() {
        Player p16 = new Player("User16", new Color(), 100.00, true);
        p16.setCardID(R.drawable.chance18);

        g.addPlayer(p16);
        g.setCurrentPlayersTurn("User16");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void chance19() {
        Player p17 = new Player("User17", new Color(), 100.00, true);
        p17.setCardID(R.drawable.chance19);

        g.addPlayer(p17);
        g.setCurrentPlayersTurn("User17");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void chance12() {
        Player p18 = new Player("User18", new Color(), 100.00, true);
        p18.setCardID(R.drawable.chance12);

        g.addPlayer(p18);
        g.setCurrentPlayersTurn("User18");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void community4() {
        Player p19 = new Player("User19", new Color(), 100.00, true);
        p19.setCardID(R.drawable.community4);

        g.addPlayer(p19);
        g.setCurrentPlayersTurn("User19");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void chance16() {
        Player p20 = new Player("User20", new Color(), 100.00, true);
        p20.setCardID(R.drawable.chance16);

        g.addPlayer(p20);
        g.setCurrentPlayersTurn("User20");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void community5() {
        Player p21 = new Player("User21", new Color(), 100.00, true);
        p21.setCardID(R.drawable.community5);

        g.addPlayer(p21);
        g.setCurrentPlayersTurn("User21");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void testcommunity14() {
        Player p22 = new Player("User22", new Color(), 100.00, true);
        p22.setCardID(R.drawable.community14);

        g.addPlayer(p22);
        g.setCurrentPlayersTurn("User22");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void community1() {
        Player p23 = new Player("User23", new Color(), 100.00, true);
        p23.setCardID(R.drawable.community1);

        g.addPlayer(p23);
        g.setCurrentPlayersTurn("User23");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void community2() {
        Player p24 = new Player("User24", new Color(), 100.00, true);
        p24.setCardID(R.drawable.community2);

        g.addPlayer(p24);
        g.setCurrentPlayersTurn("User24");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void community3() {
        Player p25 = new Player("User25", new Color(), 100.00, true);
        p25.setCardID(R.drawable.community3);

        g.addPlayer(p25);
        g.setCurrentPlayersTurn("User25");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void community6() {
        Player p26 = new Player("User26", new Color(), 100.00, true);
        p26.setCardID(R.drawable.community6);

        g.addPlayer(p26);
        g.setCurrentPlayersTurn("User26");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void community7() {
        Player p27 = new Player("User27", new Color(), 100.00, true);
        p27.setCardID(R.drawable.community7);

        g.addPlayer(p27);
        g.setCurrentPlayersTurn("User27");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void community8() {
        Player p28 = new Player("User28", new Color(), 100.00, true);
        p28.setCardID(R.drawable.community8);

        g.addPlayer(p28);
        g.setCurrentPlayersTurn("User28");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void community9() {
        Player p29 = new Player("User29", new Color(), 100.00, true);
        p29.setCardID(R.drawable.community9);

        g.addPlayer(p29);
        g.setCurrentPlayersTurn("User29");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void testcommunity10() {
        Player p30 = new Player("User30", new Color(), 100.00, true);
        p30.setCardID(R.drawable.community10);

        g.addPlayer(p30);
        g.setCurrentPlayersTurn("User30");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }


}

