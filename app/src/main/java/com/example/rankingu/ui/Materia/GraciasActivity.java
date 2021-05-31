package com.example.rankingu.ui.Materia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.Classes.Materia;
import com.example.rankingu.Classes.Profesor;
import com.example.rankingu.Classes.Rating;
import com.example.rankingu.Classes.Reseña;
import com.example.rankingu.Classes.SesionClase;
import com.example.rankingu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class GraciasActivity extends AppCompatActivity {

    private TextView mensaje;
    private RatingBar ratingStar;
    private TextView materia;
    private Button volverPrincipio;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resena);

        mensaje = (TextView) findViewById(R.id.Reseña_final);
        ratingStar = findViewById(R.id.ratingBar_reseña_final);
        materia = findViewById(R.id.materia_activity_reseña);
        volverPrincipio = (Button) findViewById(R.id.boton_volver_info_materia);

        Bundle myBundle = this.getIntent().getExtras();

        Profesor x = (Profesor) myBundle.getSerializable("materia");
        materia.setText(x.getMateriasList().get(0).getNombre());
        Float val = (Float) myBundle.getSerializable("num");

        if(val == 0){

            String coment = myBundle.getString("comentario");
            float calificacion = myBundle.getFloat("calificacion");

            subirReseña(x, coment, calificacion);


            mensaje.setText(coment);
            ratingStar.setRating(calificacion);
        }else if(val == 1){
            Reseña rese = (Reseña) myBundle.getSerializable("usuari");


            ratingStar.setRating(rese.getRating());
            mensaje.setText(rese.getReseña());
        }






        volverPrincipio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GraciasActivity.this, MateriaActivity.class);
                startActivity(intent);
            }
        });


    }

    public void subirReseña(final Profesor profe, final String coment, final float calif){

        db.collection("Usuarios").document(user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    agregarReseña(task.getResult().getData().get("usuario").toString(), profe.getMateriasList().get(0).getNombre(),profe.getNombre(), coment, calif);

                } else {
                    Log.d(TAG, "Error en la BD: ", task.getException());
                }
            }
        });

    }

    public void agregarReseña(String usuario, String mat, String prof, String comen, float cal){

        Reseña rese = new Reseña(usuario, comen, cal);
        db.collection("Materias").document(mat).collection("profesores").document(prof).collection("ratings").document(usuario).set(rese);
        actualizarRatingTotal(mat,prof);

    }

    public void actualizarRatingTotal(final String mat, final String prof){

        db.collection("Materias").document(mat).collection("profesores").document(prof).collection("ratings").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    float total = 0;
                    float ndoc = 0;
                    String aux;

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ndoc += 1;
                        aux = String.valueOf(document.getData().get("rating"));
                        total += Float.parseFloat(aux);
                    }
                    total = total/ndoc;
                    Rating rat = new Rating();
                    rat.setRating(total);
                    rat.setNombre(prof);
                    db.collection("Materias").document(mat).collection("profesores").document(prof).set(rat);

                } else {
                    Log.d(TAG, "Error en la BD: ", task.getException());
                }

            }

        })
        ;

    }

}
