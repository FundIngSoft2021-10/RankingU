package com.example.rankingu.ui.SignUpActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;

public class SignUp_Profesor_AgregarMateriasSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__profesor__agregar_materias_success);
    }

    public void changeToAgregarMaterias(View view)
    {
        Intent addMaterias = new Intent(this,SignUp_Profesor_AgregarMaterias.class);
        startActivity(addMaterias);
    }

}
