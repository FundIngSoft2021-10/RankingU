package com.example.rankingu.Enum;

public enum EnumHoras {

    SIETE_AM("7:00"),
    OCHO_AM("8:00"),
    NUEVE_AM("9:00"),
    DIEZ_AM("10:00"),
    ONCE_AM("11:00"),
    DOCE_PM("12:00"),
    UNA_PM("13:00"),
    DOS_PM("14:00"),
    TRES_PM("15:00"),
    CUATRO_PM("16:00"),
    CINCO_PM("17:00"),
    SEIS_PM("18:00"),
    SIETE_PM("19:00"),
    OCHO_PM ("20:00"),
    NUEVE_PM("21:00"),
    DIEZ_PM ("22:00");

    private String nombre;

    EnumHoras(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
