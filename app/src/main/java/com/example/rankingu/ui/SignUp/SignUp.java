package com.example.rankingu.ui.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private Spinner spRol;
    private EditText txtMail;
    private EditText txtUser;
    private EditText txtPassword;
    private EditText txtPassConfirm;
    private FirebaseAuth firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        spRol = (Spinner) findViewById(R.id.spinnerRol);
        txtMail = (EditText) findViewById(R.id.editTextEmail);
        txtUser = (EditText) findViewById(R.id.editTextBusqueda);
        txtPassword = (EditText) findViewById(R.id.editTextPassword);
        txtPassConfirm = (EditText) findViewById(R.id.editTextPasswordConfirm);
        String[] opcionesSpRol = {"Estudiante", "Profesor"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcionesSpRol);
        spRol.setAdapter(adapter);
    }

    public void changeToNext(View view) {
        if (!txtMail.getText().toString().isEmpty() && !txtPassword.getText().toString().isEmpty()) {
            if (spRol.getSelectedItem().toString().compareTo("Estudiante") == 0) {
                firebase.createUserWithEmailAndPassword(txtMail.getText().toString(), txtPassword.getText().toString());
                Intent signEstudiante = new Intent(this, SignUp_Estudiante.class);
                startActivity(signEstudiante);
            } else if (spRol.getSelectedItem().toString().compareTo("Profesor") == 0) {
                firebase.createUserWithEmailAndPassword(txtMail.getText().toString(), txtPassword.getText().toString());
                Intent signProfesor = new Intent(this, SignUp_Profesor.class);
                startActivity(signProfesor);
            }
        }
    }
}
