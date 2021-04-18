package com.example.rankingu.ui.Materia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;

public class ReseñaActivity extends AppCompatActivity {

    private Button BtonDejar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atributos_calificar);

        BtonDejar = findViewById(R.id.boton_dejar_reseña);

        BtonDejar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReseñaActivity.this, GraciasActivity.class);
                startActivity(intent);
            }
        });
    }
}
