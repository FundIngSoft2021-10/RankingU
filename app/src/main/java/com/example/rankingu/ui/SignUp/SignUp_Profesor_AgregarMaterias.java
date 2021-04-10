package com.example.rankingu.ui.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ScrollView;

import com.example.rankingu.R;

public class SignUp_Profesor_AgregarMaterias extends AppCompatActivity {

    private AutoCompleteTextView autotxtMateria;
    private ScrollView scrollHorario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__profesor__agregar_materias);
        autotxtMateria = (AutoCompleteTextView) findViewById(R.id.autoCompleteNombreMateria);
        scrollHorario = (ScrollView)findViewById(R.id.scrollViewHorarioMateria);
    }

    public void changeToSuccess(View view)
    {
        Intent SignUpSuccess = new Intent(this, SignUp_Profesor_AgregarMateriasSuccess.class);
        startActivity(SignUpSuccess);
    }

}
