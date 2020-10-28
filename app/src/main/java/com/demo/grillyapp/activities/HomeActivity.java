package com.demo.grillyapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.grillyapp.AppData;
import com.demo.grillyapp.R;
import com.demo.grillyapp.fragments.HomeFragment;
import com.demo.grillyapp.fragments.ProfileFragment;

public class HomeActivity extends AppCompatActivity {

    ConstraintLayout mapButton, placesButton, accountButton;
    ImageView mapIcon, placesIcon, accountIcon;
    TextView mapLabel, placesLabel, accountLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();

        startPlaces();

        placesButton.setOnClickListener(view -> startPlaces());
        mapButton.setOnClickListener(view -> startMap());
        accountButton.setOnClickListener(view -> startAccount());

    }

    private void init(){
        mapButton = findViewById(R.id.mapButton);
        placesButton = findViewById(R.id.placesButton);
        accountButton = findViewById(R.id.accountButton);
        mapIcon = findViewById(R.id.mapIcon);
        placesIcon = findViewById(R.id.placesIcon);
        accountIcon = findViewById(R.id.accountIcon);
        mapLabel = findViewById(R.id.mapLabel);
        placesLabel = findViewById(R.id.placesLabel);
        accountLabel = findViewById(R.id.accountLabel);
    }

    private void startPlaces(){
        getSupportFragmentManager().beginTransaction().replace(R.id.homeContainer, new HomeFragment()).commit();
        placesIcon.setImageResource(R.drawable.ic_places_active);
        placesLabel.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        mapIcon.setImageResource(R.drawable.ic_find_us);
        mapLabel.setTextColor(ContextCompat.getColor(this, R.color.gray));
        accountIcon.setImageResource(R.drawable.ic_account);
        accountLabel.setTextColor(ContextCompat.getColor(this, R.color.gray));
    }
    private void startMap(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }else {
                startMapActivity();
            }
        }else {
            startMapActivity();
        }
    }
    private void startAccount(){
        getSupportFragmentManager().beginTransaction().replace(R.id.homeContainer, new ProfileFragment()).commit();
        placesIcon.setImageResource(R.drawable.ic_places);
        placesLabel.setTextColor(ContextCompat.getColor(this, R.color.gray));
        mapIcon.setImageResource(R.drawable.ic_find_us);
        mapLabel.setTextColor(ContextCompat.getColor(this, R.color.gray));
        accountIcon.setImageResource(R.drawable.ic_account_active);
        accountLabel.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    private void startMapActivity(){
        Intent intent = new Intent(this, MapActivity.class);
        AppData.state = 0;
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            for(int i = 0; i < permissions.length; i++){
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if(permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)){
                    if(grantResult == PackageManager.PERMISSION_GRANTED){
                        startMapActivity();
                    }
                }
            }
        }
    }
}