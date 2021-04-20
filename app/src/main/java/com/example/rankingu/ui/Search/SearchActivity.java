package com.example.rankingu.ui.Search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.Classes.Materia;
import com.example.rankingu.Classes.Profesor;
import com.example.rankingu.Classes.SesionClase;
import com.example.rankingu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SearchActivity extends AppCompatActivity {

    private Spinner spinner1;
    private EditText et1;
    private TextView tv1;
    private Button btnBuscar;
    private ArrayList<Profesor> profesores = new ArrayList<>();
    private ArrayList<String> resultados = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        et1 = (EditText) findViewById(R.id.editTextBusqueda);
        tv1 = (TextView) findViewById(R.id.Buscarpor);
        spinner1 = (Spinner) findViewById(R.id.spinnerBuscar);
        btnBuscar = findViewById(R.id.BotonBuscar);

        String[] opciones = {"Profesor", "Materia", "Carrera"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        spinner1.setAdapter(adapter);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buscar();
            }
        });
    }

    //Click
    public void Buscar() {
        String valor1_String = et1.getText().toString();
        consultaMateria(valor1_String, profesores, resultados);

        String seleccion = spinner1.getSelectedItem().toString();
        if (seleccion.equals("Profesor")) {
            Intent searchProfesor = new Intent(this, Search_Profesor.class);
            startActivity(searchProfesor);
        } else if (seleccion.equals("Materia")) {
            Intent searchMateria = new Intent(this, Search_Materia.class);
            startActivity(searchMateria);
        } else if (seleccion.equals("Carrera")) {
            Intent searchCarrera = new Intent(this, Search_Materia_Carrera.class);
            startActivity(searchCarrera);
        }
    }

    //Consulta
    public void consultaMateria(final String materiae, final ArrayList<Profesor> opciones, final ArrayList<String> result) {
        Profesor opt;
        db.collection("Materias").document(materiae)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Materia aux = new Materia();
                            consultaProfes(materiae, opciones);
                            //Log.d(TAG, "xxxxYYYZZZZ" + opciones.size());
                            for (Profesor p : opciones) {
                                aux.setPuntaje(p.getMateriasList().get(0).getPuntaje());
                                p.getMateriasList().get(0).setDescripcion(task.getResult().getData().get("descripcion").toString());
                                p.getMateriasList().get(0).setSemestre(Integer.parseInt(task.getResult().getData().get("semestre").toString()));
                            }
                        } else {
                            Log.d(TAG, "Error en la BD: ", task.getException());
                        }
                    }
                });
    }

    //Consulta
    public void consultaProfes(final String materia, final ArrayList<Profesor> opciones) {
        db.collection("Materias").document(materia).collection("profesores")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Profesor profe = new Profesor();
                                Materia mat = new Materia();
                                ArrayList<SesionClase> listaSesion = (ArrayList<SesionClase>) document.getData().get("horarios");
                                profe.setNombre(document.getData().get("nombre").toString());
                                mat.setSesiones_clase(listaSesion);
                                mat.setNombre(materia);
                                mat.setPuntaje(Double.parseDouble(document.getData().get("rating").toString()));
                                profe.addMateria(mat);
                                opciones.add(profe);
                            }
                        } else {
                            Log.d(TAG, "Error en la BD: ", task.getException());
                        }
                    }
                });
    }
}
