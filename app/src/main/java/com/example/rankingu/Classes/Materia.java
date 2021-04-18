package com.example.rankingu.Classes;

import java.util.List;

public class Materia {

    String nombre, descripcion;
    Integer semestre;
    Double rating;
    Horario horarios;
    List<String> profesoresList;

    public Materia() {
    }

    public Materia(String nombre, String descripcion, Integer semestre, Double rating, Horario horarios, List<String> profesoresList) {
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

    public List<String> getProfesoresList() {
        return profesoresList;
    }

    public void setProfesoresList(List<String> profesoresList) {
        this.profesoresList = profesoresList;
    }

    public Horario getHorarios() {
        return horarios;
    }

    public void setHorarios(Horario horarios) {
        this.horarios = horarios;
    }
}
