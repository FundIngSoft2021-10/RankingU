package com.example.rankingu.Classes;

import java.util.List;

public class Materia {

    String nombre;
    Integer puntaje, semestre, cupos;
    List<String> horarios;
    List<String> profesores;

    public Materia() {
    }

    public Materia(String nombre, Integer puntaje, Integer semestre, Integer cupos, List<String> horarios, List<String> profesores) {
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.semestre = semestre;
        this.cupos = cupos;
        this.horarios = horarios;
        this.profesores = profesores;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Integer getCupos() {
        return cupos;
    }

    public void setCupos(Integer cupos) {
        this.cupos = cupos;
    }

    public List<String> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<String> horarios) {
        this.horarios = horarios;
    }

    public List<String> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<String> profesores) {
        this.profesores = profesores;
    }
}
