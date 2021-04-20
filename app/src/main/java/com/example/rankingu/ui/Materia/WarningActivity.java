package com.example.rankingu.ui.Materia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.Classes.Profesor;
import com.example.rankingu.R;

public class WarningActivity extends AppCompatActivity {

    private Button continuar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aviso_resena);

        Bundle myBundle = this.getIntent().getExtras();
        final Profesor x = (Profesor) myBundle.getSerializable("materia");

        continuar = findViewById(R.id.boton_continuar);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WarningActivity.this, ReseñaActivity.class);

                Bundle myBundle = new Bundle();
                myBundle.putSerializable("materia", x);
                intent.putExtras(myBundle);
                startActivity(intent);
            }
        });
    }



}
