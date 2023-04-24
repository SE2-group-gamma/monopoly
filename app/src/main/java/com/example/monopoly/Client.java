package com.example.monopoly;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class Client extends Thread {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;
    private TextView clientLog;

    private boolean roll = false;
    private Handler handler = new Handler(Looper.getMainLooper());

    public Client(TextView clientLog) {
        this.clientLog = clientLog;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String id = "Player" + new Random().nextInt(1000);
            out.println(id);

            while (!Thread.currentThread().isInterrupted()) {
                String serverMessage = in.readLine();
                handler.post(() -> clientLog.append("Server: " + serverMessage + "\n"));
System.out.println("here");
                if (serverMessage.startsWith("It's your turn")&&roll==true) {
                    System.out.println("GG");
                    int diceValue = rollDice();
                    roll=false;
                    out.println(diceValue);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int rollDice() {
        return new Random().nextInt(6) + 1;
    }


    public boolean isRoll() {
        return roll;
    }

    public void setRoll(boolean roll) {
        this.roll = roll;
    }
}
