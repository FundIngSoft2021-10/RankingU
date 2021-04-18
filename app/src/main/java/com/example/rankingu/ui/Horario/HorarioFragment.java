package com.example.rankingu.ui.Horario;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rankingu.Classes.Materia;
import com.example.rankingu.Classes.MateriaDelMain;
import com.example.rankingu.Classes.SesionClase;
import com.example.rankingu.Controller.ControllerHorario;
import com.example.rankingu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HorarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HorarioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<MateriaDelMain> materias;
    ControllerHorario controladorHorario = new ControllerHorario();

    TableLayout tablaHorario;
    TableRow f7;
    View vista;
    TextView textoCelda;

    public HorarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HorarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HorarioFragment newInstance(String param1, String param2) {
        HorarioFragment fragment = new HorarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_home_estudiante, container, false);

        tablaHorario = (TableLayout) vista.findViewById(R.id.horarioTable);
        //f7 = (TableRow) vista.findViewById(tablaHorario.getChildAt(5).getId());
        //f7 = (TableRow) vista.findViewById(R.id.f_7);
        construirHorario(tablaHorario,f7,textoCelda);

        /*
        for(int i = 1;i<8;i++)
        {
            textoCelda =  vista.findViewById(f7.getChildAt(i).getId());
            textoCelda.setText("soy la celda "+i);

        }
        */
        // Inflate the layout for this fragment
        return vista;
    }


    private void cleanHorario()
    {

    }
    private void construirHorario(TableLayout tablaHorario, TableRow fila, TextView textoCelda)
    {

        Materia m1;
        SesionClase s1_7a9, s2_2a4;
        List<SesionClase> sesionesClase = new ArrayList<>();
        s1_7a9 = new SesionClase("lunes","7:00","9:00");
        s2_2a4 = new SesionClase("miercoles","14:00","16:00");
        sesionesClase.add(s1_7a9);

        sesionesClase.add(s2_2a4);


        m1 = new Materia("Calculo",sesionesClase);


        List<Integer> dias =  controladorHorario.getDiaHorario(m1);
        List<Integer> horasInicio = controladorHorario.getHoraInicioHorario(m1);
        List<Integer> horasFin = controladorHorario.getHoraFinHorario(m1);

        updateUi("mensaje dias size: "+dias.size());

        for(int i = 0; i<dias.size();i++)
        {
                fila = (TableRow) vista.findViewById(tablaHorario.getChildAt(horasInicio.get(i)).getId());
                textoCelda =  (TextView) vista.findViewById(fila.getChildAt(dias.get(i)).getId());
                textoCelda.setText(m1.toString());
                fila = (TableRow) vista.findViewById(tablaHorario.getChildAt(horasFin.get(i)).getId());
                textoCelda =  (TextView) vista.findViewById(fila.getChildAt(dias.get(i)).getId());
                textoCelda.setText(m1.toString());


        }

    }


    private void updateUi(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }
}