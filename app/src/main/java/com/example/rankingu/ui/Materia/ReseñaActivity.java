package com.example.rankingu.ui.Materia;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;
import com.example.rankingu.Classes.Horario;
import com.example.rankingu.Classes.Materia;
import com.example.rankingu.R;
import com.example.rankingu.ui.Materia.MateriaActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.ui.Enroll.ConflictActivity;
import com.example.rankingu.ui.Enroll.EnrollActivity;

public class ReseñaActivity extends AppCompatActivity {

    private Button BtonDejar;
    private RatingBar ratingStar1;
    private RatingBar ratingStar2;
    private RatingBar ratingStar3;
    private RatingBar ratingStar4;
    private float result = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atributos_calificar);

        BtonDejar = findViewById(R.id.boton_dejar_reseña);
        ratingStar1 = findViewById(R.id.ratingBar_metodologia);
        ratingStar2 = findViewById(R.id.ratingBar_puntualidad);
        ratingStar3 = findViewById(R.id.ratingBar_actitud);
        ratingStar4 = findViewById(R.id.ratingBar_organizado);

       /* ratingStar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                result = result + ratingBar.getRating();
            }
        });
        ratingStar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                result = result + ratingBar.getRating();
            }
        });
        ratingStar3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                result = result + ratingBar.getRating();
            }
        });
        ratingStar4.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                result = result + ratingBar.getRating();
            }
        });*/


        BtonDejar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            boolean x = false;
            if(x){
                Intent intent = new Intent(ReseñaActivity.this, ConflictActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            else{
                result = (ratingStar1.getRating()+ratingStar2.getRating()+ratingStar3.getRating()+ratingStar4.getRating())/4;

                AlertDialog.Builder alerta = new AlertDialog.Builder(ReseñaActivity.this);
                alerta.setMessage("La reseña se subio exitosamente "  )
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog exito = alerta.create();
                exito.setTitle("");
                exito.show();
            }
            }
        });




    }




}
