package com.demo.grillyapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.grillyapp.AppData;
import com.demo.grillyapp.R;
import com.demo.grillyapp.adapters.MealAdapter;
import com.demo.grillyapp.api.RetrofitClient;
import com.demo.grillyapp.api.UtilsApi;
import com.demo.grillyapp.api.models.response.Meal;
import com.demo.grillyapp.api.models.response.Restaurant;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsFragment extends Fragment {

    RecyclerView mealsRecycler;
    LinearLayoutManager layoutManager;

    public MealsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_meals, container, false);

        mealsRecycler = view.findViewById(R.id.mealsRecyclerView);
        Restaurant restaurant = AppData.restaurant;

        layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        getMeals(restaurant.getId());


        return view;
    }

    private void getMeals(String restaurantId){
        UtilsApi api = RetrofitClient.getRetrofitClient().create(UtilsApi.class);
        Call<ArrayList<Meal>> call = api.getMeals(restaurantId);
        call.enqueue(new Callback<ArrayList<Meal>>() {
            @Override
            public void onResponse(Call<ArrayList<Meal>> call, Response<ArrayList<Meal>> response) {
                if(response.isSuccessful()){
                    Log.d("MealsFragment", "Meals: " + new Gson().toJson(response.body()));
                    mealsRecycler.setLayoutManager(layoutManager);
                    MealAdapter adapter = new MealAdapter(response.body());
                    mealsRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Meal>> call, Throwable t) {
                Log.d("MealsFragment", "Get meals on failure: " + t.getMessage());
            }
        });
    }
}