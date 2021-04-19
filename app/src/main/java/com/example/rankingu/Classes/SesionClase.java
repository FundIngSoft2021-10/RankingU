package com.example.rankingu.Classes;

public class SesionClase {

    private String dia;
    private String hInicio;
    private String hFin;

    public SesionClase(String dia, String hInicio, String hFin) {
        this.dia = dia;
        this.hInicio = hInicio;
        this.hFin = hFin;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String gethInicio() {
        return hInicio;
    }

    public void sethInicio(String hInicio) {
        this.hInicio = hInicio;
    }

    public String gethFin() {
        return hFin;
    }

    public void sethFin(String hFin) {
        this.hFin = hFin;
    }

    public String toString()
    {
        return this.dia+": "+this.hInicio+"-"+this.hFin;
    }
}
