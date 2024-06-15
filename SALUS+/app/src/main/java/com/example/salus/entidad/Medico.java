package com.example.salus.entidad;

import java.util.List;

public class Medico {
    private int id;
    private String matricula;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String foto;
    private int id_horario;
    private int id_especialidad;
    private List<HorarioDeAtencion> horariosDeAtencion;

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getId_horario() {
        return id_horario;
    }

    public void setId_horario(int id_horario) {
        this.id_horario = id_horario;
    }

    public int getId_especialidad() {
        return id_especialidad;
    }

    public void setId_especialidad(int id_especialidad) {
        this.id_especialidad = id_especialidad;
    }

    public List<HorarioDeAtencion> getHorariosDeAtencion() {
        return horariosDeAtencion;
    }

    public void setHorariosDeAtencion(List<HorarioDeAtencion> horariosDeAtencion) {
        this.horariosDeAtencion = horariosDeAtencion;
    }
}

