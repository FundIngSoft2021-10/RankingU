package com.example.rankingu.Classes;

public class Reseña {

    String Comentarios;
    String correo;
    float rating;

    public Reseña(String comentarios, String correo, float rating) {
        Comentarios = comentarios;
        this.correo = correo;
        this.rating = rating;
    }

    public Reseña() {
    }

    public String getComentarios() {
        return Comentarios;
    }

    public void setComentarios(String comentarios) {
        Comentarios = comentarios;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
