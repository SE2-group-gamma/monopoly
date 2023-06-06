package com.example.monopoly.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.R;
import com.example.monopoly.databinding.FragmentDrawcardBinding;
import com.example.monopoly.gamelogic.ChanceCardCollection;
import com.example.monopoly.gamelogic.CommunityChestCardCollection;
import com.example.monopoly.gamelogic.Game;
import com.example.monopoly.network.Client;
import com.example.monopoly.ui.viewmodels.ClientViewModel;
import com.example.monopoly.ui.viewmodels.DrawCardViewModel;

import java.io.IOException;


public class DrawCardFragment extends Fragment {
    private ChanceCardCollection chanceCards;
    private CommunityChestCardCollection communityCards;
    private FragmentDrawcardBinding binding;
    private DrawCardViewModel drawCardViewModel;
    private ClientViewModel clientViewModel;
    private Client client;
    private final Game game = Game.getInstance();


    public DrawCardFragment() {
        super(R.layout.fragment_drawcard);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentDrawcardBinding.inflate(getLayoutInflater());

        drawCardViewModel = new ViewModelProvider(requireActivity()).get(DrawCardViewModel.class);
        clientViewModel = new ViewModelProvider(requireActivity()).get(ClientViewModel.class);
        this.chanceCards = drawCardViewModel.getChanceCards().getValue();
        this.communityCards = drawCardViewModel.getCommunityCards().getValue();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.client = clientViewModel.getClientData().getValue();
        checkField();

        binding.buttonContinueDrawCard.setOnClickListener(view -> {
            this.drawCardViewModel.setChanceCards(chanceCards);
            this.drawCardViewModel.setCommunityCards(communityCards);
            NavHostFragment.findNavController(this).navigate(R.id.action_DrawCardFragment_to_GameBoardUI);
        });

        try {
            game.doAction(game.getPlayerIDByName(game.getCurrentPlayersTurn()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this.binding.getRoot();
    }


    private void checkField() {

        // TODO: the if-condition has to check the actual position of the player
        if (game.getPlayers().get(game.getPlayerIDByName(game.getCurrentPlayersTurn())).getPosition() == 7 ||
                game.getPlayers().get(game.getPlayerIDByName(game.getCurrentPlayersTurn())).getPosition() == 22 ||
                game.getPlayers().get(game.getPlayerIDByName(game.getCurrentPlayersTurn())).getPosition() == 36){
            returnChanceCard();

        } else if (game.getPlayers().get(game.getPlayerIDByName(game.getCurrentPlayersTurn())).getPosition() == 2 ||
                game.getPlayers().get(game.getPlayerIDByName(game.getCurrentPlayersTurn())).getPosition() == 17 ||
                game.getPlayers().get(game.getPlayerIDByName(game.getCurrentPlayersTurn())).getPosition() == 33) {
            returnCommunityChestCard();
        } else {
            Log.i("Cards", "The Player is neither on a Chance-Field nor on a Community-Chest-Field");
        }

    }

    private void returnChanceCard() {
        int index = chanceCards.drawFromDeck().getId();
        binding.ImageCard.setImageResource(chanceCards.getChanceCardDeck().get(index).getImageId());
        client.getUser().setCardID(chanceCards.getChanceCardDeck().get(index).getImageId());
    }

    private void returnCommunityChestCard() {
        int index = communityCards.drawFromDeck().getId();
        binding.ImageCard.setImageResource(communityCards.getCommunityChestCardDeck().get(index).getImageId());
        client.getUser().setCardID(communityCards.getCommunityChestCardDeck().get(index).getImageId());
    }
}
