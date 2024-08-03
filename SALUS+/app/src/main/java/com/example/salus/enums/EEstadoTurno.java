package com.example.salus.enums;

public enum EEstadoTurno {
    Presente,
    Ausente,
    Pendiente;

    public static EEstadoTurno getByIndex(int index) {
        return EEstadoTurno.values()[index];
    }
}
