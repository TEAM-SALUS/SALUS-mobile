package com.example.salus.entidad;

import com.example.salus.enums.EEstadoTurno;

public class Pago {
    private Long id;
    private double monto;
    private String fecha;
    private String hora;
    private EEstadoTurno estado;
    private boolean is_active;
    private Long id_turno;

    // Constructor vacío
    public Pago() {
    }

    // Constructor con parámetros
    public Pago(Long id, double monto, String fecha, String hora, EEstadoTurno estado, boolean is_active, Long id_turno) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.is_active = is_active;
        this.id_turno = id_turno;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public EEstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EEstadoTurno estado) {
        this.estado = estado;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Long getId_turno() {
        return id_turno;
    }

    public void setId_turno(Long id_turno) {
        this.id_turno = id_turno;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "id=" + id +
                ", monto=" + monto +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", estado=" + estado +
                ", is_active=" + is_active +
                ", id_turno=" + id_turno +
                '}';
    }
}