package com.example.rankingu.Classes;

public class Usuario {

    String User, Tipo, Semestre, Correo, Carrera, Nombre, Apellido;

    public Usuario() {
    }

    public Usuario(String user, String tipo, String semestre, String correo, String carrera, String nombre, String apellido) {
        User = user;
        Tipo = tipo;
        Semestre = semestre;
        Correo = correo;
        Carrera = carrera;
        Nombre = nombre;
        Apellido = apellido;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getSemestre() {
        return Semestre;
    }

    public void setSemestre(String semestre) {
        Semestre = semestre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getCarrera() {
        return Carrera;
    }

    public void setCarrera(String carrera) {
        Carrera = carrera;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }
}
