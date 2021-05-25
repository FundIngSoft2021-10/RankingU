package com.example.rankingu.Classes;

import java.io.Serializable;

public class Usuario implements Serializable {

    String Usuario, Tipo, Semestre, Correo, Carrera, Nombre, Apellido, Foto;

    public Usuario() {
    }

    public Usuario(String usuario, String tipo, String semestre, String correo, String carrera, String nombre, String apellido, String foto) {
        Usuario = usuario;
        Tipo = tipo;
        Semestre = semestre;
        Correo = correo;
        Carrera = carrera;
        Nombre = nombre;
        Apellido = apellido;
        Foto = foto;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
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
