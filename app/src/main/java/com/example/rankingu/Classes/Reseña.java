package com.example.rankingu.Classes;

public class Reseña {

    private String usuario, reseña;
    private float rating;

    public Reseña(String usuario, String reseña, float rating) {
        this.usuario = usuario;
        this.reseña = reseña;
        this.rating = rating;
    }



    public Reseña() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getReseña() {
        return reseña;
    }

    public void setReseña(String reseña) {
        this.reseña = reseña;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
