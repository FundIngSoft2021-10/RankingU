package com.example.rankingu.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.rankingu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RatingBar calprofesor;
    private TextView  nombreprofesor;
    private TextView tipovista;
    private ImageView imagenprofesor;
    private TextView materiaprofesor;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        String aux = "estudiante";
        View root=null;
        if(aux.equalsIgnoreCase("Estudiante")){
            root = inflater.inflate(R.layout.fragment_home_estudiante, container, false);
        }
        if(aux.equalsIgnoreCase("Profesor"))
        {
            root = inflater.inflate(R.layout.fragment_home_profesor, container, false);
            calprofesor= root.findViewById(R.id.RatingBarProfesor);
            nombreprofesor= root.findViewById(R.id.NombreProfesor);
            tipovista=root.findViewById(R.id.TipoInicio);
            imagenprofesor=root.findViewById(R.id.ImageProfesor);
            materiaprofesor=root.findViewById(R.id.VistaMateriaProfe);
        }
        //final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
            }
        });
        return root;
    }

    public void consultaMaterias(final String profesor, final ArrayList<String> opciones, final ArrayList<String> ratings){
        db.collection("Profesores").document(profesor).collection("materias")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                              //  Log.d(TAG, document.getId() + " => " + document.getData().get("nombre").toString());
                                opciones.add(document.getData().get("nombre").toString());
                                consultaRating(profesor, opciones.get(opciones.size()),ratings);
                            }
                        } else {
                            Log.d(TAG, "Error en la BD: ", task.getException());
                        }
                    }
                });
    }


    public void consultaRating( String profesor, String opciones, final ArrayList<String> ratings){
        db.collection("Materias").document(opciones).collection("profesores").whereEqualTo("nombre",profesor)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult())
                            {
                                ratings.add(document.getData().get("rating").toString());
                            }
                        } else {
                            Log.d(TAG, "Error en la BD: ", task.getException());
                        }
                    }
                });
    }

}