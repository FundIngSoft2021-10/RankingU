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

import com.example.rankingu.Classes.Profesor;
import com.example.rankingu.R;
import com.example.rankingu.ui.Materia.MateriaActivity;

import java.util.ArrayList;

public class Search_Materia extends AppCompatActivity {

    private ListView listView;
    private String currentSearchText = "";
    private SearchView searchView;
    ArrayAdapter<Profesor> arrayAdaptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_materia);
        searchView = findViewById(R.id.materiaSearchView);
        listView = findViewById(R.id.materiaListView);
        ArrayList<Profesor> profesores = new ArrayList<>();

        Bundle myBundle = this.getIntent().getExtras();

        profesores = (ArrayList<Profesor>) myBundle.getSerializable("ListaProfesores");
        System.out.println("profesor llegada: "+profesores.toString());


        //arrayAdaptar = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, profesores.toString());
        listView.setAdapter(arrayAdaptar);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Profesor x = (Profesor) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(Search_Materia.this, MateriaActivity.class);
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
}


