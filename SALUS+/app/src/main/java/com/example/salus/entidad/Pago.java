package com.example.salus.entidad;
public class Pago {
    private int id_pago;
    private int monto;
    private String fecha;
    private String hora;
    private String estado;
    private int id_turno;

    // Constructor vacío
    public Pago() {
    }

    // Constructor con parámetros
    public Pago(int id_pago, int monto, String fecha, String hora, String estado, int id_turno) {
        this.id_pago = id_pago;
        this.monto = monto;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.id_turno = id_turno;
    }

    // Getters y Setters
    public int getId_pago() {
        return id_pago;
    }

    public void setId_pago(int id_pago) {
        this.id_pago = id_pago;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_turno() {
        return id_turno;
    }

    public void setId_turno(int id_turno) {
        this.id_turno = id_turno;
    }
}