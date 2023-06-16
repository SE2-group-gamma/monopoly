package com.example.monopoly;


import static org.mockito.Mockito.verify;

import android.graphics.Color;

import com.example.monopoly.gamelogic.Field;
import com.example.monopoly.gamelogic.Game;
import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.network.Client;
import com.example.monopoly.network.MonopolyServer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    public void endTurn() throws IOException, IOException {
        g.doAction(R.drawable.community8, mockClient);
        g.endTurnProtocol();
        verify(mockClient).writeToServer("GameBoardUI|turnEnd|:|");
    }

    @Test
    public void transferToBank() throws IOException {
        g.doAction(R.drawable.community11, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|transferToBank|-50|Test");
    }


    @Test
    public void outOfJail() throws IOException {
        g.doAction(R.drawable.chance6, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|outOfJailCounter|1|Test");
    }

    @Test
    public void chance3() throws IOException {
        g.doAction(R.drawable.chance3, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|giveMoney|150|Test");
    }

    @Test
    public void chance5() throws IOException {
        g.doAction(R.drawable.chance5, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|giveMoney|50|Test");
    }

    @Test
    public void chance6() throws IOException {
        g.doAction(R.drawable.chance6, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|outOfJailCounter|1|Test");
    }

    @Test
    public void chance12() throws IOException {
        g.doAction(R.drawable.chance12, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|outOfJailCounter|1|Test");
    }

    @Test
    public void chance14() throws IOException {
        g.doAction(R.drawable.chance14, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|transferToBank|-15|Test");
    }

    @Test
    public void chance17() throws IOException {
        g.doAction(R.drawable.chance17, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|giveMoney|100|Test");
    }

    @Test
    public void community1() throws IOException {
        g.doAction(R.drawable.community1, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|giveMoney|200|Test");
    }

    @Test
    public void community2() throws IOException {
        g.doAction(R.drawable.community2, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|transferToBank|-50|Test");
    }

    @Test
    public void community3() throws IOException {
        g.doAction(R.drawable.community3, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|giveMoney|50|Test");
    }

    @Test
    public void community4() throws IOException {
        g.doAction(R.drawable.community4, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|outOfJailCounter|1|Test");
    }

    @Test
    public void community6() throws IOException {
        g.doAction(R.drawable.community6, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|giveMoney|100|Test");
    }

    @Test
    public void community7() throws IOException {
        g.doAction(R.drawable.community7, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|giveMoney|20|Test");
    }

    @Test
    public void community9() throws IOException {
        g.doAction(R.drawable.community9, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|giveMoney|100|Test");
    }

    @Test
    public void community10() throws IOException {
        g.doAction(R.drawable.community10, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|transferToBank|-100|Test");
    }

    @Test
    public void community11() throws IOException {
        g.doAction(R.drawable.community11, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|transferToBank|-50|Test");
    }

    @Test
    public void community12() throws IOException {
        g.doAction(R.drawable.community12, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|giveMoney|25|Test");
    }

    @Test
    public void community14() throws IOException {
        g.doAction(R.drawable.community14, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|transferToBank|-15|Test");
    }

    @Test
    public void community15() throws IOException {
        g.doAction(R.drawable.community15, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|giveMoney|10|Test");
    }

    @Test
    public void community16() throws IOException {
        g.doAction(R.drawable.community16, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|giveMoney|100|Test");
    }

    @Test
    public void community17() throws IOException {
        g.doAction(R.drawable.community17, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|giveMoney|50|Test");
    }

    @Test
    public void community18() throws IOException {
        g.doAction(R.drawable.community18, mockClient);
        verify(mockClient).writeToServer("GameBoardUI|giveMoney|25|Test");
    }













}
