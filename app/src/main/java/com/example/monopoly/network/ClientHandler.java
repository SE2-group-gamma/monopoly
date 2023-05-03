package com.example.monopoly.network;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.monopoly.ui.UIHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;

public class ClientHandler extends Thread{

    private Socket client;
    private BufferedReader br;
    private BufferedWriter bw;


    public ClientHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            this.br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            //bw.write("Lobby|changeText|Martin Jäger"+System.lineSeparator());
            //bw.flush();

            while(true){
                if(br.ready()){
                    String msg = br.readLine();
                    Log.d("msg123",msg);
                    bw.write(msg+System.lineSeparator());
                    bw.flush();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
