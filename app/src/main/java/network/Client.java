package network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread {
    private InetAddress host;
    private int port;
    public String response;
    public String request;



    Client(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    public void setRequest(String request){
        this.request = request;
    }
    public String getResponse(){
        return response;
    }
    public void run() {
        try {

            Socket clientSocket = new Socket(host, port);

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            outToServer.writeBytes(request + 'n');
            response = inFromServer.readLine();

            clientSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
