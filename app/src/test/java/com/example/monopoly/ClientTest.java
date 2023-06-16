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

    /*@Mock
    private Socket clientSocket;
    @Mock
    private DataOutputStream outToServer;
    @Mock
    private BufferedReader inFromServer;
    @Mock
    private InputStream in;*/

    Client client3;
    //private Client testClient;


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



        /*InetAddress host = InetAddress.getByName("localhost");
        int port = 6969;
        Player user = new Player("testuser", new Color(), 1000.0, true);
        boolean isHost = false;
        List<String> receivedMessages = new ArrayList<>();
        testClient = new Client(host, port, user, isHost);

        testClient.setClientSocket(clientSocket);
        testClient.outToServer = outToServer;
        when(clientSocket.getOutputStream()).thenReturn(outToServer);
        //when(new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))).thenReturn(inFromServer);
        when(clientSocket.getInputStream()).thenReturn(in);*/
    }

    @Test
    public void testRun(){
        Thread testThread = new Thread(client3);
        //testThread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        testThread.interrupt();
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


    /*@Test
    public void testClientServerConnection() throws Exception {
        // Set up a mock server
        ServerSocket mockServerSocket = new ServerSocket(6969);
        mockServerSocket.setSoTimeout(5000);
        Socket mockServer = mockServerSocket.accept();
        System.out.println("asdad");
        PrintWriter serverOut = new PrintWriter(mockServer.getOutputStream(), true);
        BufferedReader serverIn = new BufferedReader(new InputStreamReader(mockServer.getInputStream()));



        // Set up the client
        InetAddress host = InetAddress.getLocalHost();
        int port = 6969;
        Player user = new Player("testuser", new Color(), 1000.0, true);
        boolean isHost = false;
        Client client = new Client(host, port, user, isHost);

        // Start the client thread
        Thread clientThread = new Thread(client);
        clientThread.start();

        // Wait for the client to connect to the server
        Thread.sleep(1000);

        // Send a message from the client to the server
        client.writeToServer("CLIENTMESSAGE|data|Hello, server!");

        // Wait for the message to be received by the server
        String receivedMessage = serverIn.readLine();
        System.out.println("Test132: "+receivedMessage);
        assertEquals("CLIENTMESSAGE|data|Hello, server!", receivedMessage);

        // Send a response from the server to the client
        serverOut.println("SERVERRESPONSE|data|Hello, " + user.getUsername() + "!");

        // Wait for the response to be received by the client
        Thread.sleep(1000);
        List<String> receivedMessages = client.getReceivedMessages();
        assertEquals(1, receivedMessages.size());
        assertEquals("SERVERRESPONSE|data|Hello, " + user.getUsername() + "!", receivedMessages.get(0));

        Thread.sleep(5000);
        //client.stopThread();
        clientThread.stop();

        // Clean up
        mockServerSocket.close();
        clientThread.interrupt();
    }
*/

  /*  @Test
    public void testTurnProcess() throws Exception {
        MonopolyServer monopolyServerMock = Mockito.mock(MonopolyServer.class);
        HashMap<String, Player> playersMock = new HashMap<>();
        playersMock.put("user1", new Player("user1", new Color(), 500.00, true));
        playersMock.put("user2", new Player("user2", new Color(), 500.00, false));
        Game gameMock = Mockito.mock(Game.class);

        when(gameMock.getPlayers()).thenReturn(playersMock);
        when(gameMock.getCurrentPlayersTurn()).thenReturn("user1");
        when(monopolyServerMock.getNumberOfClients()).thenReturn(playersMock.size());

        client.setMonopolyServer(monopolyServerMock);
        client.setGame(gameMock);

        client.turnProcess();

        Thread.sleep(70000);

        verify(gameMock).setCurrentPlayersTurn(anyString());
        verify(monopolyServerMock).broadCast(contains("GameBoardUI|playersTurn|"));
        assertEquals("user2", gameMock.getCurrentPlayersTurn());
    }
*/





}
