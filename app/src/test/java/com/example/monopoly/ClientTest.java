package com.example.monopoly;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import android.graphics.Color;

import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.network.Client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class ClientTest {

    private String request = "request";
    private String response = "response";
    Client client;
    Client client2;
    byte IPAddress[] = { 127, 0, 0, 1 };
    private InetAddress host;
    private Player player;

    @Mock
    Client mockClient;

    @Mock
    Player mockPlayer;


    Client client3;


    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
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
        assertEquals(msg,client.msgBuffer.get(0));
    }

    @Test
    public void chance3() throws Exception {
        player.setCardID(R.drawable.chance3);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|giveMoney|150|user1");
    }

    @Test
    public void chance5() throws Exception {
        player.setCardID(R.drawable.chance5);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|giveMoney|50|user1");
    }

    @Test
    public void chance6() throws Exception {
        player.setCardID(R.drawable.chance6);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|outOfJailCounter|1|user1");
    }

    @Test
    public void chance12() throws Exception {
        player.setCardID(R.drawable.chance12);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|outOfJailCounter|1|user1");
    }

    @Test
    public void chance14() throws Exception {
        player.setCardID(R.drawable.chance14);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|transferToBank|-15|user1");
    }

    @Test
    public void chance17() throws Exception {
        player.setCardID(R.drawable.chance17);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|giveMoney|100|user1");
    }

    @Test
    public void community1() throws Exception {
        player.setCardID(R.drawable.community1);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|giveMoney|200|user1");
    }

    @Test
    public void community2() throws Exception {
        player.setCardID(R.drawable.community2);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|transferToBank|-50|user1");
    }

    @Test
    public void community3() throws Exception {
        player.setCardID(R.drawable.community3);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|giveMoney|50|user1");
    }

    @Test
    public void community4() throws Exception {
        player.setCardID(R.drawable.community4);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|outOfJailCounter|1|user1");
    }

    @Test
    public void community6() throws Exception {
        player.setCardID(R.drawable.community6);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|giveMoney|100|user1");
    }

    @Test
    public void community7() throws Exception {
        player.setCardID(R.drawable.community7);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|giveMoney|20|user1");
    }

    @Test
    public void community9() throws Exception {
        player.setCardID(R.drawable.community9);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|giveMoney|100|user1");
    }

    @Test
    public void community10() throws Exception {
        player.setCardID(R.drawable.community10);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|transferToBank|-100|user1");
    }

    @Test
    public void community11() throws Exception {
        player.setCardID(R.drawable.community11);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|transferToBank|-50|user1");
    }

    @Test
    public void community12() throws Exception {
        player.setCardID(R.drawable.community12);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|giveMoney|25|user1");
    }

    @Test
    public void community14() throws Exception {
        player.setCardID(R.drawable.community14);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|transferToBank|-15|user1");
    }

    @Test
    public void community15() throws Exception {
        player.setCardID(R.drawable.community15);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|giveMoney|10|user1");
    }

    @Test
    public void community16() throws Exception {
        player.setCardID(R.drawable.community16);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|giveMoney|100|user1");
    }

    @Test
    public void community17() throws Exception {
        player.setCardID(R.drawable.community17);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|giveMoney|50|user1");
    }

    @Test
    public void community18() throws Exception {
        player.setCardID(R.drawable.community18);
        client.setUser(player);
        Client clientSpy = spy(client);
        clientSpy.doAction();
        verify(clientSpy).writeToServer("GameBoardUI|giveMoney|25|user1");
    }


}
