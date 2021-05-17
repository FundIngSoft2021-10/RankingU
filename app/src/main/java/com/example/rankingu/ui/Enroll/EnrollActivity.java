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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rankingu.Classes.Horario;
import com.example.rankingu.Classes.Materia;
import com.example.rankingu.Classes.MateriaDelMain;
import com.example.rankingu.Classes.Profesor;
import com.example.rankingu.Classes.SesionClase;
import com.example.rankingu.R;
import com.example.rankingu.ui.MainActivity;
import com.example.rankingu.ui.Materia.MateriaActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EnrollActivity extends AppCompatActivity{

    private TextView materia;
    private TextView descripcion;
    private Spinner profesor;
    private RatingBar calif;
    private Button confirmar;
    private Button cancelar;
    private String semestre;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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

        Bundle myBundle = this.getIntent().getExtras();
        final Profesor x = (Profesor) myBundle.getSerializable("profesor");
        final Materia elim = null;

        final ArrayList<String> opciones = new ArrayList<>();
        final ArrayList<String> ratins = new ArrayList<>();
        //ratins.add("5");
        opciones.add(x.getNombre());

        consultaMateria("poo",opciones, ratins, x.getNombre());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        profesor.setAdapter(adapter);

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = registrarInscripcion(x, elim);
                if(result){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(EnrollActivity.this);
                    alerta.setMessage("La materia fue inscrita de manera exitosa")
                            .setCancelable(false)
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    Intent intent = new Intent(EnrollActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                    AlertDialog exito = alerta.create();
                    exito.setTitle("");
                    exito.show();
                }
                else{
                    Intent intent = new Intent(EnrollActivity.this, ConflictActivity.class);

                    SesionClase hor = new SesionClase();
                    hor.setCupos("21");
                    hor.setDia("l-m");
                    hor.sethFin("13:00");
                    hor.sethInicio("11:00");
                    List<SesionClase> horarios = new ArrayList<>();
                    horarios.add(hor);
                    Materia mt = new Materia(materia.getText().toString(), descripcion.getText().toString(), Integer.parseInt(semestre), (double) calif.getRating(), horarios, x.getNombre());

                    Bundle myBundle = new Bundle();
                    myBundle.putSerializable("decidir", mt);
                    myBundle.putSerializable("eliminar", elim);
                    intent.putExtras(myBundle);

                    startActivity(intent);
                }
            }
        });

        //Selecciona profesor
        profesor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(), "selecciono: "+elemento,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //Consulta
    public void consultaMateria(final String materiae, final ArrayList<String> opciones, final ArrayList<String> ratins, final String x){
        db.collection("Materias").document(materiae).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                               descripcion.setText(task.getResult().getData().get("descripcion").toString());
                               materia.setText(materiae);
                               semestre = task.getResult().getData().get("semestre").toString();
                               consultaProfes(materiae ,opciones, ratins, x);
                        } else {
                            Log.d(TAG, "Error en la BD: ", task.getException());
                        }
                    }
                });
    }

    //Cargar inscripción a la Base de datos
    public boolean registrarInscripcion(Profesor x, Materia cruce){
        // acabar la funcion de validar
        if(validarInscripcion(cruce)){
            SesionClase hor = new SesionClase();
            hor.setCupos("21");
            hor.setDia("l-m");
            hor.sethFin("13:00");
            hor.sethInicio("11:00");
            List<SesionClase> horarios = new ArrayList<>();
            horarios.add(hor);
            Materia inscrip = new Materia(materia.getText().toString(), descripcion.getText().toString(), Integer.parseInt(semestre), (double) calif.getRating(), horarios, x.getNombre());

            //Añadir a la BD
            db.collection("Usuarios").document(user.getEmail()).collection("materias").add(inscrip);
            Toast.makeText(this, "Materia inscrita", Toast.LENGTH_LONG).show();
            return true;
        }else{
            Toast.makeText(this, "Conflicto de horarios", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean validarInscripcion(Materia cruce){
        consultaCruces(cruce);
        if(cruce.getNombre().isEmpty())
            return true;
        else
            return false;
    }

    //Consulta
    public void consultaCruces(final Materia cruce){
        db.collection("Usuarios").document(user.getEmail()).collection("materias").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData().get("nombre").toString());
                                ArrayList <SesionClase> aux = (ArrayList<SesionClase>) document.getData().get("sesiones_clase");
                                if(aux.get(0).getDia().equalsIgnoreCase("horario")){
                                    if(aux.get(0).gethInicio().equalsIgnoreCase("horario") ||aux.get(0).gethFin().equalsIgnoreCase("horario") ){
                                        cruce.setNombre(document.getData().get("nombre").toString());
                                        cruce.setDescripcion(document.getData().get("descripcion").toString());
                                        cruce.setProfesores(document.getData().get("profesores").toString());
                                        cruce.setPuntaje((Double) document.getData().get("puntaje"));
                                        cruce.setSemestre((Integer) document.getData().get("semestre"));
                                        cruce.setSesiones_clase(aux);
                                    }
                                }
                            }
                        } else {
                            Log.d(TAG, "Error en la BD: ", task.getException());
                        }
                    }
                });
    }

    //Consulta
    public void consultaProfes(String materia, final ArrayList<String> opciones, final ArrayList<String> ratins, final String x){
        db.collection("Materias").document(materia).collection("profesores")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData().get("nombre").toString());
                                if(document.getData().get("nombre").toString().equalsIgnoreCase(x)){
                                    //opciones.add(document.getData().get("nombre").toString());
                                    ratins.add(document.getData().get("rating").toString());
                                    calif.setRating(Float.parseFloat(ratins.get(0)));
                                }
                            }
                        } else {
                            Log.d(TAG, "Error en la BD: ", task.getException());
                        }
                    }
                });
    }
}