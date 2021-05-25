package com.example.rankingu.Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Profesor implements Serializable {

    String nombre;
    List<Materia> materiasList;

    public Profesor() {
        this.materiasList = new ArrayList<>();
    }

    public Profesor(String nombre, List<Materia> materiasList) {
        this.nombre = nombre;
        this.materiasList = materiasList;
    }




    @Override
    public String toString() {
        return "Profesor: " +
                 nombre +
                 materiasList;
    }







    public void addMateria(Materia materia){
        this.materiasList.add(materia);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Materia> getMateriasList() {
        return materiasList;
    }

    public void setMateriasList(List<Materia> materiasList) {
        this.materiasList = materiasList;
    }

}
