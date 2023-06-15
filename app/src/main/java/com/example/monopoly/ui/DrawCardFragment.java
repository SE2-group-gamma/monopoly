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
        });
        return this.binding.getRoot();
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
        if(cardId == R.drawable.chance0){
            clientViewModel.getClientData().getValue().getUser().setIncrement(advanceTo("go"));
        }
        if(cardId == R.drawable.chance1){
            clientViewModel.getClientData().getValue().getUser().setIncrement(advanceTo("strandbad"));
        }
        if(cardId == R.drawable.chance2){
            clientViewModel.getClientData().getValue().getUser().setIncrement(advanceTo("lindwurm"));
        }
        if(cardId == R.drawable.chance4){
            cardId = R.drawable.chance14;
        }
        if (cardId == R.drawable.chance7){
            clientViewModel.getClientData().getValue().getUser().setIncrement(-3);
        }
        if (cardId == R.drawable.chance8){
            cardId = R.drawable.chance17;
        }
        if (cardId == R.drawable.chance7){
            clientViewModel.getClientData().getValue().getUser().setIncrement(advanceTo("s_bahn_wien"));
        }
        if (cardId == R.drawable.chance10){
            cardId = R.drawable.chance3;
        }
        if (cardId == R.drawable.chance11){
            clientViewModel.getClientData().getValue().getUser().setIncrement(advanceTo("city_arkaden"));
        }
        if(cardId == R.drawable.chance13 || cardId == R.drawable.chance16){
            clientViewModel.getClientData().getValue().getUser().setIncrement(advanceTo("jail"));
        }
        if(cardId == R.drawable.chance15){
            clientViewModel.getClientData().getValue().getUser().setIncrement(-1);
        }
        if(cardId == R.drawable.chance18){
            clientViewModel.getClientData().getValue().getUser().setIncrement(-1);
        }
        if (cardId == R.drawable.chance19){
            clientViewModel.getClientData().getValue().getUser().setIncrement(advanceTo("rathaus"));
        }
        binding.ImageCard.setImageResource(cardId);
        clientViewModel.getClientData().getValue().getUser().setCardID(cardId);
    }
    public int advanceTo(String location) throws IOException {
        int fieldId = 0;

        for (int i = 0; i < Board.FELDER_ANZAHL; i++) {
            if (Board.getFieldName(i).equals(location)) {
                fieldId = i;
            }
        }

        if (clientViewModel.getClientData().getValue().getUser().getPosition() > fieldId) {
            return (Board.FELDER_ANZAHL - clientViewModel.getClientData().getValue().getUser().getPosition() + fieldId);
        } else {
            return (fieldId - clientViewModel.getClientData().getValue().getUser().getPosition());
        }
    }

    private void returnCommunityChestCard() throws IOException {
        int index = communityCards.drawFromDeck().getId();
        int cardId = communityCards.getCommunityChestCardDeck().get(index).getImageId();

        if(cardId == R.drawable.community0){
            clientViewModel.getClientData().getValue().getUser().setIncrement(advanceTo("go"));
        }
        if(cardId == R.drawable.community5){
            clientViewModel.getClientData().getValue().getUser().setIncrement(advanceTo("jail"));
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
        binding.ImageCard.setImageResource(cardId);
        clientViewModel.getClientData().getValue().getUser().setCardID(cardId);
    }

    public static Context getAppContext() {
        return context;
    }
}
