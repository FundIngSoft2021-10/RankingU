package com.example.rankingu.ui.Search;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SearchView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;

import java.util.ArrayList;

public class Search_Materia_Carrera extends AppCompatActivity {/*

    public static ArrayList<MateriaCarreraS> carreras = new ArrayList<MateriaCarreraS>();

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
        searchView = (SearchView) findViewById(R.id.carreraSearchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                currentSearchText = s;
                ArrayList<MateriaCarreraS> filteredMateriaCarreraS = new ArrayList<MateriaCarreraS>();

                for (MateriaCarreraS carrera : carreras) {
                    if (carrera.getName().toLowerCase().contains(s.toLowerCase())) {
                        filteredMateriaCarreraS.add(carrera);
                    }
                }
                return false;
            }
        });
    }


    private void setupData() {
        MateriaCarreraS Carrera1 = new MateriaCarreraS("Calculo integral - Ingenieria");
        carreras.add(Carrera1);

        MateriaCarreraS Carrera2 = new MateriaCarreraS("Calculo integral - Administracion");
        carreras.add(Carrera2);

        MateriaCarreraS Carrera3 = new MateriaCarreraS("Principios de economia - Ingenieria");
        carreras.add(Carrera3);

        MateriaCarreraS Carrera4 = new MateriaCarreraS("Principios de economia - Economia");
        carreras.add(Carrera4);
    }

    private void setUpList() {
        listView = (ListView) findViewById(R.id.carreraListView);

        listView.setAdapter((ListAdapter) carreras);
    }
    */
}