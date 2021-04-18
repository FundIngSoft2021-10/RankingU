package com.example.rankingu.Classes;

public class Horario {

    String dias,horas;
    Integer cupos;

    public Horario() {
    }

    public Horario(String dias, String horas, Integer cupos) {
        this.dias = dias;
        this.horas = horas;
        this.cupos = cupos;
    }

    public Integer getCupos() {
        return cupos;
    }

    public void setCupos(Integer cupos) {
        this.cupos = cupos;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }
}
