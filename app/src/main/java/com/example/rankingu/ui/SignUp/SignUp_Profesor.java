package com.example.rankingu.ui.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;

public class SignUp_Profesor extends AppCompatActivity {

    private TextView txtNombre;
    private TextView txtApellido;
    private CheckBox chkPolicies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__profesor);
        txtNombre= (TextView)findViewById(R.id.editTextNombre);
        txtApellido = (TextView)findViewById(R.id.editTextApellido);
        chkPolicies = (CheckBox) findViewById(R.id.checkBoxPolicies);
    }
    //Siguiente screen
    public void changeToAddMaterias(View view)
    {
        Intent addMaterias = new Intent(this,SignUp_Profesor_AgregarMaterias.class);
        startActivity(addMaterias);
    }
}
