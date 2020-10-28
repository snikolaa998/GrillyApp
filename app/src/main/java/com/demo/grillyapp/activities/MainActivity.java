package com.demo.grillyapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.demo.grillyapp.PreferencesUtils;
import com.demo.grillyapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        boolean isSigned = PreferencesUtils.isUserSigned(this);
        if(isSigned){
            startHomeActivity();
        }else {
            startLoginActivity();
        }


    }

    private void startLoginActivity(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void startHomeActivity(){
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}