package com.example.rankingu.ui.Materia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.Classes.Profesor;
import com.example.rankingu.R;
import com.example.rankingu.ui.Enroll.EnrollActivity;
import com.example.rankingu.ui.Search.Search_Materia;

public class MateriaActivity extends AppCompatActivity {

    private Button rating;
    private Button inscribir;
    private TextView materia, docente, ratingV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);

        rating = findViewById(R.id.boton_inscribir_materia2);
        inscribir = findViewById(R.id.boton_inscribir_materia);
        materia = findViewById(R.id.textView21);
        docente = findViewById(R.id.textView22);
        ratingV = findViewById(R.id.textView23);

        Bundle myBundle = this.getIntent().getExtras();
        Profesor x = (Profesor)myBundle.getSerializable("materia");

        materia.setText(x.getMateriasList().get(0).getNombre());
        docente.setText(x.getMateriasList().get(0).getProfesores());
        ratingV.setText(String.valueOf(x.getMateriasList().get(0).getPuntaje()));

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
