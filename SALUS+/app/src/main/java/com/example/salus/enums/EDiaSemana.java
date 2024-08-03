package com.example.salus.enums;

public enum EDiaSemana {
    DOMINGO,
    LUNES,
    MARTES,
    MIERCOLES,
    JUEVES,
    VIERNES,
    SABADO;

    public static EDiaSemana getByIndex(int index) {
        return EDiaSemana.values()[index];
    }
}
