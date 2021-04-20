package com.example.rankingu.Classes;

public class Reseña {

    private String usuario, reseña;

    public Reseña() {
    }

    public Reseña(String usuario, String error) {
        this.usuario = usuario;
        this.reseña = reseña;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getError() {
        return reseña;
    }

    public void setError(String error) {
        this.reseña = reseña;
    }
}
