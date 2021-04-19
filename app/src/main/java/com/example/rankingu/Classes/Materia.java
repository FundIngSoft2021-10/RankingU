package com.example.rankingu.Classes;

import java.util.List;

public class Materia {

    String nombre;
    Integer puntaje, semestre, cupos;
    List<String> horarios;
    List<String> profesores;
    List<SesionClase> sesiones_clase;
    List<Reseña> reseñas;

    public Materia() {
    }

    public Materia(String nombre, Integer puntaje, Integer semestre, Integer cupos, List<String> horarios, List<String> profesores, List<SesionClase> sesiones_clase, List<Reseña> reseñas) {
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.semestre = semestre;
        this.cupos = cupos;
        this.horarios = horarios;
        this.profesores = profesores;
        this.sesiones_clase = sesiones_clase;
        this.reseñas = reseñas;
    }

    public Materia(String nombre, List<SesionClase> sesiones_clase) {
        this.nombre = nombre;
        this.sesiones_clase = sesiones_clase;
    }

    public List<Reseña> getReseñas() {
        return reseñas;
    }

    public void setReseñas(List<Reseña> reseñas) {
        this.reseñas = reseñas;
    }

    public List<SesionClase> getSesiones_clase() {
        return sesiones_clase;
    }

    public void setSesiones_clase(List<SesionClase> sesiones_clase) {
        this.sesiones_clase = sesiones_clase;
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

    public String toString()
    {
        String texto = this.nombre+"\n";
        for(SesionClase s:this.sesiones_clase)
        {
            texto+=" "+s.toString();
        }
        return texto;
    }
}
