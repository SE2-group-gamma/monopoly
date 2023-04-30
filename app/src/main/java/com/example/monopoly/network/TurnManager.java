package com.example.monopoly.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class TurnManager {
    private int currentIndex;
    private List<ClientHandler> clientHandlers;
    private boolean isServerTurn;

    public TurnManager(List<ClientHandler> clientHandlers) {
        this.clientHandlers = clientHandlers;
        this.currentIndex = 0;
        this.isServerTurn = true;
    }

    public void handleClientHandlerTurn(ClientHandler clientHandler, BufferedReader in, BufferedWriter out) throws IOException {
        synchronized (this) {
            try {
                // This loop now checks if it's the clientHandler's turn and if the server's turn is over
                while (!clientHandler.isTurn() || isServerTurn) {
                    wait();
                }

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    for (ClientHandler otherClient : clientHandlers) {
                        if (otherClient != clientHandler) {
                            BufferedWriter otherOut = new BufferedWriter(new OutputStreamWriter(otherClient.getOutputStream()));
                            otherOut.write("Client " + clientHandler.getId() + ": " + inputLine + "\n");
                            otherOut.flush();
                        }
                    }

                    // Update the turn
                    currentIndex = (currentIndex + 1) % clientHandlers.size();
                    clientHandlers.get(currentIndex).giveTurn();
                    isServerTurn = true;
                    break;
                }
            } catch (InterruptedException e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                clientHandler.setTurn(false);
                notifyAll();
            }
        }
    }


    public void handleClientTurn(Client client, BufferedReader in, DataOutputStream out) throws IOException {
        synchronized (this) {
            try {
                while (true) {
                    if (isServerTurn) {
                        out.writeUTF("It is currently the server's turn.\n");
                        out.flush();
                    } else {
                        while (!clientHandlers.get(currentIndex).isTurn()) {
                            wait();
                        }

                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            out.writeUTF("Client " + client.getId() + ": " + inputLine);
                            out.flush();

                            currentIndex = (currentIndex + 1) % clientHandlers.size();
                            clientHandlers.get(currentIndex).giveTurn();
                            isServerTurn = true;
                            break;
                        }
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void start() {
        currentIndex = 0;
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.setTurn(false);
            clientHandler.start();
        }

        clientHandlers.get(0).giveTurn();
    }
}
