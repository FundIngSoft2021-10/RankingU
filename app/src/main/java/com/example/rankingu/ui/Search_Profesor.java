package com.example.rankingu.ui;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;

import java.util.ArrayList;

public class Search_Profesor extends AppCompatActivity {

    public static ArrayList<Profesor> profesores = new ArrayList<Profesor>();

    private ListView listView;

    private String currentSearchText = "";
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSearchWidgets();
        setupData();
        setUpList();
    }

    private void initSearchWidgets() {
        searchView = (SearchView) findViewById(R.id.profesorSearchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                currentSearchText = s;
                ArrayList<Profesor> filteredProfesor = new ArrayList<Profesor>();

                for (Profesor profesor : profesores) {
                    if (profesor.getName().toLowerCase().contains(s.toLowerCase())) {
                        if (selectedFilter.equals("all")) {
                            filteredProfesor.add(profesor);
                        } else {
                            if (profesor.getName().toLowerCase().contains(selectedFilter)) {
                                filteredProfesor.add(profesor);
                            }
                        }
                    }
                }
                ShapeAdapter adapter = new ShapeAdapter(getApplicationContext(), 0, filteredProfesor);
                listView.setAdapter(adapter);

                return false;
            }
        });
    }


    private void setupData() {
        MateriaCarrera Profesor1 = new MateriaCarrera("Anabel Montero");
        profesores.add(Profesor1);

        MateriaCarrera Profesor2 = new MateriaCarrera("Leonardo Florez");
        profesores.add(Profesor2);

        MateriaCarrera Profesor3 = new MateriaCarrera("Julio Carreño");
        profesores.add(Profesor3);

        MateriaCarrera Profesor4 = new MateriaCarrera("Camilo Cañon");
        profesores.add(Profesor4);
    }

    private void setUpList() {
        listView = (ListView) findViewById(R.id.profesorListView);

        MateriaCarrera adapter = new MateriaCarrera(getApplicationContext(), 0, profesores);
        listView.setAdapter(adapter);
    }
}
