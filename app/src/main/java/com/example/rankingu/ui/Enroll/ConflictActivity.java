package com.example.rankingu.ui.Enroll;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.Classes.Materia;
import com.example.rankingu.Classes.MateriaDelMain;
import com.example.rankingu.R;
import com.example.rankingu.ui.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ConflictActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Button confirmar, cancelar;
    private TextView materia1, materia2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_conflict);

        cancelar = findViewById(R.id.BtonConfirmar);
        confirmar = findViewById(R.id.BtonCancelar);

        materia1 =findViewById(R.id.textConflicto);
        materia2 = findViewById(R.id.textMaterias);

        Bundle myBundle = this.getIntent().getExtras();
        final Materia x = (Materia) myBundle.getSerializable("decidir");
        final Materia y = (Materia) myBundle.getSerializable("eliminar");

        materia1.setText("Actual: "+ y.getNombre());
        materia1.setText("A inscribir: "+ x.getNombre());

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarMateria(y,x);

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ConflictActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void registrarMateria(Materia x){
        //Añadir a la BD
        db.collection("Usuarios").document(user.getEmail()).collection("materias").add(x);
        Toast.makeText(this, "Materia inscrita", Toast.LENGTH_LONG).show();
    }

    public void eliminarMateria(Materia elim, final Materia x){
        //Sacar de la BD
        db.collection("Usuarios").document(user.getEmail()).collection("materias").document(elim.getNombre().toLowerCase())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ConflictActivity.this, "Materia desincrita", Toast.LENGTH_LONG).show();
                        registrarMateria(x);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ConflictActivity.this, "Vuelta a intentar", Toast.LENGTH_LONG).show();
                    }
                });
    }

}
