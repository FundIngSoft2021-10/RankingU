package com.example.rankingu.Classes;

import java.util.List;

public class Reseña {

    String comentarios;
    double rating;
    String correo_usuario;

    public Reseña() {
    }

    public Reseña(String comentarios, double rating, String correo_usuario) {
        this.comentarios = comentarios;
        this.rating = rating;
        this.correo_usuario = correo_usuario;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCorreo_usuario() {
        return correo_usuario;
    }

    public void setCorreo_usuario(String correo_usuario) {
        this.correo_usuario = correo_usuario;
    }
}
