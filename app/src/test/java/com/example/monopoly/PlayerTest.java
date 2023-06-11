package com.example.monopoly;

import org.junit.jupiter.api.Test;

import android.graphics.Color;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.mock;

import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.gamelogic.PlayerMapPosition;
import com.example.monopoly.network.Client;

import java.net.InetAddress;
import java.net.UnknownHostException;

class PlayerTest {
    static Player player,player_two;
    static Color col;

    @BeforeEach
    void setup(){
        col = mock(Color.class);
        player = new Player("Hans",col,100.00,true);
        player_two = new Player("Fritz",col,100.00,true);
    }
    @Test
    void testSetGetId() {
        player.setId(1);
        assertEquals(1,player.getId());
        player_two.setId(2);
        assertEquals(2,player_two.getId());
    }

    @Test
    void testSetGetMyClient() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName("192.168.0.1");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        Client client = new Client(inetAddress,8755,true);
        player.setMyClient(client);
        assertEquals(client,player.getMyClient());
    }

    @Test
    void testPosition() {
        player.setPosition(5);
        assertEquals(5,player.getPosition());
        player.incrementPosition(3);
        assertEquals(8,player.getPosition());
    }


    @Test
    void setGetCapital() {
        assertEquals(player.getCapital(),100.00);
        player.setCapital(120.00);
        assertEquals(player.getCapital(),120.00);
    }

    @Test
    void setGetAlive() {
        assertEquals(player.isAlive(),true);
        player.setAlive(false);
        assertEquals(player.isAlive(),false);
    }

    @Test
    void setGetInPrison() {
        assertEquals(player.isInPrison(),false);
        player.setInPrison(true);
        assertEquals(player.isInPrison(),true);
    }

    @Test
    void getUsername() {
        assertEquals(player.getUsername(),"Hans");
    }

    @Test
    void getCol() {
        assertEquals(player.getCol(),col);
    }

    @Test
    public void transferMoneyPlayerToPlayer(){
        player.transferMoneyPlayerToPlayer(player,player_two,20);
        //player_two increased by 200
        assertEquals(120, player_two.getCapital(), 0.0);
        //player decreased by $200
        assertEquals(80, player.getCapital(), 0.0);
    }
    @Test
    void testPlayerMapPosition(){
        PlayerMapPosition expectedPosition1 = new PlayerMapPosition(5, 5,1);
        player.setPlayerMapPosition(expectedPosition1);
        PlayerMapPosition actualPosition1 = player.getPlayerMapPosition();
        assertEquals(expectedPosition1, actualPosition1);

        PlayerMapPosition expectedPosition2 = new PlayerMapPosition(7, 7,1);
        player.setPlayerMapPosition(expectedPosition2);
        PlayerMapPosition actualPosition2 = player.getPlayerMapPosition();
        assertEquals(expectedPosition2, actualPosition2);
    }


}