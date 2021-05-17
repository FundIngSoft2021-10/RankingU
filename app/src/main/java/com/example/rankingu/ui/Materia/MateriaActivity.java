package com.example.rankingu.ui.Materia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.Classes.Materia;
import com.example.rankingu.Classes.Profesor;
import com.example.rankingu.Classes.SesionClase;
import com.example.rankingu.R;
import com.example.rankingu.ui.Enroll.EnrollActivity;
import com.example.rankingu.ui.Search.Search_Materia;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MateriaActivity extends AppCompatActivity {

    private Button rating;
    private Button inscribir;
    private TextView materia, docente, ratingV,seleccion;
    private String mater, profe;
    private Spinner horario;
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
        //seleccion = findViewById(R.id.horario_spinner);

        Bundle myBundle = this.getIntent().getExtras();
        final Profesor x = (Profesor) myBundle.getSerializable("materia");

        materia.setText(x.getMateriasList().get(0).getNombre());
        docente.setText(x.getMateriasList().get(0).getProfesores());
        ratingV.setText(String.valueOf(x.getMateriasList().get(0).getPuntaje()));

        SesionClase aux = new SesionClase();
        sesiones.add(aux);
        consultarHorarios(x.getMateriasList().get(0).getNombre(),sesiones,x.getMateriasList().get(0).getProfesores());
        adapatar = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sesiones);
        horario.setAdapter(adapatar);

        /*if(horario.getSelectedItem().toString() != "") {
            final String escogido = horario.getSelectedItem().toString();
            seleccion.setText(horario.getSelectedItem().toString());
        }*/

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
                System.out.println("entro");

                if (task.isSuccessful()) {
                    System.out.println("si tuvo resultados" + task.getResult().size() + " " + task.getResult().toString());

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
}
