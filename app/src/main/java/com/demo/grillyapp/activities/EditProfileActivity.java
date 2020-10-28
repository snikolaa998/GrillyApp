package com.demo.grillyapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.demo.grillyapp.PreferencesUtils;
import com.demo.grillyapp.R;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private Uri imageUri;
    private CircleImageView profileImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ImageView addPhotoButton = findViewById(R.id.btnAddPhoto);
        ImageView backButton = findViewById(R.id.backButton);
        profileImageView = findViewById(R.id.editProfileImage);
        Button saveButton = findViewById(R.id.btnSave);

        EditText nameInput = findViewById(R.id.nameInput);
        EditText streetInput = findViewById(R.id.streetInput);
        EditText phoneInput = findViewById(R.id.phoneInput);

        String imageUriString = PreferencesUtils.getUserImageString(this);

        String nameFromPrefs = PreferencesUtils.getUserName(this);
        String streetFromPrefs = PreferencesUtils.getUserStreet(this);
        String phoneFromPrefs = PreferencesUtils.getUserPhone(this);

        if(nameFromPrefs != null)
            nameInput.setText(nameFromPrefs);
        if(streetFromPrefs != null)
            streetInput.setText(streetFromPrefs);
        if(phoneFromPrefs != null)
            phoneInput.setText(phoneFromPrefs);

        if(imageUriString != null){
            Uri uri = Uri.parse(imageUriString);
            profileImageView.setImageURI(uri);
        }

        backButton.setOnClickListener(view -> finish());
        addPhotoButton.setOnClickListener(view -> CropImage.activity().start(this));

        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String street = streetInput.getText().toString();
            String phone = phoneInput.getText().toString();

            PreferencesUtils.saveUserData(this, name, street, phone);
            PreferencesUtils.saveUserImage(this, imageUri);

            finish();
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == Activity.RESULT_OK){
                assert result != null;
                imageUri = result.getUri();
                profileImageView.setImageURI(imageUri);
            }
        }
    }
}