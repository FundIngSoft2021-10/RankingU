package com.example.rankingu.ui.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.rankingu.Classes.Materia;
import com.example.rankingu.Classes.SesionClase;
import com.example.rankingu.Controller.ControllerHorario;
import com.example.rankingu.R;
import com.example.rankingu.ui.Enroll.ConflictActivity;
import com.example.rankingu.ui.Horario.HorarioFragment;
import com.example.rankingu.ui.Horario.eliminarMateria;
import com.example.rankingu.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RatingBar calprofesor;
    private TextView  nombreprofesor;
    private TextView tipovista;
    private ImageView imagenprofesor;
    private TextView materiaprofesor;
    private TableLayout tablaprofesor;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private FragmentTransaction transaction;
    private Fragment HorarioFragment;

    //HorarioAtributos
    private ControllerHorario controladorHorario = new ControllerHorario();

    private TableLayout tablaHorario;
    private TableRow fila;
    private TextView textoCelda;
    private SwipeRefreshLayout refrescar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        String aux = "estudiante";
        View root=null;
        HorarioFragment = new HorarioFragment();
        if(aux.equalsIgnoreCase("Estudiante")){

            root = inflater.inflate(R.layout.fragment_home_estudiante,null);

            tablaHorario = (TableLayout) root.findViewById(R.id.horarioTable);
            refrescar = (SwipeRefreshLayout) root.findViewById(R.id.refrescarSwipe);
            //cleanHorario(tablaHorario,fila,textoCelda);
            //construirHorario(tablaHorario,fila,textoCelda,root);

            busquedaHorarioEst(db,tablaHorario,fila,textoCelda,root);

            final View finalRoot = root;
            refrescar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        cleanHorario( tablaHorario,  fila,  textoCelda,   finalRoot);
                        busquedaHorarioEst(db,tablaHorario,fila,textoCelda, finalRoot);

                        refrescar.setRefreshing(false);
                    }
                });



            //tipovista.setText("Estudiante");
        }
        if(aux.equalsIgnoreCase("Profesor"))
        {
            root = inflater.inflate(R.layout.fragment_home_profesor, container, false);
            calprofesor= root.findViewById(R.id.RatingBarProfesor);
            nombreprofesor= root.findViewById(R.id.NombreProfesor);
            tipovista=root.findViewById(R.id.TipoInicio);
            materiaprofesor=root.findViewById(R.id.VistaMateriaProfe);
            tablaprofesor=root.findViewById(R.id.tablemateriasprofesor);
            // tipovista.setText(user.getClass().getName());
            final ArrayList<String> opciones = new ArrayList<>();
            final ArrayList<String> ratings = new ArrayList<>();
            ratings.add("5");
            opciones.add("materias");
            consultaMaterias("Anabel",opciones,ratings);
            nombreprofesor.setText("anabel");
            tipovista.setText("Profesor");
            LlenarTabla(ratings,opciones);
        }
        //final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
                                consultaRating(profesor, opciones.get(opciones.size()-1),ratings);
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
                                //materiaprofesor.setText(document.getData().get("nombre").toString());
                            }
                        } else {
                            Log.d(TAG, "Error en la BD: ", task.getException());
                        }
                    }
                });
    }

    public void LlenarTabla(final ArrayList<String> ratings,final ArrayList<String> opciones){
        int filas = tablaprofesor.getChildCount();
        tablaprofesor.removeViews(0, filas);
        if(ratings.size() > 0 && opciones.size() > 0) {
            for (int i = 0; i < ratings.size(); i++) {
                   TableRow fila1 = new TableRow(this.getContext());
                   TextView t1 = new TextView(this.getContext());
                   t1.setText(opciones.get(i));
                   fila1.addView(t1);

                   RatingBar t2 = new RatingBar(this.getContext());
                   t2.setRating(Float.parseFloat(ratings.get(i)));
                   t2.setEnabled(false);
                   fila1.addView(t2);
                   tablaprofesor.addView(fila1);
            }
        }
    }

    public void busquedaHorarioEst(final FirebaseFirestore db, final TableLayout tablaHorario, final TableRow fila, final TextView textoCelda, final View vista){
        final ArrayList<Materia> array = new ArrayList<>();
        db.collection("Usuarios").document(user.getEmail()).collection("materias").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
               // updateUi("actualizando horario");
                ArrayList<Materia> arrayAux = new ArrayList<>();
                ArrayList<SesionClase> sesiones = new ArrayList<>();
                String diaCons, hInicio, hFin, cupos;
                if(task.isSuccessful())
                {
                    diaCons = "";
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Materia m = document.toObject(Materia.class);

                        if(m.getSesiones_clase() !=null)
                        {
                            for(int i =0;i<m.getSesiones_clase().size();i++)
                            {
                                StringTokenizer st = new StringTokenizer(m.getSesiones_clase().get(i).getDia(),"-");
                                while(st.hasMoreTokens())
                                {
                                    switch(st.nextToken())
                                    {
                                        case "L":
                                            diaCons = "lunes";
                                            break;
                                        case "M":
                                            diaCons = "martes";
                                            break;
                                        case "X":
                                            diaCons = "miercoles";
                                            break;
                                        case "J":
                                            diaCons = "jueves";
                                            break;
                                        case "V":
                                            diaCons = "viernes";
                                            break;
                                        case "S":
                                            diaCons = "sabado";
                                            break;
                                        case "D":
                                            diaCons = "domingo";
                                            break;
                                        default:
                                            break;

                                    }
                                    sesiones.add(new SesionClase(diaCons,m.getSesiones_clase().get(0).gethInicio(),m.getSesiones_clase().get(0).gethFin(),m.getSesiones_clase().get(0).getCupos()));

                                }

                            }
                            m.setSesiones_clase(sesiones);

                            arrayAux.add(m);
                            construirHorario(tablaHorario,fila,textoCelda,vista,  arrayAux);

                        }
                        else
                            {

                            }
                    }
                }
                else {
                    updateUi("Error");
                    Log.d(TAG, "Error en la BD: ", task.getException());
                }
            }
        });
    }

    //Metodos Horario
    private void construirHorario(TableLayout tablaHorario, TableRow fila, TextView textoCelda, final View vista, final ArrayList<Materia> arr)
    {
        List<Integer> dias = new ArrayList<>();
        List<Integer> horasInicio= new ArrayList<>();
        List<Integer> horasFin= new ArrayList<>();

        for(int j = 0;j<arr.size();j++)
        {
            dias =  controladorHorario.getDiaHorario(arr.get(j));
            horasInicio = controladorHorario.getHoraInicioHorario(arr.get(j));
            horasFin = controladorHorario.getHoraFinHorario(arr.get(j));
            for(int i = 0; i<dias.size();i++)
            {
                fila = (TableRow) vista.findViewById(tablaHorario.getChildAt(horasInicio.get(i)).getId());
                final Materia m = arr.get(j);
                textoCelda =  (TextView) vista.findViewById(fila.getChildAt(dias.get(i)).getId());
                textoCelda.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        eliminarMateriaHorario(m);

                    }
                });
                textoCelda.setBackgroundColor(R.drawable.celdahorario);
                textoCelda.setTextSize(1,11);
                //textoCelda.setText(materiaLista.get(j).toString());
                fila = (TableRow) vista.findViewById(tablaHorario.getChildAt(horasFin.get(i)).getId());
                textoCelda =  (TextView) vista.findViewById(fila.getChildAt(dias.get(i)).getId());
                textoCelda.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        eliminarMateriaHorario(m);

                    }
                });
                //textoCelda.setText(arr.get(j).toString());
                textoCelda.setText(arr.get(j).getNombre()+"\n"+arr.get(j).getProfesores()+"\n"+arr.get(j).getSesiones_clase().get(j).gethInicio()+"-"+arr.get(j).getSesiones_clase().get(j).gethFin());
                textoCelda.setBackgroundColor(R.drawable.celdahorario);
                textoCelda.setTextSize(1,11);
                textoCelda.setBackgroundColor(R.drawable.celdahorario);
            }
        }
    }

    private void cleanHorario(TableLayout tablaHorario, TableRow fila, TextView textoCelda, final View vista)
    {


        for(int j = 1;j<tablaHorario.getChildCount();j++)
        {
            for(int i = 1; i<8;i++)
            {

                fila = (TableRow) vista.findViewById(tablaHorario.getChildAt(j).getId());

                textoCelda =  (TextView) vista.findViewById(fila.getChildAt(i).getId());
                textoCelda.setText("");
                textoCelda.setBackground(vista.getResources().getDrawable(R.drawable.cell_shape));

            }
        }
    }


    private void eliminarMateriaHorario(Materia m)
    {
        Intent intent = new Intent(getActivity(), eliminarMateria.class);
        intent.putExtra("materia", m);
        startActivity(intent);
    }

    private void updateUi(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }
}