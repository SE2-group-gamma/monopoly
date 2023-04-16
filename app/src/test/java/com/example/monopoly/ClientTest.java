package com.example.monopoly;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.example.monopoly.network.Client;

import org.junit.jupiter.api.Test;

public class ClientTest {

private String request = "request";
private String response = "response";
Client client;
byte IPAddress[] = { 127, 0, 0, 1 };


    @Test
    public void testConnectionRefused() throws IOException {
        InetAddress address = InetAddress.getByAddress(IPAddress);
        client = new Client(address,0);

        assertThrows(RuntimeException.class, () -> {
            client.run();
        });

    }

    @Test
    public void testClientGettersSetters() throws UnknownHostException {
        InetAddress address = InetAddress.getByAddress(IPAddress);
        client = new Client(address,0);
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



}
