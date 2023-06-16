package com.example.monopoly.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.monopoly.R;
import com.example.monopoly.databinding.FragmentDrawcardBinding;
import com.example.monopoly.gamelogic.Board;
import com.example.monopoly.gamelogic.ChanceCardCollection;
import com.example.monopoly.gamelogic.CommunityChestCardCollection;
import com.example.monopoly.gamelogic.Game;
import com.example.monopoly.ui.viewmodels.CardViewModel;
import com.example.monopoly.ui.viewmodels.ClientViewModel;
import com.example.monopoly.ui.viewmodels.DiceViewModel;

import java.io.IOException;


public class DrawCardFragment extends Fragment {
    private ChanceCardCollection chanceCards;
    private CommunityChestCardCollection communityCards;
    private FragmentDrawcardBinding binding;
    private DiceViewModel diceViewModel;
    private CardViewModel cardViewModel;
    private Game game = Game.getInstance();
    private ClientViewModel clientViewModel;


    public static Context context;


    public DrawCardFragment() {
        super(R.layout.fragment_drawcard);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentDrawcardBinding.inflate(getLayoutInflater());

        cardViewModel = new ViewModelProvider(requireActivity()).get(CardViewModel.class);
        clientViewModel = new ViewModelProvider(requireActivity()).get(ClientViewModel.class);
        chanceCards = cardViewModel.getChanceCards().getValue();
        communityCards = cardViewModel.getCommunityCards().getValue();
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
            try {
                //game.doAction(clientViewModel.getClientData().getValue().getUser().getCardID(),clientViewModel.getClientData().getValue());
                clientViewModel.getClientData().getValue().doAction(clientViewModel.getClientData().getValue().getUser().getCardID());
                } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    clientViewModel.getClientData().getValue().writeToServer("GameBoardUI|cardDrawn|" + clientViewModel.getClientData().getValue().getUser().getCardID()
                            + "|" + clientViewModel.getClientData().getValue().getUser().getUsername());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        handler.postDelayed(runnable, 2000);*/
    }


    private void checkField() throws IOException {
        if (Board.getFieldName(clientViewModel.getClientData().getValue().getUser().getPosition()).equals("chance")) {
            returnChanceCard();
        } else if (Board.getFieldName(clientViewModel.getClientData().getValue().getUser().getPosition()).equals("community")) {
            returnCommunityChestCard();
        }
    }

    private void returnChanceCard() throws IOException {
        int index = chanceCards.drawFromDeck().getId();
        int cardId = chanceCards.getChanceCardDeck().get(index).getImageId();

        if(cardId == R.drawable.chance4 || cardId == R.drawable.chance0 || cardId == R.drawable.chance1){
            cardId = R.drawable.chance14;
        }

        if (cardId == R.drawable.chance8 || cardId == R.drawable.chance2 || cardId == R.drawable.chance3){
            cardId = R.drawable.chance17;
        }

        if (cardId == R.drawable.chance10 || cardId == R.drawable.chance7 || cardId == R.drawable.chance9){
            cardId = R.drawable.chance3;
        }

        if (cardId == R.drawable.chance11 || cardId == R.drawable.chance13 || cardId == R.drawable.chance15){
            cardId = R.drawable.chance5;
        }
        if (cardId == R.drawable.chance16 || cardId == R.drawable.chance18 || cardId == R.drawable.chance19){
            cardId = R.drawable.chance6;
        }

        binding.ImageCard.setImageResource(cardId);
        clientViewModel.getClientData().getValue().getUser().setCardID(cardId);
    }

    private void returnCommunityChestCard() throws IOException {
        int index = communityCards.drawFromDeck().getId();
        int cardId = communityCards.getCommunityChestCardDeck().get(index).getImageId();

        if (cardId == R.drawable.community0){
            cardId = R.drawable.community3;
        }

        if (cardId == R.drawable.community8){
            cardId = R.drawable.community2;
        }
        if (cardId == R.drawable.community13){
            cardId = R.drawable.community7;
        }
        if (cardId == R.drawable.community19){
            cardId = R.drawable.community11;
        }
        if (cardId == R.drawable.community5){
            cardId = R.drawable.community15;
        }
        binding.ImageCard.setImageResource(cardId);
        clientViewModel.getClientData().getValue().getUser().setCardID(cardId);
    }

    public static Context getAppContext() {
        return context;
    }
}
