package com.example.rankingu.ui.Materia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.Classes.Materia;
import com.example.rankingu.Classes.Profesor;
import com.example.rankingu.Classes.Reseña;
import com.example.rankingu.Classes.SesionClase;
import com.example.rankingu.R;
import com.example.rankingu.ui.Enroll.EnrollActivity;
import com.example.rankingu.ui.Search.Search_Materia;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;

import java.io.Serializable;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MateriaActivity extends AppCompatActivity {

    private Button rating;
    private Button inscribir;
    private TextView materia, docente, ratingV;
    private Spinner horario;
    private ListView calificaciones;
    private ArrayList<Reseña> estrellas = new ArrayList<>();
    ArrayAdapter<Reseña> adapter;
    ArrayAdapter<SesionClase> adapatar;
    private ArrayList<SesionClase> sesiones= new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SesionClase horarioEnviar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);

        rating = findViewById(R.id.boton_inscribir_materia2);
        inscribir = findViewById(R.id.boton_inscribir_materia);
        materia = findViewById(R.id.textView21);
        docente = findViewById(R.id.textView22);
        ratingV = findViewById(R.id.textView23);
        horario = findViewById(R.id.spinner_horario);
        calificaciones = findViewById(R.id.Lista_rating);

        Bundle myBundle = this.getIntent().getExtras();
        final Profesor x = (Profesor) myBundle.getSerializable("materia");

        materia.setText(x.getMateriasList().get(0).getNombre());
        docente.setText(x.getMateriasList().get(0).getProfesores());
        ratingV.setText(String.valueOf(x.getMateriasList().get(0).getPuntaje()));

        SesionClase aux = new SesionClase();
        sesiones.add(aux);
        consultarHorarios(x.getMateriasList().get(0).getNombre(),sesiones,x.getNombre());
        adapatar = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sesiones);
        horario.setAdapter(adapatar);


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        consultarRatings(x.getMateriasList().get(0).getNombre(),x.getNombre(), adapter);

        calificaciones.setAdapter(adapter);

        calificaciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                float nume = 1;
                Reseña usu = (Reseña) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MateriaActivity.this, GraciasActivity.class);
                Bundle myBundle = new Bundle();
                myBundle.putSerializable("materia", x);
                myBundle.putSerializable("usuari",usu);
                myBundle.putFloat("num",nume);
                intent.putExtras(myBundle);
                startActivity(intent);

            }
        });




        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MateriaActivity.this, WarningActivity.class);

                Bundle myBundle = new Bundle();
                myBundle.putSerializable("materia", x);
                intent.putExtras(myBundle);

                startActivity(intent);
            }
        });

        //Selecciona horario
        horario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                horarioEnviar = (SesionClase) horario.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        inscribir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MateriaActivity.this, EnrollActivity.class);
                System.out.println(horarioEnviar.toString());
                Bundle myBundle = new Bundle();
                myBundle.putSerializable("materia", x);
                myBundle.putSerializable("horario", horarioEnviar);
                intent.putExtras(myBundle);

                startActivity(intent);
            }
        });
    }


    ///Consultar sesiones
    public void consultarHorarios(final String materia, final ArrayList<SesionClase> sesions, final String profesor){

        db.collection("Materias").document(materia).collection("profesores").document(profesor).collection("horarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        SesionClase ses = new SesionClase();
                        ses.setCupos(document.getData().get("cupos").toString());
                        ses.setDia(document.getData().get("dia").toString());
                        ses.sethFin(document.getData().get("hFin").toString());
                        ses.sethInicio(document.getData().get("hInicio").toString());
                        sesions.add(ses);
                    }
                } else {
                    Log.d(TAG, "Error en la BD: ", task.getException());
                }
            }
        });

    }
    ///Consultar estrellas
    public void consultarRatings(final String materia, final String profesor, final ArrayAdapter<Reseña> adapter){

        db.collection("Materias").document(materia).collection("profesores").document(profesor).collection("ratings").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    String aux;
                    Float aux2;

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Reseña rese = new Reseña();
                        aux = String.valueOf(document.getData().get("rating"));
                        rese.setRating(Float.parseFloat(aux));
                        rese.setReseña(document.getData().get("reseña").toString());
                        rese.setUsuario(document.getData().get("usuario").toString());
                        System.out.println(rese.toString());
                        adapter.add(rese);
                    }

                } else {
                    Log.d(TAG, "Error en la BD: ", task.getException());
                }
            }
        });


    }


}
