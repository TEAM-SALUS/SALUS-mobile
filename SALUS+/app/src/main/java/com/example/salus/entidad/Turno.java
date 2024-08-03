package com.example.salus.entidad;

import com.example.salus.enums.EEstadoTurno;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Turno {
    private Long id;
    private String fecha;
    private String horario;
    private boolean pagado;
    private EEstadoTurno estado;
    private String sintomas;
    private String diagnostico;
    private String tratamiento;
    private boolean is_Active;
    private Long id_medico;
    private Long id_paciente;

    public Turno() {
    }

    public Turno(Long id, String fecha, String horario, boolean pagado, EEstadoTurno estado, String sintomas, String diagnostico, String tratamiento, boolean is_Active, Long id_medico, Long id_paciente) {
        this.id = id;
        this.fecha = fecha;
        this.horario = horario;
        this.pagado = pagado;
        this.estado = estado;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.is_Active = is_Active;
        this.id_medico = id_medico;
        this.id_paciente = id_paciente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public EEstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EEstadoTurno estado) {
        this.estado = estado;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public boolean isIs_Active() {
        return is_Active;
    }

    public void setIs_Active(boolean is_Active) {
        this.is_Active = is_Active;
    }

    public Long getId_medico() {
        return id_medico;
    }

    public void setId_medico(Long id_medico) {
        this.id_medico = id_medico;
    }

    public Long getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(Long id_paciente) {
        this.id_paciente = id_paciente;
    }

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", fecha='" + fecha + '\'' +
                ", horario='" + horario + '\'' +
                ", pagado=" + pagado +
                ", estado=" + estado +
                ", sintomas='" + sintomas + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamiento='" + tratamiento + '\'' +
                ", is_Active=" + is_Active +
                ", id_medico=" + id_medico +
                ", id_paciente=" + id_paciente +
                '}';
    }
}

