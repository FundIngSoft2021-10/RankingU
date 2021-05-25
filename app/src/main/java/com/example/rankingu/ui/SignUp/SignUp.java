package com.example.rankingu.ui.SignUp;

import android.app.ProgressDialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.Classes.Usuario;
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

    private static final String TAG = "EmailPassword";
    private Spinner spRol;
    private EditText txtMail;
    private EditText txtUser;
    private EditText txtPassword;
    private EditText txtPassConfirm;
    private ProgressDialog progressDialog;
    private Button next;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

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
        progressDialog = new ProgressDialog(this);


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

                    //Crear el usuario en la bd
                    Usuario usu = new Usuario();
                    usu.setCorreo(txtMail.getText().toString());
                    usu.setUsuario(txtUser.getText().toString());
                    usu.setCarrera("Sistemas");
                    usu.setFoto("https://images.vexels.com/media/users/3/147102/isolated/preview/082213cb0f9eabb7e6715f59ef7d322a-icono-de-perfil-de-instagram-by-vexels.png");
                    usu.setTipo(spRol.getSelectedItem().toString());



                    //Agrega a la BD
                    db.collection("Usuarios").document(txtMail.getText().toString()).set(usu);


                    crearUsuario(txtMail.getText().toString(),txtPassword.getText().toString());




                    /*user = FirebaseAuth.getInstance().getCurrentUser();
                    user.sendEmailVerification();*/

                    //Ir a la pantalla dependiendo el tipo
                    if (spRol.getSelectedItem().toString().compareTo("Estudiante") == 0) {
                        Intent signEstudiante = new Intent(SignUp.this, SignUp_Estudiante.class);
                        Bundle mybundle = new Bundle();
                        mybundle.putSerializable("usuario",usu);
                        signEstudiante.putExtras(mybundle);
                        startActivity(signEstudiante);
                    } else if (spRol.getSelectedItem().toString().compareTo("Profesor") == 0) {
                        Intent signProfesor = new Intent(SignUp.this, SignUp_Profesor.class);
                        startActivity(signProfesor);
                    }


                }



            }
        });


    }

    private void crearUsuario(String email, String password){
        progressDialog.setMessage("Realizando registro");
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                            sendEmail();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    private void sendEmail() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = auth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Email sent
                    }
                });
        // [END send_email_verification]
    }


    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    private void reload() { }

    private void updateUI(FirebaseUser user) {

    }


}
