package com.example.rankingu.ui.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;
import com.example.rankingu.ui.Enroll.ConflictActivity;
import com.example.rankingu.ui.LoginActivity;
import com.example.rankingu.ui.Materia.GraciasActivity;
import com.example.rankingu.ui.Materia.ReseñaActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {

    private Spinner spRol;
    private EditText txtMail;
    private EditText txtUser;
    private EditText txtPassword;
    private EditText txtPassConfirm;
    private FirebaseAuth firebase;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private Button next;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        next = (Button) findViewById(R.id.buttonNext);
        spRol = (Spinner) findViewById(R.id.spinnerRol);
        txtMail = (EditText) findViewById(R.id.editTextEmail);
        txtUser = (EditText) findViewById(R.id.editTextBusqueda);
        txtPassword = (EditText) findViewById(R.id.editTextPassword);
        txtPassConfirm = (EditText) findViewById(R.id.editTextPasswordConfirm);
        String[] opcionesSpRol = {"Estudiante", "Profesor"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcionesSpRol);
        spRol.setAdapter(adapter);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtMail.getText().toString().isEmpty() || txtPassword.getText().toString().isEmpty() || txtPassConfirm.getText().toString().isEmpty() || txtUser.getText().toString().isEmpty()) {
                    Toast.makeText(SignUp.this,"Por favor llene todos los campos",Toast.LENGTH_LONG).show();
                }else if (!txtMail.getText().toString().contains("@javeriana.edu.co")) {
                        Toast.makeText(SignUp.this, "El correo debe contener @javeriana.edu.co", Toast.LENGTH_LONG).show();
                    } else if ((!txtPassword.getText().toString().equals(txtPassConfirm.getText().toString()))){
                            Toast.makeText(SignUp.this, "Las contraseñas deben ser iguales", Toast.LENGTH_LONG).show();
                        } else {

                            /// Se añade el usuario en la base de datos Usuarios
                            db.collection("Usuarios").document(txtMail.getText().toString());
                            //db.collection("Usuarios").add(txtMail.getText().toString());
                            //db.collection("Usuarios").document(txtMail.getText().toString()).set(txtUser);




                            ////Se crea en la base de datos
                            firebase = FirebaseAuth.getInstance();
                            firebase.createUserWithEmailAndPassword(txtMail.getText().toString(), txtPassword.getText().toString());

                            //Ir a la pantalla dependiendo el tipo
                            if (spRol.getSelectedItem().toString().compareTo("Estudiante") == 0) {
                                Intent signEstudiante = new Intent(SignUp.this, SignUp_Estudiante.class);
                                startActivity(signEstudiante);
                            } else if (spRol.getSelectedItem().toString().compareTo("Profesor") == 0) {
                                Intent signProfesor = new Intent(SignUp.this, SignUp_Profesor.class);
                                startActivity(signProfesor);
                            }


                        }



            }
        });


    }



  /*  public void changeToNext(View view) {
        if (!txtMail.getText().toString().isEmpty() && !txtPassword.getText().toString().isEmpty()) {

            ////Se crea en la base de datos
            firebase = FirebaseAuth.getInstance();
            firebase.createUserWithEmailAndPassword(txtMail.getText().toString(), txtPassword.getText().toString());

            //Intento iniciar de nuevo el usuario
            //firebase.signInWithEmailAndPassword(txtMail.getText().toString(), txtPassword.getText().toString());

            /*firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        //Toast.makeText(getApplicationContext(), user.getDisplayName(), Toast.LENGTH_LONG).show();
                        GoMainScreen();
                    }
                }
            };

            ///aqui esta el fallo
            /*user = firebase.getCurrentUser();
            if(user == null){
                System.out.println("usuario nulo");
            }else {
                user.sendEmailVerification();
            }
            System.out.println("peto");

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
    }*/


    private void updateUI(FirebaseUser user) {
    }



}
