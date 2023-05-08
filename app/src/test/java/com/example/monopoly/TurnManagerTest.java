package com.example.monopoly;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.monopoly.network.Client;
import com.example.monopoly.network.ClientHandler;
import com.example.monopoly.network.TurnManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

class TurnManagerTest {

    private TurnManager turnManager;
    private List<ClientHandler> clientHandlers;

    @BeforeEach
    void setUp() throws IOException {
        clientHandlers = new ArrayList<>();
        turnManager = new TurnManager(clientHandlers);

        for (int i = 0; i < 3; i++) {
            Socket mockSocket = Mockito.mock(Socket.class);
            ByteArrayInputStream mockInputStream = new ByteArrayInputStream("".getBytes());
            Mockito.when(mockSocket.getInputStream()).thenReturn(mockInputStream);

            ByteArrayOutputStream mockOutputStream = new ByteArrayOutputStream();
            Mockito.when(mockSocket.getOutputStream()).thenReturn(mockOutputStream);

            clientHandlers.add(new ClientHandler(mockSocket, turnManager));
        }
    }

    @Test
    void testStart() {
        assertFalse(clientHandlers.get(0).isTurn());
        assertFalse(clientHandlers.get(1).isTurn());
        assertFalse(clientHandlers.get(2).isTurn());

        turnManager.start();

        assertTrue(clientHandlers.get(0).isTurn());
        assertFalse(clientHandlers.get(1).isTurn());
        assertFalse(clientHandlers.get(2).isTurn());
    }

    @Test
    void testHandleClientHandlerTurn() throws IOException, InterruptedException {
        PipedInputStream clientInput = new PipedInputStream();
        PipedOutputStream clientOutput = new PipedOutputStream(clientInput);

        PipedInputStream serverInput = new PipedInputStream();
        PipedOutputStream serverOutput = new PipedOutputStream(serverInput);

        BufferedReader in = new BufferedReader(new InputStreamReader(clientInput));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(serverOutput));

        ClientHandler clientHandler = clientHandlers.get(0);
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            try {
                turnManager.handleClientHandlerTurn(clientHandler, in, out);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }).start();

        Thread.sleep(1000); // Give some time for the turnManager to start handling the turn
        clientHandler.giveTurn();
        latch.await(2, TimeUnit.SECONDS); // Wait for the turnManager to finish handling the turn

        assertTrue(clientHandler.isTurn());
    }

    @Test
    void testHandleClientTurn() throws IOException, InterruptedException {
        AtomicInteger clientTurnCount = new AtomicInteger();
        AtomicBoolean serverTurn = new AtomicBoolean(true);


        List<ClientHandler> clientHandlers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            clientHandlers.add(new ClientHandlerMock());
        }

        TurnManager turnManager = new TurnManager(clientHandlers) {
            @Override
            protected void giveTurnToNextClient() {
                clientHandlers.get(clientTurnCount.getAndIncrement() % clientHandlers.size()).giveTurn();
            }

            @Override
            protected boolean isServerTurn() {
                return serverTurn.get();
            }
        };

        // Create a mock Client
        Client client = new ClientMock();
        BufferedReader in = new BufferedReader(new StringReader("test message\n"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);

        // initial state
        assertFalse(clientHandlers.get(0).isTurn());
        assertFalse(clientHandlers.get(1).isTurn());
        assertFalse(clientHandlers.get(2).isTurn());

        // server turn
        Thread t = new Thread(() -> {
            try {
                turnManager.handleClientTurn(client, in, out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t.start();

        while (true) {
            if (serverTurn.get()) {
                out.writeUTF("test message\n");
                out.flush();
                turnManager.latch.countDown();
                break;
            }
        }

        serverTurn.set(false);

        // client turn
        turnManager.handleClientTurn(client, in, out);

        while (true) {
            if (!serverTurn.get()) {
                assertTrue(clientHandlers.get(0).isTurn());
                turnManager.latch.countDown(); //
                break;
            }
        }

        // other client turns
        for (int i = 1; i < clientHandlers.size(); i++) {
            assertFalse(clientHandlers.get(i).isTurn());
        }

        t.join(); // Thread Completion
    }



}
