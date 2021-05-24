package com.example.rankingu.ui.Rankingtons;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.rankingu.Classes.Materia;
import com.example.rankingu.Classes.Profesor;
import com.example.rankingu.Classes.SesionClase;
import com.example.rankingu.R;
import com.example.rankingu.ui.Materia.MateriaActivity;
import com.example.rankingu.ui.Search.Search_Materia;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RankingtonsFragment extends Fragment {

    private RankingtonsViewModel rankingtonsViewModel;
    private ListView lista;
    private TextView nombreUsu;
    private ArrayList<Profesor> profesores = new ArrayList<>();
    private ArrayList<String> resultados = new ArrayList<>();
    private ArrayAdapter<Profesor>adaptador;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rankingtonsViewModel =
                ViewModelProviders.of(this).get(RankingtonsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rankingtons, container, false);
        rankingtonsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {


                    }
                }
        );
        nombreUsu =(TextView) root.findViewById(R.id.Buscarpor);

        lista = (ListView) root.findViewById(R.id.lista_rankington);

        adaptador = new ArrayAdapter<Profesor>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1, profesores);
        Profesor pr = new Profesor();
        consultaMateria("poo", profesores,adaptador,db);

        lista.setAdapter(adaptador);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Profesor x = (Profesor) adapterView.getItemAtPosition(i);
                System.out.println(x.toString());
                Intent intent = new Intent(getActivity().getBaseContext(), MateriaActivity.class);
                Bundle myBundle = new Bundle();
                myBundle.putSerializable("materia", x);
                intent.putExtras(myBundle);
                startActivity(intent);
            }
        });

        return root;
    }

    public ArrayList<Materia> consultarMateriasEstudiante(final FirebaseFirestore db)
    {
        ArrayList<Materia> materiasEstu = new ArrayList<>();

        //db.collection("Usuarios")
        return null;

    }



    //Consulta
    public void consultaMateria(final String materiae, final ArrayList<Profesor> opciones, final ArrayAdapter<Profesor> adapt, final FirebaseFirestore db) {
        db.collection("Materias").document(materiae).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {

                    Materia aux = new Materia();

                    aux.setNombre(task.getResult().getData().get("nombre").toString());
                    aux.setDescripcion(task.getResult().getData().get("descripcion").toString());
                    //aux.setSemestre((Integer) task.getResult().getData().get("semestre"));
                    consultaProfes(materiae, opciones, adapt,db, aux);

                } else {
                    Log.d(TAG, "Error en la BD: ", task.getException());
                }
            }
        });
    }

    //Consulta
    public void consultaProfes(final String materia, final ArrayList<Profesor> opciones, final ArrayAdapter<Profesor> adapt, final FirebaseFirestore db, final Materia mat) {

        db.collection("Materias").document(materia).collection("profesores").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Profesor profe = new Profesor();
                        profe.setNombre(document.getData().get("nombre").toString());
                        mat.setProfesores(profe.getNombre());
                        mat.setPuntaje(Double.parseDouble(document.getData().get("rating").toString()));
                        consultaSesion(materia,opciones,adapt, db,document.getId(), mat, profe);
                    }
                } else {
                    Log.d(TAG, "Error en la BD: ", task.getException());
                }
            }
        });
    }

    public void consultaSesion(final String materia, final ArrayList<Profesor> opciones, final ArrayAdapter<Profesor> adapt, final FirebaseFirestore db, final String idDoc, final Materia mat, final Profesor profe) {

        db.collection("Materias").document(materia).collection("profesores").document(idDoc).collection("horarios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {
                    //;


                    for (QueryDocumentSnapshot document : task.getResult()) {
                        SesionClase sesion = new SesionClase();
                        Materia mat = new Materia();
                       // List<SesionClase> lista = new ArrayList<>();

                        sesion.setCupos(document.getData().get("cupos").toString());
                        sesion.setDia(document.getData().get("dia").toString());
                        sesion.sethFin(document.getData().get("hFin").toString());
                        sesion.sethInicio(document.getData().get("hInicio").toString());
                        profe.addMateria(mat);

                       adapt.add(profe);



                    }
                }
                else {
                    Log.d(TAG, "Error en la BD: ", task.getException());
            }
            }


        });

    }






    private void updateUi(String msg) {
        Toast.makeText(getActivity().getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }





}



