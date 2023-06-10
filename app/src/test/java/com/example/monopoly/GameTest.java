package com.example.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.graphics.Color;

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
    byte IPAddress[] = { 127, 0, 0, 1 };

    @Mock
    private HashMap<Integer,Player> mockPlayers;
    @Mock
    private HashMap<Integer,Field> mockFields;
    @Mock
    private MonopolyServer mockServer;
    @Mock
    private Client mockClient;
    @Mock
    private Player mockPlayer;
    @Mock
    private Game mockGame;
    @Mock
    private Field mockField;


   @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        g = Game.getInstance();
        p = new Player("Test",new Color(),100.00,true);

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

