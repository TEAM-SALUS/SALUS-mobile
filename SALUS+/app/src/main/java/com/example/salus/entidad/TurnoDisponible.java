package com.example.salus.entidad;

public class TurnoDisponible {
    private String dia;
    private String hora;
    private int id;

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "TurnoDisponible{" +
                "dia='" + dia + '\'' +
                ", hora='" + hora + '\'' +
                ", id=" + id +
                '}';
    }
}

