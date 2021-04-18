package com.example.rankingu.Controller;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rankingu.Classes.Materia;
import com.example.rankingu.Classes.SesionClase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.example.rankingu.Enum.EnumDias;
import com.example.rankingu.Enum.EnumHoras;

public class ControllerHorario {
    private List<Materia> materias;
    private List<SesionClase> sesionesClase;
    private EnumDias dias;


    public List<Integer> getHoraInicioHorario(Materia materia) {
        List<Integer> listHoras = new ArrayList<>();
        for (int i = 0; i < materia.getSesiones_clase().size(); i++) {

            switch (materia.getSesiones_clase().get(i).gethInicio()) {
                case "7:00":
                    listHoras.add(1);
                    break;
                case "8:00":
                    listHoras.add(2);
                    break;
                case "9:00":
                    listHoras.add(3);
                    break;
                case "10:00":
                    listHoras.add(4);
                    break;
                case "11:00":
                    listHoras.add(5);
                    break;
                case "12:00":
                    listHoras.add(6);
                    break;
                case "13:00":
                    listHoras.add(7);
                    break;
                case "14:00":
                    listHoras.add(8);
                    break;
                case "15:00":
                    listHoras.add(9);
                    break;
                case "16:00":
                    listHoras.add(10);
                    break;
                case "17:00":
                    listHoras.add(11);
                    break;
                case "18:00":
                    listHoras.add(12);
                    break;
                case "19:00":
                    listHoras.add(13);
                    break;
                case "20:00":
                    listHoras.add(14);
                    break;
                case "21:00":
                    listHoras.add(15);
                    break;
                case "22:00":
                    listHoras.add(16);
                    break;
            }
        }
        return listHoras;
    }

        public List<Integer> getHoraFinHorario (Materia materia) {
            List<Integer> listHoras = new ArrayList<>();

            for (int i = 0; i < materia.getSesiones_clase().size(); i++) {
                switch (materia.getSesiones_clase().get(i).gethFin()) {
                    case "8:00":
                        listHoras.add(1);
                        break;
                    case "9:00":
                        listHoras.add(2);
                        break;
                    case "10:00":
                        listHoras.add(3);
                        break;
                    case "11:00":
                        listHoras.add(4);
                        break;
                    case "12:00":
                        listHoras.add(5);
                        break;
                    case "13:00":
                        listHoras.add(6);
                        break;
                    case "14:00":
                        listHoras.add(7);
                        break;
                    case "15:00":
                        listHoras.add(8);
                        break;
                    case "16:00":
                        listHoras.add(9);
                        break;
                    case "17:00":
                        listHoras.add(10);
                        break;
                    case "18:00":
                        listHoras.add(11);
                        break;
                    case "19:00":
                        listHoras.add(12);
                        break;
                    case "20:00":
                        listHoras.add(13);
                        break;
                    case "21:00":
                        listHoras.add(14);
                        break;
                    case "22:00":
                        listHoras.add(15);
                        break;

                }
            }
            return listHoras;
        }


        public List<Integer> getDiaHorario (Materia materia)
        {
            List<Integer> listDias = new ArrayList<>();
            for (int i = 0; i < materia.getSesiones_clase().size(); i++) {
                switch (materia.getSesiones_clase().get(i).getDia()) {
                    case "lunes":
                        listDias.add(1);
                        break;
                    case "martes":
                        listDias.add(2);
                        break;
                    case "miercoles":
                        listDias.add(3);
                        break;
                    case "jueves":
                        listDias.add(4);
                        break;
                    case "viernes":
                        listDias.add(5);
                        break;
                    case "sabado":
                        listDias.add(6);
                        break;
                    case "domingo":
                        listDias.add(7);
                        break;
                }
            }
            return listDias;
        }
}

