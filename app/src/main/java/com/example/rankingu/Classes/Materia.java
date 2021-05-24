package com.example.rankingu.Classes;

import java.io.Serializable;
import java.util.List;

public class Materia implements Serializable {

    String nombre , descripcion;
    Integer semestre;
    Double puntaje;
    List<SesionClase> sesiones_clase;
    String profesores;

    public Materia() {
    }
    /*
    @Override
    public String toString() {
        String resul = nombre+"\n";
        for(SesionClase s:this.getSesiones_clase())
        {
            resul+= s.toString()+"\n";
        }
        return resul;
    }

     */


    @Override
    public String toString() {
        return "\nMateria: " +
                nombre;
    }



    public Materia(String nombre, String descripcion, Integer semestre, Double puntaje, List<SesionClase> sesiones_clase, String profesores) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.semestre = semestre;
        this.puntaje = puntaje;
        this.sesiones_clase = sesiones_clase;
        this.profesores = profesores;
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

    public String getProfesores() {
        return profesores;
    }

    public void setProfesores(String profesores) {
        this.profesores = profesores;
    }


}
