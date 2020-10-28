package com.demo.grillyapp;

import android.app.Application;

import com.demo.grillyapp.api.models.response.Restaurant;
import com.demo.grillyapp.fragments.MealsFragment;

import java.util.List;

public class AppData extends Application {

    public static Restaurant restaurant;
    public static List<Restaurant> restaurants;
    public static int state;

    public static MealsFragment mealsFragment;
}
