package com.example.monopoly;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.example.monopoly.network.MonopolyServer;

import org.junit.Test;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonopolyServerTest {

    private MonopolyServer createServer(int maxNumOfClients){
        MonopolyServer ms = null;
        try {
            ms = new MonopolyServer(maxNumOfClients);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Create MonopolyServer failed!");
        }
        return ms;
    }

    @Test
    public void singleClientConnectionTest() throws IOException {
        MonopolyServer ms = createServer(1);
        assertNotNull(ms);
        ms.start();
        int port = ms.getLocalPort();
        Socket sc = new Socket("127.0.0.1", port);
        while (ms.isListening());
        assertTrue(sc.isConnected());
        assertEquals(1, ms.getNumberOfClients());
        sc.close();
    }

    @Test
    public void multipleConnectionTest() throws IOException {
        MonopolyServer ms = null;
        Random rand = new Random();

        int numOfClients = rand.nextInt(10) + 1;
        ms = createServer(numOfClients);
        assertNotNull(ms);
        ms.start();
        int port = ms.getLocalPort();
        List<Socket> clients = new ArrayList<>();
        for(int i = 0; i < numOfClients; i++){
            clients.add(new Socket("127.0.0.1", port));
        }
        while (ms.isListening());
        for(Socket sc : clients){
            assertTrue(sc.isConnected());
        }
        assertEquals(numOfClients, ms.getNumberOfClients());
        for(Socket sc : clients){
            sc.close();
        }
    }

    @Test
    public void tooManyConnectionsTest() throws IOException {
        MonopolyServer ms = createServer(1);
        ms.start();
        int port = ms.getLocalPort();
        Socket s1 = new Socket("127.0.0.1", port);
        assertThrows(ConnectException.class, () -> new Socket("127.0.0.1", port));
        while(ms.isListening());
        assertEquals(1, ms.getNumberOfClients());
        assertTrue(s1.isConnected());
        s1.close();
    }
}
