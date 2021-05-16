package com.example.rankingu.ui.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;
import com.example.rankingu.ui.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private Spinner spRol;
    private EditText txtMail;
    private EditText txtUser;
    private EditText txtPassword;
    private EditText txtPassConfirm;
    private FirebaseAuth firebase;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

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

            ////Se crea en la base de datos
            firebase = FirebaseAuth.getInstance();
            firebase.createUserWithEmailAndPassword(txtMail.getText().toString(), txtPassword.getText().toString());

            //Intento iniciar de nuevo el usuario
            firebase.signInWithEmailAndPassword(txtMail.getText().toString(), txtPassword.getText().toString());

            ///aqui esta el fallo
            user = firebase.getCurrentUser();
            user.sendEmailVerification();


            ////Se cambia de pantalla si es estudiante o profesor
            if (spRol.getSelectedItem().toString().compareTo("Estudiante") == 0) {
                Intent signEstudiante = new Intent(this, SignUp_Estudiante.class);
                startActivity(signEstudiante);
            } else if (spRol.getSelectedItem().toString().compareTo("Profesor") == 0) {
                Intent signProfesor = new Intent(this, SignUp_Profesor.class);
                startActivity(signProfesor);
            }
        }else{
            Toast.makeText(SignUp.this,"Hubo un error al crear usuario",Toast.LENGTH_LONG).show();
        }
    }
    private void updateUI(FirebaseUser user) {
    }



}
