package com.demo.grillyapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.grillyapp.AppData;
import com.demo.grillyapp.R;
import com.demo.grillyapp.activities.RestaurantActivity;
import com.demo.grillyapp.api.models.response.Restaurant;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder> {

    private ArrayList<Restaurant> restaurantList;
    private Context context;

    public RestaurantAdapter(ArrayList<Restaurant> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.context = context;
    }

    @NonNull
    @Override
    public RestaurantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false);
        return new RestaurantHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);

        Glide.with(context).load(restaurant.getImage()).into(holder.itemImage);
        holder.itemName.setText(restaurant.getName());
        holder.itemLocation.setText(restaurant.getLocation());

        holder.itemConstraint.setOnClickListener(view -> {
            Intent intent = new Intent(context, RestaurantActivity.class);
            AppData.restaurant = restaurant;
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    class RestaurantHolder extends RecyclerView.ViewHolder{

        TextView itemName, itemLocation;
        ImageView itemImage;
        ConstraintLayout itemConstraint;

        public RestaurantHolder(@NonNull View view) {
            super(view);

            itemName = view.findViewById(R.id.itemName);
            itemLocation = view.findViewById(R.id.itemLocation);
            itemImage = view.findViewById(R.id.itemImage);
            itemConstraint = view.findViewById(R.id.itemConstraint);
        }
    }
}
