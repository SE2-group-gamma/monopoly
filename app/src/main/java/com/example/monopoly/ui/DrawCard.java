package com.example.monopoly.ui;

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.monopoly.R;
import com.example.monopoly.gamelogic.ChanceCardCollection;
import com.example.monopoly.gamelogic.CommunityChestCardCollection;

public class DrawCard extends Fragment {
    private ChanceCardCollection chanceCards;
    private CommunityChestCardCollection communityCards;

    public DrawCard(){
        createDecks();
    }

    public void createDecks(){
        chanceCards = new ChanceCardCollection();
        communityCards = new CommunityChestCardCollection();
    }


}
