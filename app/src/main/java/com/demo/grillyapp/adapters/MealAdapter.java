package com.demo.grillyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.grillyapp.R;
import com.demo.grillyapp.api.models.response.Meal;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private List<Meal> meals;
    private Context context;

    public MealAdapter(List<Meal> meals) {
        this.meals = meals;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        context = parent.getContext();
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);

        Glide.with(context).load(meal.getImage()).into(holder.mealImage);
        holder.mealName.setText(meal.getName());
        String price = "Price: " + meal.getPrice() + "$";
        holder.mealPrice.setText(price);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class MealViewHolder extends RecyclerView.ViewHolder{

        ImageView mealImage;
        TextView mealName;
        TextView mealPrice;

        MealViewHolder(@NonNull View itemView) {
            super(itemView);

            mealImage = itemView.findViewById(R.id.mealImage);
            mealName = itemView.findViewById(R.id.mealName);
            mealPrice = itemView.findViewById(R.id.mealPrice);
        }
    }
}
