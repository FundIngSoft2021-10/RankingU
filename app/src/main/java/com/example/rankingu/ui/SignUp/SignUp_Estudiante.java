package com.example.rankingu.ui.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.Classes.Profesor;
import com.example.rankingu.Classes.Usuario;
import com.example.rankingu.R;
import com.example.rankingu.ui.Enroll.EnrollActivity;
import com.example.rankingu.ui.LoginActivity;
import com.example.rankingu.ui.Materia.MateriaActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp_Estudiante extends AppCompatActivity {

    private Button continuar;
    private EditText nombre;
    private EditText apellido;
    private Spinner spinSemes;
    private Spinner spinCarrera;
    private CheckBox aceptar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__estudiante);

        nombre = (EditText) findViewById(R.id.editTextNombre);
        apellido = (EditText) findViewById(R.id.ingrese_apellido);
        aceptar = (CheckBox) findViewById(R.id.checkBoxPolicies);
        continuar = (Button) findViewById(R.id.Volver_login_estudiante);
        spinSemes = (Spinner) findViewById(R.id.spinnerSemestre);
        spinCarrera = (Spinner) findViewById(R.id.spinnerCarrera);

        String[] opcionesSpRol = {"1","2","3","4","5","6","7","8","9","10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcionesSpRol);
        spinSemes.setAdapter(adapter);

        String[] carrera = {"Sistemas"};
        ArrayAdapter<String> adapterC = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, carrera);
        spinCarrera.setAdapter(adapterC);

        Bundle myBundleRecibir = this.getIntent().getExtras();
        final Usuario usur = (Usuario) myBundleRecibir.getSerializable("usuario");



        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(nombre.getText().toString().isEmpty() || apellido.getText().toString().isEmpty()){
                    Toast.makeText(SignUp_Estudiante.this,"Por favor llene todos los campos",Toast.LENGTH_LONG).show();
                }else if(aceptar.isChecked() == false){
                    Toast.makeText(SignUp_Estudiante.this,"Por favor acepte las politicas",Toast.LENGTH_LONG).show();
                }else {
                    usur.setNombre(nombre.getText().toString());
                    usur.setApellido(apellido.getText().toString());
                    usur.setSemestre(spinSemes.getSelectedItem().toString());

                    //Agrega a la BD
                    db.collection("Usuarios").document(usur.getCorreo()).set(usur);

                    Intent intent = new Intent(SignUp_Estudiante.this, LoginActivity.class);
                    Toast.makeText(SignUp_Estudiante.this, "Se esperara a que verifique su correo", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
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
