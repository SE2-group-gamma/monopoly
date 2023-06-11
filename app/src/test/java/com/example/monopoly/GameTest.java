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
import org.junit.jupiter.api.BeforeAll;
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

        p.setPosition(0);
        g.addPlayer(p);
        g.setCurrentPlayersTurn("Test");

        assertThrows(NullPointerException.class, () -> {
            g.advanceAndCollect("Strandbad");
        });

    }

    @Test
    public void chance0() throws IOException {
        g.getPlayers().get(0).setCardID(R.drawable.chance0);
        HashMap<Integer, Field> fields = new HashMap<>();
        fields.put(1,mock(Field.class));

        assertThrows(NullPointerException.class, () ->{
            g.doAction();
        });
    }

    @Test
    public void community0() throws IOException {
        g.getPlayers().get(0).setCardID(R.drawable.community0);
        HashMap<Integer, Field> fields = new HashMap<>();
        fields.put(1,mock(Field.class));

        assertThrows(NullPointerException.class, () ->{
            g.doAction();
        });
    }

    @Test
    public void chance1() {
        g.getPlayers().get(0).setCardID(R.drawable.chance1);
        g.setCurrentPlayersTurn("Test");
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance2() {
        g.getPlayers().get(0).setCardID(R.drawable.chance2);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance3() {
        g.getPlayers().get(0).setCardID(R.drawable.chance3);
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

        g.getPlayers().get(0).setCardID(R.drawable.chance4);

        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance4_2() {
        Field field1 = new Field(0, "S-Bahn Kärnten", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(0, field1);
        Field field2 = new Field(1, "S-Bahn Wien", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(1, field2);
        Field field3 = new Field(2, "S-Bahn Tirol", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(2, field3);
        Field field4 = new Field(3, "S-Bahn Steiermark", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(3, field4);

        g.setFields(fields);

        g.getPlayers().get(0).setCardID(R.drawable.chance4);
        g.getPlayers().get(0).setPosition(1);

        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance4_3() {
        Field field1 = new Field(0, "S-Bahn Kärnten", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(0, field1);
        Field field2 = new Field(1, "S-Bahn Wien", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(1, field2);
        Field field3 = new Field(2, "S-Bahn Tirol", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(2, field3);
        Field field4 = new Field(3, "S-Bahn Steiermark", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(3, field4);

        g.setFields(fields);

        g.getPlayers().get(0).setCardID(R.drawable.chance4);
        g.getPlayers().get(0).setPosition(2);

        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance4_4() {
        Field field1 = new Field(0, "S-Bahn Kärnten", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(0, field1);
        Field field2 = new Field(1, "S-Bahn Wien", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(1, field2);
        Field field3 = new Field(2, "S-Bahn Tirol", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(2, field3);
        Field field4 = new Field(3, "S-Bahn Steiermark", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(3, field4);

        g.setFields(fields);

        g.getPlayers().get(0).setCardID(R.drawable.chance4);
        g.getPlayers().get(0).setPosition(3);

        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance5() {
        g.getPlayers().get(0).setCardID(R.drawable.chance5);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance6() {
        g.getPlayers().get(0).setCardID(R.drawable.chance6);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance7() {
        g.getPlayers().get(0).setCardID(R.drawable.chance7);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance9() {
        Field field1 = new Field(0, "S-Bahn Wien", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(0, field1);
        g.setFields(fields);

        g.getPlayers().get(0).setCardID(R.drawable.chance9);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void chance10() {
        g.getPlayers().get(0).setCardID(R.drawable.chance10);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance11() {
        g.getPlayers().get(0).setCardID(R.drawable.chance11);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance13() {
        Field field1 = new Field(0, "Jail", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(0, field1);
        g.setFields(fields);

        g.getPlayers().get(0).setCardID(R.drawable.chance13);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance14() {
        g.getPlayers().get(0).setCardID(R.drawable.chance14);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance15() {
        g.getPlayers().get(0).setCardID(R.drawable.chance15);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void chance17() {
        g.getPlayers().get(0).setCardID(R.drawable.chance17);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void chance18() {
        g.getPlayers().get(0).setCardID(R.drawable.chance18);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void chance19() {
        g.getPlayers().get(0).setCardID(R.drawable.chance19);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void chance12() {
        g.getPlayers().get(0).setCardID(R.drawable.chance12);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void community4() {
        g.getPlayers().get(0).setCardID(R.drawable.community4);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void chance16() {
        g.getPlayers().get(0).setCardID(R.drawable.chance16);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void community5() {
        g.getPlayers().get(0).setCardID(R.drawable.community5);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void testcommunity14() {
        g.getPlayers().get(0).setCardID(R.drawable.community14);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void community1() {
        g.getPlayers().get(0).setCardID(R.drawable.community1);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void community2() {
        g.getPlayers().get(0).setCardID(R.drawable.community2);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void community3() {
        g.getPlayers().get(0).setCardID(R.drawable.community3);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void community6() {
        g.getPlayers().get(0).setCardID(R.drawable.community6);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void community7() {
        g.getPlayers().get(0).setCardID(R.drawable.community7);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void community8() {
        g.getPlayers().get(0).setCardID(R.drawable.community8);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void community9() {
        g.getPlayers().get(0).setCardID(R.drawable.community9);

        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void testcommunity10() {
        g.getPlayers().get(0).setCardID(R.drawable.community10);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void testcommunity11() {
        g.getPlayers().get(0).setCardID(R.drawable.community11);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void testcommunity12() {
        g.getPlayers().get(0).setCardID(R.drawable.community12);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void testcommunity15() {
        g.getPlayers().get(0).setCardID(R.drawable.community15);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void testcommunity16() {
        g.getPlayers().get(0).setCardID(R.drawable.community16);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void testcommunity17() {
        g.getPlayers().get(0).setCardID(R.drawable.community17);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void testcommunity18() {
        g.getPlayers().get(0).setCardID(R.drawable.community18);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }
    @Test
    public void testcommunity19() {
        g.getPlayers().get(0).setCardID(R.drawable.community19);
        assertThrows(NullPointerException.class, () -> {
            g.doAction();
        });
    }

    @Test
    public void testAdvanceAndCollect2() throws IOException {
        field = new Field(0, "Strandbad", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(0, field);
        g.setFields(fields);
        g.getPlayers().get(0).setPosition(2);

        assertThrows(NullPointerException.class, () -> {
            g.advanceAndCollect("Strandbad");
        });
    }

    @Test
    public void testAdvanceAndCollect3() throws IOException {
        field = new Field(4, "Strandbad", "x", mock(Color.class), 200, 2, mock(Player.class), 20, 500, 500, mock(Image.class));
        fields.put(0, field);
        g.setFields(fields);
        g.getPlayers().get(0).setPosition(2);

        assertThrows(NullPointerException.class, () -> {
            g.advanceAndCollect("Strandbad");
        });

    }
}


