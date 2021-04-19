package com.example.rankingu.Classes;

import java.util.List;

public class Materia {

<<<<<<< Updated upstream
    String nombre;
    Integer puntaje, semestre, cupos;
    List<String> horarios;
    List<String> profesores;
=======
    String nombre, descripcion;
    Integer semestre;
    Double rating;
    Horario horarios;
    List<String> profesoresList;
    List<Reseña> reseñas;
>>>>>>> Stashed changes

    public Materia() {
    }

<<<<<<< Updated upstream
    public Materia(String nombre, Integer puntaje, Integer semestre, Integer cupos, List<String> horarios, List<String> profesores) {
=======
    public Materia(String nombre, String descripcion, Integer semestre, Double rating, Horario horarios, List<String> profesoresList, List<Reseña> reseñas) {
>>>>>>> Stashed changes
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.semestre = semestre;
        this.cupos = cupos;
        this.horarios = horarios;
<<<<<<< Updated upstream
        this.profesores = profesores;
=======
        this.profesoresList = profesoresList;
        this.reseñas = reseñas;
    }

    public List<Reseña> getReseñas() {
        return reseñas;
    }

    public void setReseñas(List<Reseña> reseñas) {
        this.reseñas = reseñas;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
>>>>>>> Stashed changes
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
