package com.example.monopoly.ui;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import com.example.monopoly.network.Client;

import java.net.InetAddress;

public class NSD_Client {

    private String nsdTag = "monopoly";
    private String serviceType = "_monopoly._tcp.";         //added . because the service added a . to the end?

    /*
    serviceType needs to be changed to "_monopoly" after service is registered, otherwise App will not start
    */

    private NsdManager manager;
    private NsdManager.DiscoveryListener discoveryListener;
    private NsdManager.ResolveListener resolveListener;
    private int port;
    private InetAddress host;
    private String request;
    private Client client;
    private int count = 0;
    private Object readyToken="";
    private boolean isReady = false;
    private boolean isHost = false;

    public boolean isReady() {
        synchronized (readyToken){
            return isReady;
        }
    }

    public void setIsHost(boolean host){
        this.isHost=host;
    }


    public void start(NsdManager manager){
        this.manager = manager;

        initializeDiscoveryListener();
        initializeResolveListener();

        manager.discoverServices(serviceType, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }



    public void initializeDiscoveryListener() {

        discoveryListener = new NsdManager.DiscoveryListener() {
            @Override
            public void onStartDiscoveryFailed(String s, int i) {
                Log.e(nsdTag, "onStartDiscoveryFailed");
                manager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String s, int i) {
                Log.e(nsdTag,"onStopDiscoveryFailed");
                manager.stopServiceDiscovery(this);
            }

            @Override
            public void onDiscoveryStarted(String s) {
                Log.i(nsdTag,"onDiscoveryStarted");
            }

            @Override
            public void onDiscoveryStopped(String s) {
                Log.i(nsdTag,"onDiscoveryStopped");
            }

            @Override
            public void onServiceFound(NsdServiceInfo nsdServiceInfo) {
                if (!nsdServiceInfo.getServiceType().equals(serviceType)){
                    Log.i(nsdTag, "onServiceFound unknown service: " + nsdServiceInfo.getServiceType()+" mytype:"+serviceType);
                }
                else{
                    if(count == 0) {            // because it gets called 2 times?
                        Log.i(nsdTag, "onServiceFound known service: " + nsdServiceInfo.getServiceType());
                        manager.resolveService(nsdServiceInfo, resolveListener);
                        count++;
                    }
                }

            }

            @Override
            public void onServiceLost(NsdServiceInfo nsdServiceInfo) {
                Log.e(nsdTag,"onServiceLost");
            }
        };
    }

    public Client getClient() {
        return client;
    }

    public void initializeResolveListener() {

        resolveListener = new NsdManager.ResolveListener() {
            @Override
            public void onResolveFailed(NsdServiceInfo nsdServiceInfo, int i) {
                Log.e(nsdTag,"onResolveFailed: " + nsdServiceInfo + "" + i);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo nsdServiceInfo) {
                Log.i(nsdTag, "onServiceResolved: " + nsdServiceInfo);
                port = nsdServiceInfo.getPort();
                host = nsdServiceInfo.getHost();

                Log.d("SocketConn","Searching ...");
                client = new Client(host, port, isHost);
                client.start();
                synchronized (readyToken){
                    isReady=true;
                }
            }
        };
    }
}
