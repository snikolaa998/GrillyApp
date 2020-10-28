package com.demo.grillyapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.grillyapp.AppData;
import com.demo.grillyapp.R;
import com.demo.grillyapp.activities.MapActivity;
import com.demo.grillyapp.api.models.response.Restaurant;


public class RestaurantFragment extends Fragment {

    public RestaurantFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_restaurant, container, false);

        ConstraintLayout menuButton = view.findViewById(R.id.menuButton);
        ImageView restaurantImage = view.findViewById(R.id.restaurantImage);
        ImageView locationImageView = view.findViewById(R.id.restaurantLocationImage);
        TextView locationTextView = view.findViewById(R.id.restaurantLocationText);
        ImageView phoneImageView = view.findViewById(R.id.restaurantPhoneImage);
        TextView phoneTextView = view.findViewById(R.id.restaurantPhoneText);


        Restaurant restaurant = AppData.restaurant;

        Glide.with(this).load(restaurant.getImage()).into(restaurantImage);
        locationTextView.setText(restaurant.getLocation());
        phoneTextView.setText(restaurant.getPhone());

        locationImageView.setOnClickListener(view1 -> startMapActivity());
        locationTextView.setOnClickListener(view1 -> startMapActivity());

        phoneImageView.setOnClickListener(view1 -> startDialIntent(restaurant.getPhone()));
        phoneTextView.setOnClickListener(view1 -> startDialIntent(restaurant.getPhone()));

        menuButton.setOnClickListener(view1 -> {
            MealsFragment mealsFragment = new MealsFragment();
            AppData.mealsFragment = mealsFragment;
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.animation_enter, R.anim.animation_exit);
            transaction.add(R.id.restaurantContainer, mealsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }

    private void startDialIntent(String number){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    private void startMapActivity(){
        Intent intent = new Intent(getActivity(), MapActivity.class);
        AppData.state = 1;
        getActivity().startActivity(intent);
    }
}