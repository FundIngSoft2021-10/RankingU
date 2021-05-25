package com.example.rankingu.Classes;

import java.io.Serializable;

public class SesionClase implements Serializable {

    private String dia, hInicio, hFin, cupos;

    public SesionClase() {
    }

    public SesionClase(String dia, String hInicio, String hFin, String cupos) {
        this.dia = dia;
        this.hInicio = hInicio;
        this.hFin = hFin;
        this.cupos = cupos;
    }

    public String getCupos() {
        return cupos;
    }

    public void setCupos(String cupos) {
        this.cupos = cupos;
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

    @Override
    public String toString()
    {
        if(this.dia != null)
            return this.dia+": "+this.hInicio+"-"+this.hFin;
        else
            return "Seleccione";
    }

}
