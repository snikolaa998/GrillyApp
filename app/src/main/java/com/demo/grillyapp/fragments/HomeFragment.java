package com.demo.grillyapp.fragments;

import android.os.Bundle;

import androidx.annotation.LongDef;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.demo.grillyapp.AppData;
import com.demo.grillyapp.R;
import com.demo.grillyapp.Utils;
import com.demo.grillyapp.adapters.RestaurantAdapter;
import com.demo.grillyapp.api.RetrofitClient;
import com.demo.grillyapp.api.UtilsApi;
import com.demo.grillyapp.api.models.response.Restaurant;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView restaurantRecycler;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        restaurantRecycler = view.findViewById(R.id.restaurantRecycler);

        getRestaurants();

        return view;
    }

    private void getRestaurants(){
            UtilsApi api = RetrofitClient.getRetrofitClient().create(UtilsApi.class);
            Call<ArrayList<Restaurant>> call = api.getRestaurants();
            call.enqueue(new Callback<ArrayList<Restaurant>>() {
                @Override
                public void onResponse(Call<ArrayList<Restaurant>> call, Response<ArrayList<Restaurant>> response) {
                    Log.d("HomeFragment", "Status code: " + response.code());

                    if(response.isSuccessful()){
                        ArrayList<Restaurant> restaurants = response.body();
                        Log.d("HomeFragment", new Gson().toJson(restaurants));
                        AppData.restaurants = restaurants;
                        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                        restaurantRecycler.setLayoutManager(layoutManager);
                        RestaurantAdapter adapter = new RestaurantAdapter(restaurants, getContext());
                        restaurantRecycler.setAdapter(adapter);
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<Restaurant>> call, Throwable t) {

                }
            });


    }
}