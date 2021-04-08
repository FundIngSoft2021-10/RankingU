package com.example.rankingu.ui;

<<<<<<< Updated upstream
import androidx.appcompat.app.AppCompatActivity;

=======
>>>>>>> Stashed changes
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

<<<<<<< Updated upstream
=======
import androidx.appcompat.app.AppCompatActivity;

>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        Intent addMaterias = new Intent(this,SignUp_Profesor_AgregarMaterias.class);
=======
        Intent addMaterias = new Intent(this, SignUp_Profesor_AgregarMaterias.class);
>>>>>>> Stashed changes
        startActivity(addMaterias);
    }
}
