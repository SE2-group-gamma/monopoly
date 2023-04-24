package com.example.monopoly;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Server extends Thread {
    private static final int PORT = 5000;
    private static List<Player> players = new ArrayList<>();
    private ServerSocket serverSocket;
    private TextView serverLog;
    private Handler handler = new Handler(Looper.getMainLooper());

    public Server(TextView serverLog) {
        this.serverLog = serverLog;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
            handler.post(() -> serverLog.append("Server started\n"));
            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String id = in.readLine();
            Player player = new Player(id);
            players.add(player);

            handler.post(() -> serverLog.append("Player connected: " + id + "\n"));
            out.println("Welcome, " + id + "! You can roll the dice when it's your turn.");

            while (true) {
                for (Player p : players) {
                    if (p.getId().equals(id)) {
                        out.println("It's your turn to roll the dice!");
                        int diceValue = Integer.parseInt(in.readLine());
                        p.setDiceValue(diceValue);
                        handler.post(() -> serverLog.append("Player " + id + " rolled: " + diceValue + "\n"));
                    } else {
                        out.println("Waiting for " + p.getId() + " to roll the dice...");
                        Thread.sleep(1000);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("Server", ex.toString());
        }
        return null;
    }

}
