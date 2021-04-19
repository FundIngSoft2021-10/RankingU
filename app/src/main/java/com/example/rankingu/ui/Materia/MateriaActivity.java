package com.example.rankingu.ui.Materia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.Classes.Usuario;
import com.example.rankingu.R;
import com.example.rankingu.ui.Enroll.EnrollActivity;
import com.example.rankingu.ui.Search.Search_Materia;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MateriaActivity extends AppCompatActivity {

    private Button rating;
    private Button inscribir;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);

        rating = findViewById(R.id.boton_inscribir_materia2);
        inscribir = findViewById(R.id.boton_inscribir_materia);

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MateriaActivity.this, WarningActivity.class);
                startActivity(intent);
            }
        });

        inscribir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MateriaActivity.this, EnrollActivity.class);
                startActivity(intent);
            }
        });
    }



}
