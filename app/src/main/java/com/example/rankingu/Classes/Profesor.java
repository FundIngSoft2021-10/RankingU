package com.example.rankingu.Classes;

import java.util.List;

public class Profesor {

    String nombre;
    List<MateriaDelMain> materiasList;

    public Profesor() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<MateriaDelMain> getMateriasList() {
        return materiasList;
    }

    public void setMateriasList(List<MateriaDelMain> materiasList) {
        this.materiasList = materiasList;
    }
}
