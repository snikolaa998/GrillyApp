package com.demo.grillyapp.api;

import com.demo.grillyapp.api.models.request.LoginCredentials;
import com.demo.grillyapp.api.models.request.RegisterCredentials;
import com.demo.grillyapp.api.models.response.Meal;
import com.demo.grillyapp.api.models.response.Restaurant;
import com.google.gson.JsonElement;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UtilsApi {

    @POST("register")
    Call<JsonElement> registerUser(@Body RegisterCredentials data);

    @POST("login")
    Call<JsonElement> loginUser(@Body LoginCredentials data);

    @GET("restaurants")
    Call<ArrayList<Restaurant>> getRestaurants();

    @GET("restaurants/{id}/meals")
    Call<ArrayList<Meal>> getMeals(@Path("id") String id);

}
