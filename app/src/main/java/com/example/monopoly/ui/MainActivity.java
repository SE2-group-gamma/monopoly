package com.example.monopoly.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.nsd.NsdManager;
import android.os.Bundle;

import com.example.monopoly.R;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.monopoly.databinding.ActivityMainBinding;
import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.network.ClientHandler;
import com.example.monopoly.network.MonopolyServer;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private NSD_Client nsdClient;
    private MonopolyServer monopoly;
    private NSDServer nsdServ;
    private ClientHandler clientHandler;
    private NsdManager nsdManager;

    private Button leaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        //Inizialize NSDManager for client-sided Network Service Discovery
        /*NsdManager manager = (NsdManager) getSystemService(Context.NSD_SERVICE);
        NSD_Client nsd = new NSD_Client();
        nsd.start(manager);*/


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }



    private void closeServerConnection(){
        if (nsdServ != null && monopoly != null){
            try{
                monopoly.shutdownServer();
                nsdServ.stopNSD();
                Log.i("ServerActivity", "Server done main");
            }catch ( IOException e){
                e.printStackTrace();
            }

        }

    }

    private void closeClientConnection() {
        if(clientHandler != null && clientHandler.getClient() != null){
            try{
                clientHandler.getClient().close();
                //clientHandler.endConn();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        if (nsdClient != null){
            nsdClient.stopDiscovery();

        }
        Log.i("ClientActivity","Client closed 3");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        nsdClient.stopDiscovery();
        try {
            monopoly.shutdownServer();
            clientHandler.getClient().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        nsdServ.stopNSD();

        Log.i("MainActivity", "Conections done");


    }
}