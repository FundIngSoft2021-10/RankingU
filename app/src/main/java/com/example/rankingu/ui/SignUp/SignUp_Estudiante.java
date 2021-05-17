package com.example.rankingu.ui.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;
import com.example.rankingu.ui.Enroll.EnrollActivity;
import com.example.rankingu.ui.LoginActivity;
import com.example.rankingu.ui.Materia.MateriaActivity;

public class SignUp_Estudiante extends AppCompatActivity {

    private Button continuar;
    private EditText nombre;
    private EditText Apellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__estudiante);


        continuar = findViewById(R.id.Volver_login_estudiante);



        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp_Estudiante.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    /*//Volver al login hasta la confirmacion del correo
    public void returnLogin(View view)
    {

        Intent irMain = new Intent(this, LoginActivity.class);
        startActivity(irMain);
    }*/


}
