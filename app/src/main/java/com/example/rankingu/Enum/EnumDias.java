package com.example.rankingu.Enum;

public enum EnumDias {
    LUNES("lunes"),
    MARTES("martes"),
    MIERCOLES("miercoles"),
    JUEVES("jueves"),
    VIERNES("viernes"),
    SABADO("sabado"),
    DOMINGO("domingo");

    private String nombre;

    EnumDias(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
