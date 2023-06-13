package com.example.monopoly.ui;

import android.content.Context;
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
import com.example.monopoly.ui.viewmodels.CardViewModel;

import java.io.IOException;


public class DrawCardFragment extends Fragment {
    private ChanceCardCollection chanceCards;
    private CommunityChestCardCollection communityCards;
    private FragmentDrawcardBinding binding;
    private CardViewModel cardViewModel;
    private Game game = Game.getInstance();

    public static Context context;


    public DrawCardFragment() {
        super(R.layout.fragment_drawcard);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentDrawcardBinding.inflate(getLayoutInflater());

        cardViewModel = new ViewModelProvider(requireActivity()).get(CardViewModel.class);
        this.chanceCards = cardViewModel.getChanceCards().getValue();
        this.communityCards = cardViewModel.getCommunityCards().getValue();
        context = getAppContext();
        try {
            checkField();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding.buttonContinueDrawCard.setOnClickListener(view -> {
            this.cardViewModel.setChanceCards(this.chanceCards);
            this.cardViewModel.setCommunityCards(this.communityCards);
            NavHostFragment.findNavController(this).navigate(R.id.action_DrawCardFragment_to_GameBoardUI);
        });

        /*try {
            game.doAction();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        return this.binding.getRoot();
    }


    private void checkField() throws IOException {
        if (game.getPlayers().get(game.getPlayerIDByName(game.getCurrentPlayersTurn())).getPosition() == 7 ||
                game.getPlayers().get(game.getPlayerIDByName(game.getCurrentPlayersTurn())).getPosition() == 22 ||
                game.getPlayers().get(game.getPlayerIDByName(game.getCurrentPlayersTurn())).getPosition() == 36) {
            returnChanceCard();

        } else if (game.getPlayers().get(game.getPlayerIDByName(game.getCurrentPlayersTurn())).getPosition() == 2 ||
                game.getPlayers().get(game.getPlayerIDByName(game.getCurrentPlayersTurn())).getPosition() == 17 ||
                game.getPlayers().get(game.getPlayerIDByName(game.getCurrentPlayersTurn())).getPosition() == 33) {
            returnCommunityChestCard();
        } else {
            Log.i("Cards", "The Player is neither on a Chance-Field nor on a Community-Chest-Field");
        }

        //temp
        returnChanceCard();
    }

    private void returnChanceCard() throws IOException {
        int index = chanceCards.drawFromDeck().getId();
        int cardId = chanceCards.getChanceCardDeck().get(index).getImageId();
        binding.ImageCard.setImageResource(cardId);
        /*game.getPlayers().get(game.getPlayerIDByName(game.getCurrentPlayersTurn())).getMyClient().writeToServer(
                "GameBoardUI|setCard|" + cardId + "|" + game.getCurrentPlayersTurn());

        game.getPlayers().get(game.getPlayerIDByName(game.getCurrentPlayersTurn())).getMyClient().writeToServer(
                "DrawFragment|removeCardBroadcast|" + cardId + ":chance|" + game.getCurrentPlayersTurn());*/
    }

    private void returnCommunityChestCard() throws IOException {
        int index = communityCards.drawFromDeck().getId();
        int cardId = communityCards.getCommunityChestCardDeck().get(index).getImageId();
        binding.ImageCard.setImageResource(cardId);
        /*game.getPlayers().get(game.getPlayerIDByName(game.getCurrentPlayersTurn())).getMyClient().writeToServer(
                "GameBoardUI|setCard|" + cardId + "|" + game.getCurrentPlayersTurn());
        game.getPlayers().get(game.getPlayerIDByName(game.getCurrentPlayersTurn())).getMyClient().writeToServer(
                "DrawFragment|removeCardBroadcast|" + cardId + ":community|" + game.getCurrentPlayersTurn());*/
    }

    public static Context getAppContext() {
        return context;
    }
}
