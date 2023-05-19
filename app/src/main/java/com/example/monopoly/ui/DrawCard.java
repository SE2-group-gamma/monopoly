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
        setDrawables();
    }

    public void createDecks(){
        chanceCards = new ChanceCardCollection();
        communityCards = new CommunityChestCardCollection();
    }

    public void setDrawables(){
        chanceCards.getChanceCardDeck().get(0).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance0));
        chanceCards.getChanceCardDeck().get(1).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance1));
        chanceCards.getChanceCardDeck().get(2).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance2));
        chanceCards.getChanceCardDeck().get(3).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance3));
        chanceCards.getChanceCardDeck().get(4).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance4));
        chanceCards.getChanceCardDeck().get(5).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance5));
        chanceCards.getChanceCardDeck().get(6).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance6));
        chanceCards.getChanceCardDeck().get(7).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance7));
        chanceCards.getChanceCardDeck().get(8).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance8));
        chanceCards.getChanceCardDeck().get(9).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance9));
        chanceCards.getChanceCardDeck().get(10).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance10));
        chanceCards.getChanceCardDeck().get(11).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance11));
        chanceCards.getChanceCardDeck().get(12).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance12));
        chanceCards.getChanceCardDeck().get(13).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance13));
        chanceCards.getChanceCardDeck().get(14).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance14));
        chanceCards.getChanceCardDeck().get(15).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance15));
        chanceCards.getChanceCardDeck().get(16).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance16));
        chanceCards.getChanceCardDeck().get(17).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance17));
        chanceCards.getChanceCardDeck().get(18).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance18));
        chanceCards.getChanceCardDeck().get(19).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.chance19));

        communityCards.getCommunityChestCardDeck().get(0).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community0));
        communityCards.getCommunityChestCardDeck().get(1).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community1));
        communityCards.getCommunityChestCardDeck().get(2).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community2));
        communityCards.getCommunityChestCardDeck().get(3).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community3));
        communityCards.getCommunityChestCardDeck().get(4).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community4));
        communityCards.getCommunityChestCardDeck().get(5).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community5));
        communityCards.getCommunityChestCardDeck().get(6).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community6));
        communityCards.getCommunityChestCardDeck().get(7).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community7));
        communityCards.getCommunityChestCardDeck().get(8).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community8));
        communityCards.getCommunityChestCardDeck().get(9).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community9));
        communityCards.getCommunityChestCardDeck().get(10).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community10));
        communityCards.getCommunityChestCardDeck().get(11).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community11));
        communityCards.getCommunityChestCardDeck().get(12).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community12));
        communityCards.getCommunityChestCardDeck().get(13).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community13));
        communityCards.getCommunityChestCardDeck().get(14).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community14));
        communityCards.getCommunityChestCardDeck().get(15).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community15));
        communityCards.getCommunityChestCardDeck().get(16).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community16));
        communityCards.getCommunityChestCardDeck().get(17).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community17));
        communityCards.getCommunityChestCardDeck().get(18).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community18));
        communityCards.getCommunityChestCardDeck().get(19).setImage(ContextCompat.getDrawable(requireContext(), R.drawable.community19));
    }

}
