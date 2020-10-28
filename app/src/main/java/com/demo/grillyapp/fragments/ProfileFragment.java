package com.demo.grillyapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.grillyapp.PreferencesUtils;
import com.demo.grillyapp.R;
import com.demo.grillyapp.activities.EditProfileActivity;
import com.demo.grillyapp.activities.MainActivity;

public class ProfileFragment extends Fragment {

    ConstraintLayout editProfileButton;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);


        TextView logoutButton = view.findViewById(R.id.btnLogout);
        logoutButton.setOnClickListener(v -> logoutUser());

        editProfileButton = view.findViewById(R.id.btnEdit);
        editProfileButton.setOnClickListener(v -> startEditProfileActivity());

        return view;

    }

    private void logoutUser(){
        PreferencesUtils.removeUserStatus(getContext());
        startMainActivity();
        getActivity().finish();
    }

    private void startMainActivity(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
    }

    private void startEditProfileActivity(){
        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        getActivity().startActivity(intent);
    }
}