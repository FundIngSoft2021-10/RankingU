package com.example.rankingu.ui;

<<<<<<< Updated upstream
import androidx.appcompat.app.AppCompatActivity;

=======
>>>>>>> Stashed changes
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

<<<<<<< Updated upstream
=======
import androidx.appcompat.app.AppCompatActivity;

>>>>>>> Stashed changes
import com.example.rankingu.R;

public class SignUp_Profesor_AgregarMateriasSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__profesor__agregar_materias_success);
    }

    public void changeToAgregarMaterias(View view)
    {
<<<<<<< Updated upstream
        Intent addMaterias = new Intent(this,SignUp_Profesor_AgregarMaterias.class);
=======
        Intent addMaterias = new Intent(this, SignUp_Profesor_AgregarMaterias.class);
>>>>>>> Stashed changes
        startActivity(addMaterias);
    }

}
