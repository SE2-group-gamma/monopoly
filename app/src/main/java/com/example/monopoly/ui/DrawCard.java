package com.example.monopoly.ui;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.R;
import com.example.monopoly.databinding.FragmentDrawcardBinding;
import com.example.monopoly.gamelogic.ChanceCardCollection;
import com.example.monopoly.gamelogic.CommunityChestCardCollection;

public class DrawCard extends Fragment {
    private ChanceCardCollection chanceCards;
    private CommunityChestCardCollection communityCards;
    private FragmentDrawcardBinding binding;

    public DrawCard(){
        super(R.layout.fragment_drawcard);
        createDecks();
        setDrawables();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = FragmentDrawcardBinding.inflate(getLayoutInflater());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        returnChanceCard();
        binding.buttonContinueDrawCard.setOnClickListener(view -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_DrawCard_to_GameBoardUI);
        });

        return this.binding.getRoot();
    }

    public void returnChanceCard(){
        int index = chanceCards.drawFromDeck().getId();
        binding.ImageCard.setImageResource(chanceCards.getChanceCardDeck().get(index).getImageId());
    }

    public void returnCommunityChestCard(){
        int index = communityCards.drawFromDeck().getId();
        binding.ImageCard.setImageResource(communityCards.getCommunityChestCardDeck().get(index).getImageId());
    }

    // * * * TEST * * *
    public void createDecks(){
        this.chanceCards = new ChanceCardCollection();
        this.communityCards = new CommunityChestCardCollection();
    }

    public void setDrawables(){
        chanceCards.getChanceCardDeck().get(0).setImageId(R.drawable.chance0);
        chanceCards.getChanceCardDeck().get(1).setImageId(R.drawable.chance1);
        chanceCards.getChanceCardDeck().get(2).setImageId(R.drawable.chance2);
        chanceCards.getChanceCardDeck().get(3).setImageId(R.drawable.chance3);
        chanceCards.getChanceCardDeck().get(4).setImageId(R.drawable.chance4);
        chanceCards.getChanceCardDeck().get(5).setImageId(R.drawable.chance5);
        chanceCards.getChanceCardDeck().get(6).setImageId(R.drawable.chance6);
        chanceCards.getChanceCardDeck().get(7).setImageId(R.drawable.chance7);
        chanceCards.getChanceCardDeck().get(8).setImageId(R.drawable.chance8);
        chanceCards.getChanceCardDeck().get(9).setImageId(R.drawable.chance9);
        chanceCards.getChanceCardDeck().get(10).setImageId(R.drawable.chance10);
        chanceCards.getChanceCardDeck().get(11).setImageId(R.drawable.chance11);
        chanceCards.getChanceCardDeck().get(12).setImageId(R.drawable.chance12);
        chanceCards.getChanceCardDeck().get(13).setImageId(R.drawable.chance13);
        chanceCards.getChanceCardDeck().get(14).setImageId(R.drawable.chance14);
        chanceCards.getChanceCardDeck().get(15).setImageId(R.drawable.chance15);
        chanceCards.getChanceCardDeck().get(16).setImageId(R.drawable.chance16);
        chanceCards.getChanceCardDeck().get(17).setImageId(R.drawable.chance17);
        chanceCards.getChanceCardDeck().get(18).setImageId(R.drawable.chance18);
        chanceCards.getChanceCardDeck().get(19).setImageId(R.drawable.chance19);

        communityCards.getCommunityChestCardDeck().get(0).setImageId(R.drawable.community0);
        communityCards.getCommunityChestCardDeck().get(1).setImageId(R.drawable.community1);
        communityCards.getCommunityChestCardDeck().get(2).setImageId(R.drawable.community2);
        communityCards.getCommunityChestCardDeck().get(3).setImageId(R.drawable.community3);
        communityCards.getCommunityChestCardDeck().get(4).setImageId(R.drawable.community4);
        communityCards.getCommunityChestCardDeck().get(5).setImageId(R.drawable.community5);
        communityCards.getCommunityChestCardDeck().get(6).setImageId(R.drawable.community6);
        communityCards.getCommunityChestCardDeck().get(7).setImageId(R.drawable.community7);
        communityCards.getCommunityChestCardDeck().get(8).setImageId(R.drawable.community8);
        communityCards.getCommunityChestCardDeck().get(9).setImageId(R.drawable.community9);
        communityCards.getCommunityChestCardDeck().get(10).setImageId(R.drawable.community10);
        communityCards.getCommunityChestCardDeck().get(11).setImageId(R.drawable.community11);
        communityCards.getCommunityChestCardDeck().get(12).setImageId(R.drawable.community12);
        communityCards.getCommunityChestCardDeck().get(13).setImageId(R.drawable.community13);
        communityCards.getCommunityChestCardDeck().get(14).setImageId(R.drawable.community14);
        communityCards.getCommunityChestCardDeck().get(15).setImageId(R.drawable.community15);
        communityCards.getCommunityChestCardDeck().get(16).setImageId(R.drawable.community16);
        communityCards.getCommunityChestCardDeck().get(17).setImageId(R.drawable.community17);
        communityCards.getCommunityChestCardDeck().get(18).setImageId(R.drawable.community18);
        communityCards.getCommunityChestCardDeck().get(19).setImageId(R.drawable.community19);
    }

}
