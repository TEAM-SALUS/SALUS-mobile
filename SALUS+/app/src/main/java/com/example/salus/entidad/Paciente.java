package com.example.salus.entidad;

public class Paciente {
    private Long id;
    private String dni_paciente;
    private String nombre;
    private String apellido;
    private String email;
    private String clave;
    private String telefono;
    private String foto;
    private String is_active;
    private Long id_obra_social;
    private Integer pacienteUser;

    public Paciente() {
    }

    public Paciente(Long id, String dni_paciente, String nombre, String apellido, String email, String clave, String telefono, String foto, String is_active, Long id_obra_social, Integer pacienteUser) {
        this.id = id;
        this.dni_paciente = dni_paciente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.clave = clave;
        this.telefono = telefono;
        this.foto = foto;
        this.is_active = is_active;
        this.id_obra_social = id_obra_social;
        this.pacienteUser = pacienteUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni_paciente() {
        return dni_paciente;
    }

    public void setDni_paciente(String dni_paciente) {
        this.dni_paciente = dni_paciente;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
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

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public Long getId_obra_social() {
        return id_obra_social;
    }

    public void setId_obra_social(Long id_obra_social) {
        this.id_obra_social = id_obra_social;
    }

    public Integer getPacienteUser() {
        return pacienteUser;
    }

    public void setPacienteUser(Integer pacienteUser) {
        this.pacienteUser = pacienteUser;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", dni_paciente='" + dni_paciente + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", clave='" + clave + '\'' +
                ", telefono='" + telefono + '\'' +
                ", foto='" + foto + '\'' +
                ", is_active='" + is_active + '\'' +
                ", id_obra_social='" + id_obra_social + '\'' +
                ", pacienteUser=" + pacienteUser +
                '}';
    }
}
