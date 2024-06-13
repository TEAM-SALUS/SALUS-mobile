package com.example.salus.entidad;

public class TurnoDisponible {
    private int id;
    private String dia;
    private String hora;
    private int medico;
    public TurnoDisponible() {
    }
    public TurnoDisponible(int id, String dia, String hora, int medico) {
        this.id = id;
        this.dia = dia;
        this.hora = hora;
        this.medico = medico;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
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

    public int getMedico() {
        return medico;
    }
    public void setMedico(int medico) {
        this.hora = hora;
    }
    @Override
    public String toString() {
        return dia + " " + hora;
    }
}
