package com.example.rankingu.ui.Search;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;


public class SearchActivity extends AppCompatActivity {

    private Spinner spinner1;
    private EditText et1;
    private TextView tv1;
    private Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        et1 = findViewById(R.id.editTextBusqueda);
        tv1 = findViewById(R.id.Buscarpor);
        spinner1 = findViewById(R.id.spinnerBuscar);
        btnBuscar = findViewById(R.id.BotonBuscar);
        String[] opciones = {"Materia"};//{"Profesor", "Materia", "Carrera"};
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
        String valor1_String = et1.getText().toString().trim();

        String seleccion = spinner1.getSelectedItem().toString();
        if (seleccion.equals("Profesor")) {
            Intent searchProfesor = new Intent(this, Search_Profesor.class);
            startActivity(searchProfesor);

        } else if (seleccion.equals("Materia")) {
            Intent searchMateria = new Intent(this, Search_Materia.class);
            Bundle myBundle = new Bundle();
            myBundle.putSerializable("palabra", valor1_String);
            searchMateria.putExtras(myBundle);
            startActivity(searchMateria);

        } else if (seleccion.equals("Carrera")) {
            Intent searchCarrera = new Intent(this, Search_Materia_Carrera.class);
            startActivity(searchCarrera);
        }
    }
}
