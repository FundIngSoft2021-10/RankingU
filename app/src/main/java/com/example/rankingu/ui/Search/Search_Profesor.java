package com.example.rankingu.ui.Search;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SearchView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;
import com.example.rankingu.ui.ProfesorS;

import java.util.ArrayList;

public class Search_Profesor extends AppCompatActivity {

    public static ArrayList<ProfesorS> profesores = new ArrayList<ProfesorS>();

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
                ArrayList<ProfesorS> filteredProfesorS = new ArrayList<ProfesorS>();

                for (ProfesorS profesorS : profesores) {
                    if (profesorS.getName().toLowerCase().contains(s.toLowerCase())) {
                            filteredProfesorS.add(profesorS);
                    }
                }
                return false;
            }
        });
    }


    private void setupData() {
        ProfesorS profesorS1 = new ProfesorS("Anabel Montero");
        profesores.add(profesorS1);

        ProfesorS profesorS2 = new ProfesorS("Leonardo Florez");
        profesores.add(profesorS2);

        ProfesorS profesorS3 = new ProfesorS("Julio Carreño");
        profesores.add(profesorS3);

        ProfesorS profesorS4 = new ProfesorS("Camilo Cañon");
        profesores.add(profesorS4);
    }

    private void setUpList() {
        listView = (ListView) findViewById(R.id.profesorListView);

        listView.setAdapter((ListAdapter) profesores);
    }
}
