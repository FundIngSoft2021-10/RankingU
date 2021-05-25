package com.example.rankingu.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.rankingu.Classes.Materia;
import com.example.rankingu.Classes.SesionClase;
import com.example.rankingu.Controller.ControllerHorario;
import com.example.rankingu.R;
import com.example.rankingu.ui.Horario.HorarioFragment;
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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        String aux = "estudiante";
        View root=null;
        HorarioFragment = new HorarioFragment();
        if(aux.equalsIgnoreCase("Estudiante")){

            root = inflater.inflate(R.layout.fragment_home_estudiante, container, false);
            tablaHorario = (TableLayout) root.findViewById(R.id.horarioTable);
            //cleanHorario(tablaHorario,fila,textoCelda);
            //construirHorario(tablaHorario,fila,textoCelda,root);
            busquedaHorarioEst(db,tablaHorario,fila,textoCelda,root);


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
                ArrayList<Materia> arrayAux = new ArrayList<>();
                if(task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //updateUi(document.getData().toString());
                        Materia m = new Materia();
                        List<SesionClase> sesiones = new ArrayList<>();

                        //SesionClase aux = new SesionClase();
                        //aux = document.getData().get("sesiones_clase").;
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
                        construirHorario(tablaHorario,fila,textoCelda,vista,  arrayAux);
                    }
                }

            }
        });
    }

    //Metodos Horario
    private void construirHorario(TableLayout tablaHorario, TableRow fila, TextView textoCelda,View vista,ArrayList<Materia> arr)
    {
        List<Integer> dias = new ArrayList<>();
        List<Integer> horasInicio= new ArrayList<>();
        List<Integer> horasFin= new ArrayList<>();
        /*
        Materia m1;
        Materia m2;
        List<Materia> materiaLista = new ArrayList<>();
        List<Integer> dias = new ArrayList<>();
        List<Integer> horasInicio= new ArrayList<>();
        List<Integer> horasFin= new ArrayList<>();

        SesionClase s1_7a9, s2_2a4, s3_Viernes;
        List<SesionClase> sesionesClase = new ArrayList<>();
        List<SesionClase> sesion2 = new ArrayList<>();
        s1_7a9 = new SesionClase("lunes","7:00","9:00","20");
        s2_2a4 = new SesionClase("miercoles","14:00","16:00","20");
        s3_Viernes = new SesionClase("domingo", "13:00","15:00","20");
        sesionesClase.add(s1_7a9);

        sesionesClase.add(s2_2a4);
        sesion2.add(s3_Viernes);


        m1 = new Materia("Calculo",sesionesClase);
        m2 = new Materia("Etica en la ingenieria", sesion2);
        materiaLista.add(m1);
        materiaLista.add(m2);

        //updateUi(String.valueOf(materiaLista.size()));

         */
        for(int j = 0;j<arr.size();j++)
        {
            dias =  controladorHorario.getDiaHorario(arr.get(j));
            horasInicio = controladorHorario.getHoraInicioHorario(arr.get(j));
            horasFin = controladorHorario.getHoraFinHorario(arr.get(j));
            for(int i = 0; i<dias.size();i++)
            {
                fila = (TableRow) vista.findViewById(tablaHorario.getChildAt(horasInicio.get(i)).getId());
                textoCelda =  (TextView) vista.findViewById(fila.getChildAt(dias.get(i)).getId());
                textoCelda.setBackgroundColor(30);
                textoCelda.setTextSize(1,11);
                //textoCelda.setText(materiaLista.get(j).toString());
                fila = (TableRow) vista.findViewById(tablaHorario.getChildAt(horasFin.get(i)).getId());
                textoCelda =  (TextView) vista.findViewById(fila.getChildAt(dias.get(i)).getId());
                textoCelda.setText(arr.get(j).toString());
                textoCelda.setBackgroundColor(30);
                textoCelda.setTextSize(1,11);
            }
        }
    }

    private void cleanHorario(TableLayout tablaHorario, TableRow fila, TextView textoCelda, View vista)
    {
        for(int i = 0;i<tablaHorario.getChildCount();i++)
        {
            fila = (TableRow) vista.findViewById(tablaHorario.getChildAt(i).getId());
            for(int j=1;j<fila.getChildCount();j++)
            {

                //textoCelda =  (TextView) vista.findViewById();

                //textoCelda.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                //textoCelda.setText("");
            }
        }

    }


    private void updateUi(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }
}