package com.example.rankingu.ui.Materia;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;

public class GraciasActivity extends AppCompatActivity {

    private TextView mensaje;
    private RatingBar ratingStar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resena);

        mensaje = (TextView) findViewById(R.id.Reseña_final);
        ratingStar = findViewById(R.id.ratingBar_reseña_final);

        Bundle myBundle = this.getIntent().getExtras();

        String coment = myBundle.getString("comentario");
        float calificacion = myBundle.getFloat("calificacion");


        mensaje.setText(coment);
        ratingStar.setRating(calificacion);


    }

}
