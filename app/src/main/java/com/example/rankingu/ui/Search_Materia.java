package com.example.rankingu.ui;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SearchView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;

import java.util.ArrayList;

public class Search_Materia extends AppCompatActivity {

    public static ArrayList<MateriaS> materias = new ArrayList<MateriaS>();

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
        searchView = (SearchView) findViewById(R.id.materiaSearchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                currentSearchText = s;
                ArrayList<MateriaS> filteredMateriaS = new ArrayList<MateriaS>();

                for (MateriaS materiaS : materias) {
                    if (materiaS.getName().toLowerCase().contains(s.toLowerCase())) {
                        filteredMateriaS.add(materiaS);
                    }
                }
                return false;
            }
        });
    }


    private void setupData() {
        MateriaS materiaS1 = new MateriaS("ADOO");
        materias.add(materiaS1);

        MateriaS materiaS2 = new MateriaS("POO");
        materias.add(materiaS2);

        MateriaS materiaS3 = new MateriaS("IngeSoft");
        materias.add(materiaS3);

        MateriaS materiaS4 = new MateriaS("Intro Compu Grafica");
        materias.add(materiaS4);
    }

    private void setUpList() {
        listView = (ListView) findViewById(R.id.materiaListView);

        listView.setAdapter((ListAdapter) materias);
    }
}
