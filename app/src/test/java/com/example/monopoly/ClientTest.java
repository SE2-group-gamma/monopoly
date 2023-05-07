package com.example.monopoly;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


import android.graphics.Color;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.network.Client;
import com.example.monopoly.ui.UIHandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


public class ClientTest {

    private String request = "request";
    private String response = "response";
    Client client;
    Client client2;
    byte IPAddress[] = { 127, 0, 0, 1 };
    private InetAddress host;
    private Player player;

    @Mock
    Player mockPlayer;

    Client client3;


    @BeforeEach
    public void setUp(){
        host = mock(InetAddress.class);
        Color color = new Color();
        player = new Player("user1", color,277.92,true);
        client = new Client(host,0,null,false);
        client2 = new Client(host,0,false);

        InetAddress localhost = null;
        try {
            localhost = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        client3 = new Client(localhost, 6969, mockPlayer, false);
    }

    @Test
    public void testConnectionRefused() throws IOException {
        InetAddress address = InetAddress.getByAddress(IPAddress);
        client = new Client(address,0,null,false);

        assertThrows(RuntimeException.class, () -> {
            client.run();
        });

    }

    @Test
    public void testClientGettersSetters() throws UnknownHostException {
        InetAddress address = InetAddress.getByAddress(IPAddress);
        client = new Client(address,0, null,false);
        client.setResponse(response);
        client.setRequest(request);
        assertEquals(client.getResponse(),response);
        assertEquals(client.getRequest(),request);
    }

    /*
    TO-DO:

    testClientConnectionEstablished()
    testClientDiscovery()

     */

    @Test
    public void testGetHost() {
        assertEquals(host, client.getHost());
    }

    @Test
    public void testSetAndGetUser() {
        client.setUser(player);
        assertEquals(player, client.getUser());
    }

    @Test
    public void testGetRequest() {
        assertEquals(null, client.getRequest());
    }

    @Test
    public void testSetResponse() {
        client.setResponse("New Response");
        assertEquals("New Response", client.getResponse());
    }

    @Test
    public void testIsHost() {
        assertEquals(false, client.isHost());
    }

    @Test
    public void testSetHost() {
        client.setHost(false);
        assertEquals(false, client.isHost());
    }

    @Test
    public void testSetAndGetKey() {
        client.setKey(5678);
        assertEquals(5678, client.getKey());
    }

    @Test
    public void testWriteToServer() throws IOException {
        String message = "Test Message";
        client.writeToServer(message);
        assertEquals(message, client.msgBuffer.get(0));
    }

    @Test
    public void testSubscribe() {
        assertNull(Client.handlers.get("test"));
    }


    @Test
    public void writeToServer_whenCalled_shouldAddMessageToBuffer() throws IOException {
        String msg = "START_GAME";
        client.writeToServer(msg);
        assert(client.msgBuffer.get(0).equals(msg));
    }




}
