package com.example.rankingu.ui.Search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SearchView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.Classes.Materia;
import com.example.rankingu.Classes.Profesor;
import com.example.rankingu.Classes.SesionClase;
import com.example.rankingu.R;
import com.example.rankingu.ui.Materia.MateriaActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Search_Materia extends AppCompatActivity {

    private ListView listView;
    private String currentSearchText = "";
    private SearchView searchView;
    ArrayAdapter<Profesor> arrayAdaptar;
    private ArrayList<Profesor> profesores = new ArrayList<>();
    private ArrayList<String> resultados = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_materia);
        searchView = findViewById(R.id.materiaSearchView);
        listView = findViewById(R.id.materiaListView);

        Bundle myBundle = this.getIntent().getExtras();
        currentSearchText = (String)myBundle.getSerializable("palabra");

        consultaMateria(currentSearchText, profesores);
        arrayAdaptar = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, profesores);
        listView.setAdapter(arrayAdaptar);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Profesor x = (Profesor) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(Search_Materia.this, MateriaActivity.class);
                Bundle myBundle = new Bundle();
                myBundle.putSerializable("materia", x);
                intent.putExtras(myBundle);
                startActivity(intent);

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Search_Materia.this.arrayAdaptar.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String n) {
                Search_Materia.this.arrayAdaptar.getFilter().filter(n);
                return false;
            }
        });
    }

    //Consulta
    public void consultaMateria(final String materiae, final ArrayList<Profesor> opciones) {
        db.collection("Materias").document(materiae).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Materia aux = new Materia();
                    consultaProfes(materiae, opciones);
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
        db.collection("Materias").document(materia).collection("profesores").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Profesor profe = new Profesor();
                        Materia mat = new Materia();
                        ArrayList<SesionClase> listaSesion = (ArrayList<SesionClase>) document.getData().get("horarios");
                        profe.setNombre(document.getData().get("nombre").toString());
                        mat.setProfesores(profe.getNombre());
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


