package com.demo.grillyapp;

import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;

public class Utils {

    public static boolean isPasswordHidden = true;

    public Utils() {}

    public static void showPasswordValue(EditText input, ImageView icon){
        input.setTransformationMethod(null);
        isPasswordHidden = false;
        input.setSelection(input.getText().length());
        icon.setImageResource(R.drawable.ic_password_invisible);
    }

    public static void hidePasswordValue(EditText input, ImageView icon){
        input.setTransformationMethod(new PasswordTransformationMethod());
        isPasswordHidden = true;
        input.setSelection(input.getText().length());
        icon.setImageResource(R.drawable.ic_password_visible);
    }


}
