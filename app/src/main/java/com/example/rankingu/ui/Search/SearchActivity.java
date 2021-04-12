package com.example.rankingu.ui.Search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;
import com.example.rankingu.ui.Search.Search_Materia;
import com.example.rankingu.ui.Search.Search_Materia_Carrera;
import com.example.rankingu.ui.Search.Search_Profesor;

public class SearchActivity extends AppCompatActivity {

    private Spinner spinner1;
    private EditText et1;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        et1 = (EditText) findViewById(R.id.editTextBusqueda);
        tv1 = (TextView) findViewById(R.id.Buscarpor);
        spinner1 = (Spinner)findViewById(R.id.spinnerBuscar);

        String [] opciones = {"Profesor","Materia","Carrera"};
        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        spinner1.setAdapter(adapter);
    }

    //
    public void Buscar(View view){

        String valor1_String = et1.getText().toString();

        String seleccion = spinner1.getSelectedItem().toString();
        if(seleccion.equals("Profesor")){

            Intent searchProfesor = new Intent(this, Search_Profesor.class);
            startActivity(searchProfesor);

        }
        else if(seleccion.equals("Materia")){

            Intent searchMateria = new Intent(this, Search_Materia.class);
            startActivity(searchMateria);

        }
        else if(seleccion.equals("Carrera")){

            Intent searchCarrera = new Intent(this, Search_Materia_Carrera.class);
            startActivity(searchCarrera);

        }
    }
}
