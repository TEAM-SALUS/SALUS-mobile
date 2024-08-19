package com.example.salus.entidad;

public class NuevoTurno {
    private int id_medico;
    private int id_paciente;
    private int turno_disponible;
    private String obra_social;
    private String estado;
    private boolean pagado;

    // Getters y setters

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }



    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }
    public int getTurno_disponible() {
        return turno_disponible;
    }

    public void setTurno_disponible(int turno_disponible) {
        this.turno_disponible = turno_disponible;
    }

    public int getId_medico() {
        return id_medico;
    }

    public void setId_medico(int id_medico) {
        this.id_medico = id_medico;
    }

    public String getObraSocial() {
        return obra_social;
    }

    public void setObra_social(String obra_social) {
        this.obra_social = obra_social;
    }
    @Override
    public String toString() {
        return "NuevoTurno{" +
                "id_medico=" + id_medico +
                ", id_paciente=" + id_paciente +
                ", turno_disponible=" + turno_disponible +
                ", obra_social='" + obra_social + '\'' +
                ", estado='" + estado + '\'' +
                ", pagado=" + pagado +
                '}';
    }
}