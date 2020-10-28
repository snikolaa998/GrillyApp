package com.demo.grillyapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.grillyapp.PreferencesUtils;
import com.demo.grillyapp.R;
import com.demo.grillyapp.Utils;
import com.demo.grillyapp.api.RetrofitClient;
import com.demo.grillyapp.api.UtilsApi;
import com.demo.grillyapp.api.models.request.RegisterCredentials;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private Button registerButton;
    private ImageView showPasswordButton;
    private TextView signInButton;

    private boolean isPasswordHidden = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

    }

    @Override
    protected void onResume() {
        super.onResume();

        showPasswordButton.setOnClickListener(view -> {
            if(Utils.isPasswordHidden)
                Utils.showPasswordValue(passwordInput, showPasswordButton);
            else
                Utils.hidePasswordValue(passwordInput, showPasswordButton);
        });

        registerButton.setOnClickListener(view -> {
            String name = nameInput.getText().toString();
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                Log.d("RegisterActivity", "Register button");
                registerRequest(name, email, password);
            }else {
                Toast.makeText(RegisterActivity.this, R.string.input_failed, Toast.LENGTH_SHORT).show();
            }

        });
        signInButton.setOnClickListener(view -> startLoginActivity());
    }

    private void registerRequest(String name, String email, String password){
        Log.d("RegisterActivity", "RegisterActivity -- RegisterRequest");
        UtilsApi api = RetrofitClient.getRetrofitClient().create(UtilsApi.class);
        Call<JsonElement> call = api.registerUser(new RegisterCredentials(name, email, password));

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d("RegisterActivity", "Register status code: " + response.code());
                Log.d("RegisterActivity", "Register response: " + response.code());
                if(response.isSuccessful()){
                    if(response.body() != null){
                        String status = response.body().getAsJsonObject().get("status").toString();

                        if(status.equals("1")){
                            startRestaurantActivity();
                            PreferencesUtils.saveUserStatus(RegisterActivity.this);
                        }else {
                            Toast.makeText(RegisterActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(RegisterActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RegisterActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("RegisterActivity", "Register getLocalizedMessage: " + t.getLocalizedMessage());
                Log.d("RegisterActivity", "Register getMessage: " + t.getMessage());
            }
        });
    }

    private void startRestaurantActivity() {
        Intent restaurantIntent = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(restaurantIntent);
        finish();
    }

    private void startLoginActivity(){
        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private void init(){
        nameInput = findViewById(R.id.registerNameInput);
        emailInput = findViewById(R.id.registerEmailInput);
        passwordInput = findViewById(R.id.registerPasswordInput);
        registerButton = findViewById(R.id.registerSignUpButton);
        showPasswordButton = findViewById(R.id.registerShowPassword);
        signInButton = findViewById(R.id.registerSignInButton);
    }
}