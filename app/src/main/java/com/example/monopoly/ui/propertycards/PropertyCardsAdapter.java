package com.example.monopoly.ui.propertycards;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monopoly.R;
import com.example.monopoly.gamelogic.properties.ClientPropertyStorage;


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
        holder.propertyCard.setImageResource(cps.getPropertyList().get(position).getImageId());
    }

    @Override
    public int getItemCount() {
        return cps.getPropertyList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView propertyCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            propertyCard = itemView.findViewById(R.id.propertyCardImageView);
        }
        public ImageView getPropertyCard() {
            return propertyCard;
        }
    }
}
