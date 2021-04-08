package com.example.rankingu.ui;

<<<<<<< Updated upstream
import androidx.appcompat.app.AppCompatActivity;

=======
>>>>>>> Stashed changes
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

<<<<<<< Updated upstream
=======
import androidx.appcompat.app.AppCompatActivity;

>>>>>>> Stashed changes
import com.example.rankingu.R;

public class SignUp_Estudiante extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__estudiante);
    }

    public void changeToMain(View view)
    {
        Intent mainScreen = new Intent(this,mainScreen.class);
        startActivity(mainScreen);
    }
}
