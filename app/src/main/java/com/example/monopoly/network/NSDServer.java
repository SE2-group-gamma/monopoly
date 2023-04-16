package com.example.monopoly.network;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

public class NSDServer implements NsdManager.RegistrationListener{

    private NsdManager nsdManager;

    public NSDServer(NsdManager nsdManager) {
        this.nsdManager = nsdManager;
    }

    public void unregisterNSDService(){
        this.nsdManager.unregisterService(this);
    }

    public void registerNSDService(int port){
        NsdServiceInfo serviceInfo = new NsdServiceInfo();

        serviceInfo.setServiceName("monopoly");
        serviceInfo.setServiceType("_monopoly._tcp");
        serviceInfo.setPort(port);

        nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, this);
    }

    @Override
    public void onRegistrationFailed(NsdServiceInfo nsdServiceInfo, int i) {
        Log.e("NSDServer", "Registration failed with info: " + i);
    }

    @Override
    public void onUnregistrationFailed(NsdServiceInfo nsdServiceInfo, int i) {
        Log.e("NSDServer", "Unregistration failed with info: " + i);
    }

    @Override
    public void onServiceRegistered(NsdServiceInfo nsdServiceInfo) {
        Log.i("NSDServer", "Service registered");
    }

    @Override
    public void onServiceUnregistered(NsdServiceInfo nsdServiceInfo) {
        Log.i("NSDServer", "Service unregistered");
    }
}
