package com.example.monopoly;

import com.example.monopoly.network.ClientHandler;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

class ClientHandlerMock extends ClientHandler {
    public ClientHandlerMock() {
        super(null, null);
    }

    @Override
    public OutputStream getOutputStream() {
        return new ByteArrayOutputStream();
    }

    @Override
    public void run() {
    }
}

