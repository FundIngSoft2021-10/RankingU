package com.example.rankingu.ui.Enroll;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EnrollActivity extends AppCompatActivity{

    private TextView materia;
    private TextView descripcion;
    private Spinner profesor;
    private RatingBar calif;
    private Button confirmar;
    private Button cancelar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        materia = findViewById(R.id.materiaView);
        descripcion = findViewById(R.id.textDescripcion);
        profesor = findViewById(R.id.spinnerProfesor);
        calif = findViewById(R.id.califMatProfe);
        calif.setEnabled(false);
        confirmar = findViewById(R.id.BtonConfirmar);
        cancelar = findViewById(R.id.BtonCancelar);

        final ArrayList<String> opciones = new ArrayList<>();
        final ArrayList<String> ratins = new ArrayList<>();
        ratins.add("5");
        opciones.add("Profesores");

        consultaMateria("poo",opciones, ratins);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        profesor.setAdapter(adapter);

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Registrar materia
                boolean x = false;
                if(x){
                Intent intent = new Intent(EnrollActivity.this, ConflictActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                }
                else{
                    AlertDialog.Builder alerta = new AlertDialog.Builder(EnrollActivity.this);
                    alerta.setMessage("La materia fue inscrita de manera exitosa")
                            .setCancelable(false)
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog exito = alerta.create();
                    exito.setTitle("");
                    exito.show();
                }
            }
        });

        //Selecciona profesor
        profesor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calif.setRating(Float.parseFloat(ratins.get(position)));// elemento = (String) profesor.getAdapter().getItem(position);
                //Toast.makeText(getContext(), "selecciono: "+elemento,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //Consulta
    public void consultaMateria(final String materiae, final ArrayList<String> opciones, final ArrayList<String> ratins){
        db.collection("Materias").document(materiae)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                               descripcion.setText(task.getResult().getData().get("descripcion").toString());
                               materia.setText(materiae);
                               consultaProfes(materiae,opciones,ratins);
                        } else {
                            Log.d(TAG, "Error en la BD: ", task.getException());
                        }
                    }
                });
    }

    //Consulta
    public void consultaProfes(String materia, final ArrayList<String> opciones, final ArrayList<String> ratins){
        db.collection("Materias").document(materia).collection("profesores")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData().get("nombre").toString());
                                opciones.add(document.getData().get("nombre").toString());
                                ratins.add(document.getData().get("rating").toString());
                            }
                        } else {
                            Log.d(TAG, "Error en la BD: ", task.getException());
                        }
                    }
                });
    }
}