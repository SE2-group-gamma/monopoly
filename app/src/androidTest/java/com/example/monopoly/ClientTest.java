package com.example.monopoly;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import network.Client;

public class ClientTest {

private String request = "request";
private String response = "response";


    @Test
    public void testClientSocketRequest() throws UnknownHostException {
        byte IPAddress[] = { 127, 0, 0, 1 };
        InetAddress address = InetAddress.getByAddress(IPAddress);
        Client client = new Client(address,0);
        client.setRequest(request);
        assertEquals(client.getRequest(),request);
    }

    @Test
    public void testClientSocketResponse() throws UnknownHostException {
        byte IPAddress[] = { 127, 0, 0, 1 };
        InetAddress address = InetAddress.getByAddress(IPAddress);
        Client client = new Client(address,0);
        client.setResponse(response);
        assertEquals(client.getResponse(),response);
    }
    


}
