package com.example.rankingu.ui.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.rankingu.MainActivity;
import com.example.rankingu.R;

public class SignUp_Estudiante extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__estudiante);
    }

    public void changeToMain(View view)
    {
        Intent mainScreen = new Intent(this, MainActivity.class);
        startActivity(mainScreen);
    }
}
