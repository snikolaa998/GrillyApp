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
import com.demo.grillyapp.api.models.request.LoginCredentials;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private ImageView showPasswordButton;
    private TextView signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    @Override
    protected void onResume() {
        super.onResume();

        showPasswordButton.setOnClickListener(view -> {
            if (Utils.isPasswordHidden)
                Utils.showPasswordValue(passwordInput, showPasswordButton);
            else
                Utils.hidePasswordValue(passwordInput, showPasswordButton);
        });

        loginButton.setOnClickListener(view -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            if(!email.isEmpty() && !password.isEmpty()){
                Log.d("LoginActivity", "Login button onClick");
                loginRequest(email, password);
            }else{
                Toast.makeText(this, R.string.input_failed, Toast.LENGTH_SHORT).show();
            }

        });

        signUpButton.setOnClickListener(view -> startRegisterActivity());

    }

    private void loginRequest(String email, String password){
        Log.d("LoginActivity", "LoginActivity loginRequest");
        UtilsApi api = RetrofitClient.getRetrofitClient().create(UtilsApi.class);
        Call<JsonElement> call = api.loginUser(new LoginCredentials(email, password));

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d("LoginActivity", "Login status code: " + response.code());
                if(response.isSuccessful()){
                    if(response.body() != null){
                        Log.d("LoginActivity", new Gson().toJson(response));
                        String status = response.body().getAsJsonObject().get("status").toString();
                        Log.d("LoginActivity", "Login status: " + status);

                        if(status.equals("1")){
                            startRestaurantActivity();
                            PreferencesUtils.saveUserStatus(LoginActivity.this);
                        }else {
                            Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

    private void startRestaurantActivity(){
        Intent restaurantIntent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(restaurantIntent);
        finish();
    }

    private void startRegisterActivity() {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
        finish();
    }

    private void init() {
        emailInput = findViewById(R.id.loginEmailInput);
        passwordInput = findViewById(R.id.loginPasswordInput);
        loginButton = findViewById(R.id.loginButton);
        showPasswordButton = findViewById(R.id.loginShowPassword);
        signUpButton = findViewById(R.id.loginSignUpButton);
    }
}