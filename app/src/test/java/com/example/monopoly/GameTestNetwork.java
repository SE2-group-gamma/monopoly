package com.example.monopoly;


import com.example.monopoly.gamelogic.Field;
import com.example.monopoly.gamelogic.Game;
import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.network.Client;
import com.example.monopoly.network.MonopolyServer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;

import android.graphics.Color;

import java.io.IOException;
import java.util.HashMap;

public class GameTestNetwork {

    Game g;
    Player p;
    Field field;
    HashMap<Integer, Field> fields;

    @Mock
    MonopolyServer mockServer;
    @Mock
    Client mockClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        g = Game.getInstance();
        p = new Player("Test", new Color(), 100.00, true);
        fields = new HashMap<>();
        g.addPlayer(p);
        g.setCurrentPlayersTurn("Test");
        g.getPlayers().get(0).setMyClient(mockClient);
    }

    @Test
    public void transferPlayer() throws IOException {
        //g.getPlayers().get(0).setMyClient(mockClient);

        g.transferToPlayerProtocol(10);
        verify(mockClient).writeToServer("GameBoardUI|transferToPlayer|10|Test");
    }

    @Test
    public void transferToBank() throws IOException {
        g.transferToBankProtocol(10);
        verify(mockClient).writeToServer("GameBoardUI|transferToBank|10|Test");
    }
   @Test
    public void testMove() throws IOException {
        g.moveProtocol(1);
        verify(mockClient).writeToServer("GameBoardUI|move|1|Test");
    }

    @Test
    public void outOfJail() throws IOException {
        g.outOfJailCounterProtocol(1);
        verify(mockClient).writeToServer("GameBoardUI|outOfJailCounter|1|Test");
    }

    @Test
    public void endTurn() throws IOException {
        g.endTurnProtocol();
        verify(mockClient).writeToServer("GameBoardUI|endTurn|Test");
    }
    @Test
    public void transferPlayerToPlayer() throws IOException {
        Player player = new Player ("Test1", new Color(), 100.00, true);
        player.setId(1);
        g.addPlayer(player);
        g.transferPlayerToPlayerProtocol(1,10);
        verify(mockClient).writeToServer("GameBoardUI|transferPlayerToPlayer|1:10|Test");
    }
}
