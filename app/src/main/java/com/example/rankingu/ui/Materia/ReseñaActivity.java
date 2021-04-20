package com.example.rankingu.ui.Materia;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.Classes.Profesor;
import com.example.rankingu.R;
import com.example.rankingu.ui.Enroll.ConflictActivity;
import com.example.rankingu.ui.Enroll.EnrollActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReseñaActivity extends AppCompatActivity {

    private Button BtonDejar;

    private EditText comentarios;
    private RatingBar ratingStar1;
    private RatingBar ratingStar2;
    private RatingBar ratingStar3;
    private RatingBar ratingStar4;
    private float result = 0;
    private TextView materia, docente;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atributos_calificar);

        BtonDejar = findViewById(R.id.boton_dejar_reseña);
        ratingStar1 = findViewById(R.id.ratingBar_metodologia);
        ratingStar2 = findViewById(R.id.ratingBar_puntualidad);
        ratingStar3 = findViewById(R.id.ratingBar_actitud);
        ratingStar4 = findViewById(R.id.ratingBar_organizado);
        comentarios = (EditText)findViewById(R.id.Comentarios_reseña);
        materia = findViewById(R.id.materia_calificar_atributos);
        docente = findViewById(R.id.docente_calificar_atributos);

        Bundle myBundleRecibir = this.getIntent().getExtras();
        final Profesor x = (Profesor) myBundleRecibir.getSerializable("materia");

        materia.setText(x.getMateriasList().get(0).getNombre());
        docente.setText(x.getMateriasList().get(0).getProfesores());

        BtonDejar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //Registrar materia
            boolean y = false;
            if(y){
                Intent intent = new Intent(ReseñaActivity.this, ConflictActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            else{
                    result = (ratingStar1.getRating() + ratingStar2.getRating() +ratingStar3.getRating() + ratingStar4.getRating())/4;
                    String coment = comentarios.getText().toString();

                    if (coment.length() == 0){
                        //Toast.makeText(this, "Debes agregar algun comentario", Toast.LENGTH_LONG).show();
                    }else{
                        Intent myIntent = new Intent(ReseñaActivity.this, GraciasActivity.class);
                        Bundle mybundle = new Bundle();

                        mybundle.putString("comentario",coment);
                        mybundle.putFloat("calificacion", result);
                        mybundle.putSerializable("materia",x);

                        myIntent.putExtras(mybundle);
                        startActivity(myIntent);
                    }


                    /*AlertDialog.Builder alerta = new AlertDialog.Builder(ReseñaActivity.this);
                     alerta.setMessage("La reseña se subio exitosamente " + (result) ).setCancelable(false).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                AlertDialog exito = alerta.create();
                exito.setTitle("");
                exito.show();*/
            }
            }
        });




    }




}
