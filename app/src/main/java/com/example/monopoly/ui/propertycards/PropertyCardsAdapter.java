package com.example.monopoly.ui.propertycards;

import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monopoly.R;
import com.example.monopoly.gamelogic.Player;
import com.example.monopoly.gamelogic.properties.ClientPropertyStorage;
import com.example.monopoly.gamelogic.properties.Field;
import com.example.monopoly.gamelogic.properties.PropertyField;
import com.example.monopoly.network.Client;
import com.example.monopoly.ui.viewmodels.ClientViewModel;

import org.w3c.dom.Text;

import java.io.IOException;


public class PropertyCardsAdapter extends RecyclerView.Adapter<PropertyCardsAdapter.ViewHolder> {

    private static ClientPropertyStorage cps = ClientPropertyStorage.getInstance();
    private Player player;
    private Client client;

    public PropertyCardsAdapter(ClientViewModel clientViewModel) {
        this.player = clientViewModel.getClientData().getValue().getUser();
        this.client = clientViewModel.getClientData().getValue();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.property_card_element, parent, false);
        return new ViewHolder(view);
    }

    private boolean canBuyHouseOrHotel(PropertyField field){
        return player.equals(field.getOwner()) && cps.hasAllColours(player, ((PropertyField) field).getColor()) && player.getCapital() >= field.getRent().getPriceHouseOrHotel();
    }

    private void updateOnServer(String action, Field field) {
        try {
            client.writeToServer("PropertyCards|" + action + "|" + field.getName() + "|" + player.getUsername());
            client.writeToServer("PropertyCards|giveMoney|" + (-field.getPrice()) + "|" + player.getUsername());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Field field = cps.getPropertyList().get(position);
        holder.propertyCard.setImageResource(field.getImageId());
        if(field.getOwner() == null)
            holder.ownerName.setText("Owner: none");
        else
            holder.ownerName.setText("Owner: "+ field.getOwner().getUsername());

        if(field instanceof PropertyField) {
            if(((PropertyField) field).hasHotel()){
                holder.propertyHouses.setText("Hotel: 1");
            } else {
                holder.propertyHouses.setText("Houses: " + ((PropertyField) field).getNumOfHouses());
            }
        } else {
            holder.propertyHouses.setText("");
        }

        if(field instanceof PropertyField && canBuyHouseOrHotel((PropertyField) field)) {
            holder.buyHouseButton.setVisibility(View.VISIBLE);
            if(((PropertyField) field).getNumOfHouses() < 4) {
                holder.buyHouseButton.setText("Buy House");
                holder.buyHouseButton.setOnClickListener((view) -> {
                    ((PropertyField) field).addHouse();
                    onBindViewHolder(holder, position);
                    updateOnServer("addHouse", field);
                });
            } else if (!((PropertyField) field).hasHotel()){
                holder.buyHouseButton.setText("Buy Hotel");
                holder.buyHouseButton.setOnClickListener((view) -> {
                    ((PropertyField) field).addHotel();
                    onBindViewHolder(holder, position);
                    updateOnServer("addHotel", field);
                });
            } else {
                holder.buyHouseButton.setVisibility(View.GONE);
            }
        } else {
            holder.buyHouseButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return cps.getPropertyList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView propertyCard;
        private final TextView ownerName;
        private final TextView propertyHouses;
        private final Button buyHouseButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            propertyCard = itemView.findViewById(R.id.propertyCardImageView);
            ownerName = itemView.findViewById(R.id.ownerName);
            propertyHouses = itemView.findViewById(R.id.propertyHouses);
            buyHouseButton = itemView.findViewById(R.id.buyHouseBtn);
        }
    }
}
