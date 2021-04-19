package com.example.rankingu.ui.Materia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;
import com.example.rankingu.ui.Enroll.EnrollActivity;
import com.example.rankingu.ui.Search.Search_Materia;

public class MateriaActivity extends AppCompatActivity {

    private Button rating;
    private Button inscribir;

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
