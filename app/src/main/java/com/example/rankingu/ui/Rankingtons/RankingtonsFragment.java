package com.example.rankingu.ui.Rankingtons;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import com.example.rankingu.Controller.ControllerHorario;
import com.example.rankingu.R;
import com.example.rankingu.ui.Materia.MateriaActivity;
import com.example.rankingu.ui.Search.Search_Materia;
import com.example.rankingu.ui.home.HomeViewModel;
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
import java.util.StringTokenizer;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RankingtonsFragment extends Fragment {

    private RankingtonsViewModel rankingtonsViewModel;
    private ListView lista;
    private TextView nombreUsu;
    private ArrayList<Profesor> profesores = new ArrayList<>();
    private ArrayList<Materia> materias = new ArrayList<>();
    private ArrayList<String> resultados = new ArrayList<>();
    private ArrayAdapter<Profesor>adaptador;
    private ArrayAdapter<Materia>adaptadorAux;
    //private ControllerHorario controladorHorario = new ControllerHorario();

    private TableLayout tablaHorario;
    private TableRow fila;
    private TextView textoCelda;
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
        tablaHorario = (TableLayout) root.findViewById(R.id.horarioTable);
        lista = (ListView) root.findViewById(R.id.lista_rankington);

        //adaptador = new ArrayAdapter<Profesor>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1, profesores);
        adaptadorAux = new ArrayAdapter<Materia>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1,android.R.id.text1,materias );

        Profesor pr = new Profesor();




        lista.setAdapter(adaptador);
        buscarTodas(db,adaptadorAux);


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

    public void buscarTodas(final FirebaseFirestore db,  final ArrayAdapter<Materia> adapt)
    {

        db.collection("Materias").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult())
                {
                    materiaDB(db,document.getData().get("nombre").toString(),adapt);
                }
            }
        });

    }

    public void materiaDB(final FirebaseFirestore db, final String nombreMateria, final ArrayAdapter<Materia> adapt)
    {
       // nombreMateria = "poo";

       // final String finalNombreMateria = nombreMateria;
        db.collection("Materias").document(nombreMateria).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Materia m = new Materia();
                if(task.isSuccessful())
                {
                    m.setNombre(nombreMateria);
                    //m.setSemestre(Integer.parseInt(task.getResult().getData().get("semestre").toString()));
                    //m.setDescripcion(task.getResult().getData().get("descripcion").toString());
                    busca2(db,m.getNombre(),m,adapt);

                }

            }
        });
    }

    public void busca2(final FirebaseFirestore db, String nombreMateria, final Materia m, final ArrayAdapter<Materia> adapt)
    {
        db.collection("Materias").document(nombreMateria).collection("profesores").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for (QueryDocumentSnapshot document : task.getResult()) {

                    Profesor p = new Profesor();
                    p.setNombre(document.getData().get("nombre").toString());
                    m.setProfesores(p.getNombre());
                    adapt.add(m);
                }
            }
        });

    }




    public void busquedaHorarioEst(final FirebaseFirestore db, final TableLayout tablaHorario, final TableRow fila, final TextView textoCelda, final View vista){
        final ArrayList<Materia> array = new ArrayList<>();
        db.collection("Usuarios").document("aux@javeriana.edu.co").collection("materias").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Materia> arrayAux = new ArrayList<>();
                if(task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //updateUi(document.getData().toString());
                        Materia m = new Materia();
                        List<SesionClase> sesiones = new ArrayList<>();


                        String dia = document.getData().get("dia").toString();
                        String diaCons ="",hInicio ="", hFin="", cupos ="";
                        hInicio = document.getData().get("hInicio").toString();
                        hFin = document.getData().get("hFin").toString();
                        cupos = document.getData().get("cupos").toString();


                        StringTokenizer st = new StringTokenizer(dia,"-");
                        while(st.hasMoreTokens())
                        {
                            //updateUi(st.nextToken());
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
                            sesiones.add(new SesionClase(diaCons,hInicio,hFin,cupos));
                            updateUi("sesiones switch "+sesiones.size());
                        }
                        m.setNombre(document.getData().get("nombre").toString());
                        m.setSesiones_clase(sesiones);
                        arrayAux.add(m);
                        updateUi("array size inside "+arrayAux.size());
                        updateUi("size of sesion "+arrayAux.get(0).getSesiones_clase().size());
                       // construirHorario(tablaHorario,fila,textoCelda,vista,  arrayAux);
                    }
                }

            }
        });
    }




    private void updateUi(String msg) {
        Toast.makeText(getActivity().getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }





}



