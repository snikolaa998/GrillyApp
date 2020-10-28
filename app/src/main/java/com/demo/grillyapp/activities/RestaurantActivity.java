package com.demo.grillyapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.grillyapp.AppData;
import com.demo.grillyapp.R;
import com.demo.grillyapp.api.models.response.Restaurant;
import com.demo.grillyapp.fragments.MealsFragment;
import com.demo.grillyapp.fragments.RestaurantFragment;
import com.google.gson.Gson;

public class RestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        TextView titleView = findViewById(R.id.restaurantTitle);
        ImageView backButton = findViewById(R.id.backButton);

        Log.d("RestaurantActivity", "Restaurant: " + new Gson().toJson(AppData.restaurant));

        Restaurant restaurant = AppData.restaurant;
        titleView.setText(restaurant.getName());

        getSupportFragmentManager().beginTransaction().add(R.id.restaurantContainer, new RestaurantFragment()).commit();


        backButton.setOnClickListener(view -> {
            removeMealsFragment();
            super.onBackPressed();
        });

    }

    private void removeMealsFragment(){
        if(AppData.mealsFragment != null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.animation_enter, R.anim.animation_exit);
            transaction.remove(AppData.mealsFragment);
            transaction.commit();
            AppData.mealsFragment = null;
        }
    }

    @Override
    public void onBackPressed() {
        removeMealsFragment();
        super.onBackPressed();
    }
}