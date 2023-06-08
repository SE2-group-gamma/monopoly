package com.example.monopoly.ui.propertycards;

import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monopoly.R;
import com.example.monopoly.gamelogic.properties.ClientPropertyStorage;
import com.example.monopoly.gamelogic.properties.Field;
import com.example.monopoly.gamelogic.properties.PropertyField;

import org.w3c.dom.Text;


public class PropertyCardsAdapter extends RecyclerView.Adapter<PropertyCardsAdapter.ViewHolder> {

    private static ClientPropertyStorage cps = ClientPropertyStorage.getInstance();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.property_card_element, parent, false);

        return new ViewHolder(view);
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
    }

    @Override
    public int getItemCount() {
        return cps.getPropertyList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView propertyCard;
        private final TextView ownerName;
        private final TextView propertyHouses;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            propertyCard = itemView.findViewById(R.id.propertyCardImageView);
            ownerName = itemView.findViewById(R.id.ownerName);
            propertyHouses = itemView.findViewById(R.id.propertyHouses);
        }
    }
}
