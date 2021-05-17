package com.example.rankingu.Classes;

import java.util.List;

public class MateriaDelMain {

    String nombre, descripcion;
    Integer semestre;
    Double rating;
    List<Horario> horarios;
    String profesoresList;

    public MateriaDelMain() {
    }

    @Override
    public String toString() {
        return "\nMateria: " +
                nombre + horarios;
    }

    public MateriaDelMain(String nombre, String descripcion, Integer semestre, Double rating, List<Horario> horarios, String profesoresList) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.semestre = semestre;
        this.rating = rating;
        this.horarios = horarios;
        this.profesoresList = profesoresList;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProfesoresList() {
        return profesoresList;
    }

    public void setProfesoresList(String profesoresList) {
        this.profesoresList = profesoresList;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }
}
