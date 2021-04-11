package com.example.rankingu.ui.User_Main;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;

public class SearchActivity extends AppCompatActivity {

    private Spinner spinner1;
    private EditText et1;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_Search);

        et1 = (EditText) findViewById(R.id.editTextBusqueda);
        tv1 = (TextView) findViewById(R.id.textView);
        spinner1 = (Spinner)findViewById(R.id.spinner);

        String [] opciones = {"Profesor","Materia","Carrera"};
        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        spinner1.setAdapter(adapter);
    }

    //
    public void Buscar(View view){

        String valor1_String = et1.getText().toString();

        String seleccion = spinner1.getSelectedItem().toString();
        if(seleccion.equals("Profesor")){

        }
        else if(seleccion.equals("Materia")){

        }
     /*   else(seleccion.equals("Carrera")){

        }*/
    }
}
