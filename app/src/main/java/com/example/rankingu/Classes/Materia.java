package com.example.rankingu.Classes;

import java.util.List;

public class Materia {

    String nombre , descripcion;
    Integer semestre;
    Double puntaje;
    List<SesionClase> sesiones_clase;
    List<String> profesores;

    public Materia() {
    }

    public Materia(String nombre, String descripcion, Double puntaje, Integer semestre, List<String> profesores, List<SesionClase> sesiones_clase) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntaje = puntaje;
        this.semestre = semestre;
        this.profesores = profesores;
        this.sesiones_clase = sesiones_clase;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Materia(String nombre, List<SesionClase> sesiones_clase) {
        this.nombre = nombre;
        this.sesiones_clase = sesiones_clase;
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

    public Double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Double puntaje) {
        this.puntaje = puntaje;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
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
