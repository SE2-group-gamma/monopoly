package com.example.monopoly;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.graphics.Color;

import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.network.Client;
import com.example.monopoly.network.ClientHandler;
import com.example.monopoly.network.MonopolyServer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class ClientHandlerTest {
    ClientHandler clientHandler;
    Socket socket;
    Client client;
    InetAddress inetAddress;
    Player player;

    Socket socket2;
    Client client2;
    BufferedReader br;
    BufferedWriter bw;
    StringWriter stringWriter;
    ArrayList<String> msgBuffer;
    ClientHandler clientHandler2;
    ClientHandler clientHandler3;


    @BeforeEach
    public void setUp(){
        socket = new Socket();
        inetAddress = mock(InetAddress.class);
        Color color = new Color();
        player = new Player("user1", color,277.92,true);
        client = new Client(inetAddress,6347,player,false);
        clientHandler = new ClientHandler(socket,"host",client);

        br = mock(BufferedReader.class);
        bw = mock(BufferedWriter.class);
        stringWriter = new StringWriter();
        msgBuffer = new ArrayList<>();
        clientHandler.br = br;
        clientHandler.bw = bw;
        clientHandler.msgBuffer = msgBuffer;
    }


    @Test
    public void testSetServer() {
        MonopolyServer server = null;
        ServerSocket serverSocket1 = mock(ServerSocket.class);
        when(serverSocket1.getLocalPort()).thenReturn(4312);        //has to be set to a different value because it could interfere with other tests
        try {
            server = new MonopolyServer(5,serverSocket1);
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
    public void testWriteToClient() {
        clientHandler.writeToClient("Test Message");
        assertEquals("Test Message", clientHandler.msgBuffer.get(0));
    }

    @Test
    public void testGetClient() {
        assertEquals(socket, clientHandler.getSocket());
    }

    @Test
    public void testGetClientClient() {
        clientHandler.setClient(client);
        assertEquals(client, clientHandler.getClient());
    }

    @Test
    public void testSetClient() {
        clientHandler.setClient(client);
        assertEquals(client, clientHandler.getClient());
    }

    @Test
    public void testSetSocket() {
        ClientHandler clientHandler = new ClientHandler(null);
        clientHandler.setSocket(socket);
        assertEquals(socket, clientHandler.getSocket());
    }

    @Test
    public void testMsgBuffer() throws IOException {
        msgBuffer.add("Test|testMessage1");
        msgBuffer.add("Test|testMessage2");
        msgBuffer.add("Test|testMessage3");

        clientHandler.readMsgBuffer();
        verify(bw).write("Test|testMessage3" + System.lineSeparator());
        verify(bw).write("Test|testMessage2" + System.lineSeparator());
        verify(bw).write("Test|testMessage1" + System.lineSeparator());
        verify(bw, times(3)).flush();

        assertEquals(0, msgBuffer.size());
    }

    @Test
    public void testReplacer() throws IOException {
        String message = "Test|message|REPLACER";
        BufferedReader reader = new BufferedReader(new StringReader(message));
        clientHandler.br = reader;
        // TODO
        clientHandler.replacer();
    }


    public void testRun() throws IOException {
        socket = mock(Socket.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String message = "Test|message|REPLACER";

        when(socket.getInputStream()).thenReturn(mock(InputStream.class));
        when(socket.getOutputStream()).thenReturn(outputStream);

        clientHandler.setSocket(socket);
        clientHandler.run();

        /*String expectedOutput = "Test|message|localhost";
        String result = outputStream.toString().trim();
        assertNotEquals(expectedOutput, result);*/
    }


}