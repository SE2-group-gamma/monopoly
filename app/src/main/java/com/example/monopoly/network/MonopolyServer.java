package com.example.monopoly.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MonopolyServer extends Thread{

    private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    private int localPort;
    private int maxNumberOfClients;
    private boolean isListening;

    private List<Client> clientsTurn;

    //private Game game;
    private String hostname;
    private Client client;
    private int counter=1;
    private int clientc=0;




    private HashMap<Integer, ClientHandler> keyedHandlers;

    public MonopolyServer(int maxNumberOfClients) throws IOException {
        this.serverSocket = new ServerSocket(6969);
        this.localPort = serverSocket.getLocalPort();
        this.maxNumberOfClients = maxNumberOfClients;
        this.clients = new ArrayList<ClientHandler>();
        this.isListening = false;
        this.keyedHandlers=new HashMap<>();
        this.clientsTurn=new ArrayList<Client>();

    }

    public void setHostname(String hostname){
        this.hostname=hostname;
    }

    public void setClient(Client client){
        synchronized (this.clients){
            //Log.i("",client.isHost()+"");
            this.client=client;

            for (ClientHandler handler:this.clients) {
                handler.setClient(this.clientsTurn.get(this.clientc));

            }
        }
    }



    public List<ClientHandler> getClients() {
        return clients;
    }

    public int getClientc() {
        return clientc;
    }

    public void setClientc(int clientc) {
        this.clientc = clientc;
    }

    // Constructor for testing
    public MonopolyServer(int maxNumberOfClients, ServerSocket serverSocket) throws IOException {
        this.serverSocket = serverSocket;
        this.localPort = this.serverSocket.getLocalPort();
        this.maxNumberOfClients = maxNumberOfClients;
        this.clients = new ArrayList<ClientHandler>();
        this.isListening = false;
        this.keyedHandlers=new HashMap<>();
        this.clientsTurn=new ArrayList<Client>();


    }

    public void broadCast(String msg){
        synchronized (this.clients) {
            for (ClientHandler clientHandler : clients) {
                clientHandler.writeToClient(msg);

            }
        }
    }

    public void broadCastExceptSelf(String msg, ClientHandler hostHandler){
        synchronized (this.clients) {
            for (ClientHandler clientHandler : clients) {
                if(hostHandler != clientHandler)
                    clientHandler.writeToClient(msg);
            }
        }
    }

    @Override
    public void run() {
        this.isListening = true;
        int count = 0;



        //game = new Game();
        //Log.d("",""+this.maxNumberOfClients);
        while(isListening() && this.clients.size() <= maxNumberOfClients){
            ClientHandler clientHandler = null;
            try {
                // serverSocket.accept() waits for Clients to connect
                Socket socket = serverSocket.accept();
                clientHandler = new ClientHandler(socket,hostname,client);
                clientHandler.setServer(this);
                count++;

                String message = "#" + count + " from "
                        + socket.getInetAddress() + ":"
                        + socket.getPort() + "\n";
                //Log.d("SocketConn",message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            clientHandler.start();
            synchronized (this.clients){
                this.clients.add(clientHandler);
                //Log.d("clientcheck1324", "add Client Handler"+ clientHandler.getClientClient().getName());
                keyedHandlers.put(counter++,clientHandler);
            }

        }
        try {
            stopListening();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void stopListening() throws IOException {
        this.isListening = false;
        this.serverSocket.close();
    }

    public synchronized boolean isListening() {
        return isListening;
    }

    public synchronized int getNumberOfClients(){
        return this.clients.size();
    }

    public int getLocalPort() {
        return localPort;
    }


    public List<Client> getClientsTurn() {
        return clientsTurn;
    }

    public void setClientsTurn(List<Client> clientsTurn) {
        this.clientsTurn = clientsTurn;
    }
}
