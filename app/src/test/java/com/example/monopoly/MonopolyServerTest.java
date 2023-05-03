package com.example.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.monopoly.network.Client;
import com.example.monopoly.network.ClientHandler;
import com.example.monopoly.network.MonopolyServer;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonopolyServerTest {
    ServerSocket serverSocket;
    Socket socket;


    private MonopolyServer initServer(int maxNumOfClients){
        MonopolyServer ms = null;
        serverSocket = mock(ServerSocket.class);
        socket = mock(Socket.class);
        when(serverSocket.getLocalPort()).thenReturn(1234);
        try {
            when(serverSocket.accept()).thenReturn(socket);
            when(socket.getInputStream()).thenReturn(mock(InputStream.class));
            when(socket.getOutputStream()).thenReturn(mock(OutputStream.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            ms = new MonopolyServer(maxNumOfClients, serverSocket);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Create MonopolyServer failed!");
        }
        return ms;
    }

    @Test
    public void createMonopolyServerTest(){
        MonopolyServer ms = null;
        try {
            ms = new MonopolyServer(10);
        } catch (IOException e) {
            fail();
            e.printStackTrace();
        }
        assertNotNull(ms);
        assertTrue(ms.getLocalPort() != 0);
    }

    @Test
    public void singleClientConnectionTest() throws IOException {
        MonopolyServer ms = initServer(1);
        assertNotNull(ms);
        ms.start();
        int port = ms.getLocalPort();
        while (ms.isAlive());
        verify(serverSocket, times(1)).accept();
        assertEquals(1, ms.getNumberOfClients());
    }

    @Test
    public void multipleConnectionTest() throws IOException {
        MonopolyServer ms = null;
        Random rand = new Random();

        int numOfClients = rand.nextInt(10) + 1;
        ms = initServer(numOfClients);
        assertNotNull(ms);
        ms.start();
        int port = ms.getLocalPort();
        while (ms.isAlive());
        verify(serverSocket,times(numOfClients)).accept();
        assertEquals(numOfClients, ms.getNumberOfClients());

    }

}
