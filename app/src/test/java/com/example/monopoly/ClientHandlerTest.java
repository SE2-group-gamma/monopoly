package com.example.monopoly;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import android.graphics.Color;

import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.network.Client;
import com.example.monopoly.network.ClientHandler;
import com.example.monopoly.network.MonopolyServer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

class ClientHandlerTest {


    ClientHandler clientHandler;
    Socket socket;
    Client client;
    InetAddress inetAddress;
    Player player;

    @BeforeEach
    public void setUp(){
        socket = new Socket();
        inetAddress = mock(InetAddress.class);
        Color color = new Color();
        player = new Player("user1", color,277.92,true);
        client = new Client(inetAddress,6347,player,false);
        clientHandler = new ClientHandler(socket,"host",client);
    }


    @Test
    public void testSetServer() {
        MonopolyServer server = null;
        try {
            server = new MonopolyServer(5);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ClientHandler handler = new ClientHandler(null);
        handler.setServer(server);
        assertEquals(server, handler.server);
    }

    @Test
    public void testSetServerWithNull() {
        ClientHandler handler = new ClientHandler(null);
        handler.setServer(null);
        assertEquals(null, handler.server);
    }

    @Test
    void setSocket() {


    }

    @Test
    void setClient() {
    }

    @Test
    void getClient() {
    }

    @Test
    void getClientClient() {
    }

    @Test
    void run() {
    }

    @Test
    void writeToClient() {
    }
}