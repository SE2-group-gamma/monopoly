package com.example.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.example.monopoly.gamelogic.ServerLoop;
import com.example.monopoly.network.ClientHandler;
import com.example.monopoly.network.MonopolyServer;
import com.example.monopoly.network.TurnManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

class TurnManagerTest {

    private MonopolyServer server;
    private List<ClientHandler> clients;
    private ServerLoop serverLoop;
    private TurnManager turnManager;

    @BeforeEach
    void setUp() {
        server = mock(MonopolyServer.class);
        clients = new ArrayList<>();
        serverLoop = mock(ServerLoop.class);
        turnManager = new TurnManager(server, clients, serverLoop);
    }

    @Test
    void testAddClient() {
        ClientHandler client1 = mock(ClientHandler.class);
        turnManager.addClient(client1);
        assertEquals(1, turnManager.getClients().size());
        assertEquals(client1, turnManager.getClients().get(0));
    }

    @Test
    void testRemoveClient() {
        ClientHandler client1 = mock(ClientHandler.class);
        turnManager.addClient(client1);
        turnManager.removeClient(client1);
        assertEquals(0, turnManager.getClients().size());
    }

    @Test
    void testHandleMessage() {
        ClientHandler client1 = mock(ClientHandler.class);
        ClientHandler client2 = mock(ClientHandler.class);
        clients.add(client1);
        clients.add(client2);
        String message = "test message";
        turnManager.handleMessage(client1, message);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(serverLoop).handleMessage(messageCaptor.capture());
        assertEquals(message, messageCaptor.getValue());
        assertEquals(1, turnManager.getCurrentPlayerIndex());
        turnManager.handleMessage(client2, message);
        verify(serverLoop, times(2)).handleMessage(messageCaptor.capture());
        assertEquals(message, messageCaptor.getValue());
        assertEquals(0, turnManager.getCurrentPlayerIndex());
    }

}
